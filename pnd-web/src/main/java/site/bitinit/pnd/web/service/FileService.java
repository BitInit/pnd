package site.bitinit.pnd.web.service;

import site.bitinit.pnd.web.controller.dto.FileDetailDto;
import site.bitinit.pnd.web.model.PndFile;

import java.util.List;

/**
 * @author: john
 * @date: 2019/4/3
 */
public interface FileService {

    /**
     * 获取文件列表
     * @param parentId
     * @return
     */
    List<FileDetailDto> getFileList(long parentId);

    /**
     * 创建新文件夹
     * @param parentId
     * @param folderName
     */
    void createFolder(long parentId, String folderName);

    /**
     * 文件重命名
     * @param id
     * @param fileName
     */
    void renameFile(long id, String fileName);

    /**
     * 删除文件
     * @param id
     */
    void deleteFile(long id);

    /**
     * 获取子文件夹
     * @param id
     * @return
     */
    List<PndFile> getSubfolder(long id);

    /**
     * 移动文件
     * @param id
     * @param targetId
     */
    void moveFile(long id, long targetId);
}
