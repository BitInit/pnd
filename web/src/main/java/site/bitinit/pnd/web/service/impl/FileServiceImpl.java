package site.bitinit.pnd.web.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.bitinit.pnd.web.config.FileType;
import site.bitinit.pnd.web.config.PndProperties;
import site.bitinit.pnd.web.controller.dto.FolderPathDto;
import site.bitinit.pnd.web.controller.dto.ResponseDto;
import site.bitinit.pnd.web.dao.FileMapper;
import site.bitinit.pnd.web.dao.ResourceMapper;
import site.bitinit.pnd.web.entity.File;
import site.bitinit.pnd.web.entity.Resource;
import site.bitinit.pnd.web.exception.DataFormatException;
import site.bitinit.pnd.web.exception.DataNotFoundException;
import site.bitinit.pnd.web.service.FileService;
import site.bitinit.pnd.web.util.FileUtils;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * 在多文件和文件夹 删除/复制 数据时用的是悲观锁，可能会出现死锁
 * 但考虑到个人部署使用，所以系统并发量并不大，出现死锁的概率很低
 * @author john
 * @date 2020-01-11
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    private final FileMapper fileMapper;
    private final ResourceMapper resourceMapper;
    private final PndProperties pndProperties;

    @Autowired
    public FileServiceImpl(PndProperties pndProperties,
                           FileMapper fileMapper,
                           ResourceMapper resourceMapper) {
        this.fileMapper = fileMapper;
        this.pndProperties = pndProperties;
        this.resourceMapper = resourceMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseDto findByParentId(Long parentId) {
        List<File> files = fileMapper.findByParentId(parentId, false);
        List<FolderPathDto> folderPaths = getFolderTree(parentId);
        return ResponseDto.success(files, folderPaths);
    }

    @Override
    public ResponseDto findByFileId(Long fileId) {
        File file = fileMapper.findById(fileId);
        if (Objects.isNull(file)) {
            throw new DataNotFoundException("没有该文件");
        }
        return ResponseDto.success(file);
    }

    @Override
    public void createFile(File file) {
        FileUtils.checkFileName(file.getFileName());
        FileUtils.checkFileType(file.getType());
        File parentFile = findById(file.getParentId());
        if (Objects.isNull(parentFile) || !FileUtils.equals(parentFile.getType(), FileType.FOLDER)){
            throw new DataFormatException("parentId不存在或parentId不是文件夹");
        }
        fileMapper.save(file);
    }

    @Override
    public void renameFile(String fileName, Long id) {
        FileUtils.checkFileName(fileName);

        File updateFile = File.builder()
                .id(id).fileName(fileName)
                .build();
        fileMapper.update(updateFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void moveFiles(List<Long> ids, Long targetId) {
        File file = findById(targetId);
        if (Objects.isNull(file) || !FileType.FOLDER.toString().equals(file.getType())) {
            throw new DataNotFoundException("没有该文件夹");
        }

        fileMapper.updateParentId(ids, targetId, new Date());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void copyFiles(List<Long> fileIds, List<Long> targetIds) {
        for (Long targetId: targetIds) {
            File targetFile = findById(targetId);
            if (Objects.isNull(targetFile) ||
                    !FileType.FOLDER.toString().equals(targetFile.getType())) {
                throw new DataFormatException("无效的目标文件夹");
            }
            for (Long fileId: fileIds) {
                File file = fileMapper.findById(fileId);
                if (Objects.isNull(file)) {
                    throw new DataFormatException("无效的文件");
                } else if (FileType.FOLDER.toString().equals(file.getType())) {
                    copyFolder(file, targetFile);
                } else {
                    copyCommonFile(file, targetFile);
                }
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteFiles(List<Long> ids) {
        for (Long id: ids) {
            File file = fileMapper.findById(id);
            if (!file.getType().equals(FileType.FOLDER.toString())){
                deleteCommonFile(file);
            } else {
                deleteFolder(file);
            }
        }
    }

    @Override
    public ResourceWrapper loadResource(Long fileId) {
        site.bitinit.pnd.web.entity.File file = findById(fileId);
        if (Objects.isNull(file) || Objects.isNull(file.getResourceId())) {
            throw new DataNotFoundException("没有该文件");
        }
        Resource pndResource = resourceMapper.findById(file.getResourceId());
        if (Objects.isNull(pndResource)){
            throw new DataNotFoundException("没有该文件");
        }

        try {
            Path filePath = new java.io.File(pndProperties.getBasicResourcePath() + java.io.File.separator +
                    pndResource.getPath()).toPath();
            org.springframework.core.io.Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()){
                return new ResourceWrapper(resource, file);
            } else {
                throw new DataNotFoundException("没有该文件");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new DataNotFoundException("没有该文件");
        }
    }

    private List<FolderPathDto> getFolderTree(Long parentId){
        List<FolderPathDto> result = new ArrayList<>();
        buildFolderTree(parentId, result);
        Collections.reverse(result);
        return result;
    }

    private void buildFolderTree(Long parentId, List<FolderPathDto> result){
        if (parentId.equals(File.ROOT_FILE.getId())){
            result.add(new FolderPathDto(parentId, File.ROOT_FILE.getFileName()));
            return;
        }

        File file = findById(parentId);
        if (Objects.nonNull(file) && FileUtils.equals(file.getType(), FileType.FOLDER)){
            result.add(new FolderPathDto(file.getId(), file.getFileName()));
            buildFolderTree(file.getParentId(), result);
        } else {
            throw new DataNotFoundException("不存在该文件夹");
        }
    }

    private void deleteFolder(File file) {
        List<File> children = fileMapper.findByParentIdForUpdate(file.getId());
        for (File f: children) {
            if (FileType.FOLDER.toString().equals(f.getType())) {
                deleteFolder(f);
            } else {
                deleteCommonFile(f);
            }
        }
        fileMapper.deleteByIds(Collections.singletonList(file.getId()));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class)
    public void deleteCommonFile(File file){
        Resource resource = resourceMapper.findByIdForUpdate(file.getResourceId());
        if (Objects.isNull(resource)) {
            throw new IllegalStateException("数据库数据异常");
        }
        if (resource.getLink() <= 1) {
            java.io.File resourceFile = new java.io.File(pndProperties.getBasicResourcePath() +
                    java.io.File.separator + resource.getPath());
            if (resourceFile.exists() && resourceFile.delete()) {
                resourceMapper.delete(resource.getId());
            } else {
                throw new IllegalStateException("文件删除失败");
            }
        } else {
            resourceMapper.updateLink(resource.getId(), resource.getLink() - 1);
        }
        fileMapper.deleteByIds(Collections.singletonList(file.getId()));
    }

    private void copyFolder(File file, File parentFile) {
        if (parentFile.getParentId().equals(file.getId())) {
            throw new DataFormatException("不能复制到子文件夹中");
        }
        File newFile = File.builder()
                .type(FileType.FOLDER.toString()).parentId(parentFile.getId())
                .fileName(file.getFileName())
                .build();
        fileMapper.save(newFile);

        List<File> children = fileMapper.findByParentId(file.getId(), true);
        for (File f: children) {
            if (FileType.FOLDER.toString().equals(f.getType())) {
                copyFolder(f, newFile);
            } else {
                copyCommonFile(f, newFile);
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class)
    public void copyCommonFile(File file, File parentFile) {
        Resource resource = resourceMapper.findByIdForUpdate(file.getResourceId());
        if (Objects.isNull(resource)) {
            throw new IllegalStateException("数据库数据异常");
        }
        resourceMapper.updateLink(resource.getId(), resource.getLink() + 1);
        File newFile = File.builder()
                .fileName(file.getFileName()).parentId(parentFile.getId())
                .type(file.getType()).resourceId(file.getResourceId())
                .build();
        fileMapper.save(newFile);
    }

    private File findById(Long id){
        if (id == 0){
            return File.ROOT_FILE;
        } else {
            return fileMapper.findById(id);
        }
    }
}
