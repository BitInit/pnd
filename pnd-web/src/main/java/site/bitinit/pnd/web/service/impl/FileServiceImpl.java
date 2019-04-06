package site.bitinit.pnd.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import site.bitinit.pnd.common.exception.IllegalDataException;
import site.bitinit.pnd.common.exception.PndException;
import site.bitinit.pnd.common.util.Assert;
import site.bitinit.pnd.web.config.SystemConstants;
import site.bitinit.pnd.web.controller.dto.FileDetailDto;
import site.bitinit.pnd.web.dao.FileDao;
import site.bitinit.pnd.web.dao.FileResourceDao;
import site.bitinit.pnd.web.dao.ResourceDao;
import site.bitinit.pnd.web.exception.SystemDealFailException;
import site.bitinit.pnd.web.model.PndFile;
import site.bitinit.pnd.web.model.PndResource;
import site.bitinit.pnd.web.service.FileService;

import java.util.List;
import java.util.Objects;

/**
 * @author: john
 * @date: 2019/4/3
 */
@Service
public class FileServiceImpl implements FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private FileDao fileDao;
    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private FileResourceDao fileResourceDao;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public List<FileDetailDto> getFileList(long parentId) {
        return fileResourceDao.findFileDetailByParentId(parentId);
    }

    @Override
    public void createFolder(long parentId, String folderName) {
        Assert.notEmpty(folderName, "文件夹名不能为空");
        fileDao.save(parentId, folderName);
    }

    @Override
    public void renameFile(long id, String fileName) {
        Assert.notEmpty(fileName, "新文件名不能为空");

        fileDao.renameFile(id, fileName);
    }

    @Override
    public void deleteFile(long id) {
        PndFile file = fileDao.findById(id);
        if (Objects.isNull(file)){
            throw new IllegalDataException("不存在 id=" + id + " 的数据");
        }
        if (SystemConstants.FileType.FOLDER.toString().equals(file.getType())){
            deleteFolder(file);
        } else {
            deleteCommonFile(file);
        }
    }

    /**
     * 删除文件夹及其子文件
     * @param file
     */
    private void deleteFolder(PndFile file){
        List<PndFile> fileList = fileDao.findByParentIdSortByGmtModified(file.getId());
    }

    /**
     * 删除普通文件
     * @param file
     */
    private void deleteCommonFile(PndFile file){
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                if (Objects.isNull(file.getResourceId())){
                    logger.error("索引资源失败 fileId-{}", file.getId());
                    throw new SystemDealFailException("文件删除失败");
                }

                fileDao.deleteFile(file.getId());
                int i = 0;
                while (i < HANDLING_TIMES) {
                    PndResource resource = resourceDao.findById(file.getResourceId());
                    if (Objects.isNull(resource)){
                        logger.error("索引资源失败 fileId-{} resourceId-{}", file.getId(), file.getResourceId());
                        throw new SystemDealFailException("文件删除失败");
                    }
                    int expectedVal = resource.getLink().intValue();
                    if (expectedVal <= 0){
                        break;
                    }
                    int affectedRows = resourceDao.updateIndex(resource.getId(), expectedVal, expectedVal - 1);
                    if (affectedRows > 0){
                        break;
                    }
                    i++;
                }
                if (i >= HANDLING_TIMES){
                    throw new SystemDealFailException("文件删除失败");
                }
                return Boolean.TRUE;
            }
        });
    }

    private static final int HANDLING_TIMES = 10;
}
