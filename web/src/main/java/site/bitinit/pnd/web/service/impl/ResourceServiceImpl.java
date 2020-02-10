package site.bitinit.pnd.web.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.bitinit.pnd.web.config.PndProperties;
import site.bitinit.pnd.web.controller.dto.MergeFileDto;
import site.bitinit.pnd.web.dao.ResourceChunkMapper;
import site.bitinit.pnd.web.dao.ResourceMapper;
import site.bitinit.pnd.web.entity.Resource;
import site.bitinit.pnd.web.entity.ResourceChunk;
import site.bitinit.pnd.web.exception.DataNotFoundException;
import site.bitinit.pnd.web.exception.PndException;
import site.bitinit.pnd.web.service.FileService;
import site.bitinit.pnd.web.service.ResourceService;
import site.bitinit.pnd.web.util.FileUtils;
import site.bitinit.pnd.web.util.Utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.Objects;

/**
 * @author john
 * @date 2020-01-27
 */
@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {

    private final PndProperties pndProperties;
    private final ResourceChunkMapper chunkMapper;
    private final ResourceMapper resourceMapper;
    private final FileService fileService;

    @Autowired
    public ResourceServiceImpl(ResourceChunkMapper chunkMapper,
                               PndProperties pndProperties,
                               ResourceMapper resourceMapper, FileService fileService) {
        this.chunkMapper = chunkMapper;
        this.pndProperties = pndProperties;
        this.resourceMapper = resourceMapper;
        this.fileService = fileService;
    }

    @Override
    public boolean checkChunk(ResourceChunk chunk) {
        ResourceChunk resourceChunk = chunkMapper.findByIdentifierAndChunkNumber(chunk.getIdentifier(), chunk.getChunkNumber());
        return Objects.nonNull(resourceChunk);
    }

    @Override
    public void saveChunk(ResourceChunk chunk) {
        String chunkDirPath = getChunkTmpDir(chunk.getIdentifier());
        String chunkPath = getChunkTmpPath(chunk);
        try {
            if (!Files.isWritable(Paths.get(chunkDirPath))) {
                Files.createDirectories(Paths.get(chunkDirPath));
            }
            Path p = Paths.get(chunkPath);
            Files.write(p, chunk.getFile().getBytes());

            chunkMapper.save(chunk);

        } catch (Exception e) {
            log.error(e.getMessage() + "{}", e);
            throw new PndException(String.format("保存块[%s]失败", chunkPath));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void mergeChunk(MergeFileDto mergeFileDto) {
        // 合并文件到目标文件夹，并删除临时文件
        String uuid = mergeChunks(mergeFileDto);
        // 创建Resource记录和File记录
        generateRecord(mergeFileDto, uuid);
    }

    private String getChunkTmpDir(String identifier) {
        return pndProperties.getResourceTmpDir() + File.separator
                + identifier;
    }

    private String getChunkTmpPath(ResourceChunk chunk) {
        return getChunkTmpDir(chunk.getIdentifier()) + File.separator
                + chunk.getFilename() + "-" + chunk.getChunkNumber();
    }

    private String mergeChunks(MergeFileDto mergeFileDto){
        String targetFileDir = pndProperties.getBasicResourcePath() + File.separator
                + Utils.formatDate(mergeFileDto.getCreateTime(), "yyyy") + File.separator
                + Utils.formatDate(mergeFileDto.getCreateTime(), "MM");
        Utils.createFolders(targetFileDir);
        String uuid = Utils.uuid();
        String targetFilePath = targetFileDir + File.separator + uuid +
                FileUtils.extractFileExtensionName(mergeFileDto.getFileName());

        try {
            Files.createFile(Paths.get(targetFilePath));
            Files.list(Paths.get(getChunkTmpDir(mergeFileDto.getIdentifier())))
                    .filter(path -> !path.getFileName().toString().equals(mergeFileDto.getFileName()))
                    .sorted((o1, o2) -> {
                        String p1 = o1.getFileName().toString();
                        String p2 = o2.getFileName().toString();
                        int i1 = p1.lastIndexOf("-");
                        int i2 = p2.lastIndexOf("-");
                        return Integer.valueOf(p2.substring(i2)).compareTo(Integer.valueOf(p1.substring(i1)));
                    })
                    .forEach(path -> {
                        try {
                            Files.write(Paths.get(targetFilePath), Files.readAllBytes(path), StandardOpenOption.APPEND);
                            Files.delete(path);
                        } catch (IOException e) {
                            log.error(e.getMessage() + "{}", e);
                            throw new PndException(e.getMessage());
                        }
                    });
            Utils.deleteFile(getChunkTmpDir(mergeFileDto.getIdentifier()));
            chunkMapper.deleteChunk(mergeFileDto.getIdentifier());
        } catch (Exception e) {
            log.error(e.getMessage() + "{}", e);
            throw new PndException(e.getMessage());
        }
        return uuid;
    }

    private void generateRecord(MergeFileDto fileDto, String uuid){
        Resource resource = Resource.builder()
                .link(1).path(Utils.formatDate(fileDto.getCreateTime(), "yyyy") + File.separator +
                        Utils.formatDate(fileDto.getCreateTime(), "MM") + File.separator +
                        uuid + FileUtils.extractFileExtensionName(fileDto.getFileName()))
                //TODO md5校验
                .md5(Utils.uuid())
                .size(fileDto.getSize())
                .build();
        resourceMapper.save(resource);
        fileDto.setResourceId(resource.getId());
        fileDto.setType(FileUtils.getFileType(fileDto.getFileName()).toString());
        fileService.createFile(fileDto);
    }
}
