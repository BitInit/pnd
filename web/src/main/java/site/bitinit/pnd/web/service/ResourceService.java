package site.bitinit.pnd.web.service;

import org.springframework.core.io.Resource;
import site.bitinit.pnd.web.controller.dto.MergeFileDto;
import site.bitinit.pnd.web.entity.File;
import site.bitinit.pnd.web.entity.ResourceChunk;

/**
 * @author john
 * @date 2020-01-27
 */
public interface ResourceService {

    /**
     * 检查块是否已经上传
     * @param chunk 块
     * @return true： 已经上传了 false：还未上传
     */
    boolean checkChunk(ResourceChunk chunk);

    /**
     * 保存chunk
     * @param chunk chunk
     */
    void saveChunk(ResourceChunk chunk);

    /**
     * 合并chunk
     * @param mergeFileDto 文件信息
     */
    void mergeChunk(MergeFileDto mergeFileDto);


}
