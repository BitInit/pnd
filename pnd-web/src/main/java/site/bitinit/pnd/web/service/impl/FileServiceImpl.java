package site.bitinit.pnd.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.bitinit.pnd.web.controller.dto.FileDetailDto;
import site.bitinit.pnd.web.dao.FileDao;
import site.bitinit.pnd.web.dao.FileResourceDao;
import site.bitinit.pnd.web.model.PndFile;
import site.bitinit.pnd.web.service.FileService;

import java.util.List;

/**
 * @author: john
 * @date: 2019/4/3
 */
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileDao fileDao;
    @Autowired
    private FileResourceDao fileResourceDao;

    @Override
    public List<FileDetailDto> getFileList(long parentId) {
        return fileResourceDao.findFileDetailByParentId(parentId);
    }
}
