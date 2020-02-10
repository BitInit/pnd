package site.bitinit.pnd.web.service;

import org.springframework.core.io.Resource;
import site.bitinit.pnd.web.controller.dto.ResponseDto;
import site.bitinit.pnd.web.entity.File;

import java.util.List;

/**
 * @author john
 * @date 2020-01-11
 */
public interface FileService {

    /**
     * 根据parentId返回文件
     * @param parentId parentId
     * @return list
     */
    ResponseDto findByParentId(Long parentId);

    /**
     * 根据id返回文件
     * @param fileId fileId
     * @return file response
     */
    ResponseDto findByFileId(Long fileId);

    /**
     * 创建文件
     * @param file file
     */
    void createFile(File file);

    /**
     * 更新文件
     * @param fileName 文件名
     * @param id 文件id
     */
    void renameFile(String fileName, Long id);

    /**
     * 文件移动
     * @param ids ids
     * @param targetId 目标文件夹
     */
    void moveFiles(List<Long> ids, Long targetId);

    /**
     * 文件复制
     * @param fileIds fileIds
     * @param targetIds targetIds
     */
    void copyFiles(List<Long> fileIds, List<Long> targetIds);

    /**
     * 删除文件及其子文件
     * @param ids 文件id
     */
    void deleteFiles(List<Long> ids);

    /**
     * 加载资源，下载
     * @param fileId fileId
     * @return resource
     */
    ResourceWrapper loadResource(Long fileId);

    class ResourceWrapper{
        public Resource resource;
        public File file;

        public ResourceWrapper(Resource resource, File file) {
            this.resource = resource;
            this.file = file;
        }
    }
}
