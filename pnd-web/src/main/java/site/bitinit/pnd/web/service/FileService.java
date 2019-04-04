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
}
