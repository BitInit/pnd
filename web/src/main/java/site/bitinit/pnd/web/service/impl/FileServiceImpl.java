package site.bitinit.pnd.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import site.bitinit.pnd.common.exception.IllegalDataException;
import site.bitinit.pnd.common.util.Assert;
import site.bitinit.pnd.exception.SystemDealFailException;
import site.bitinit.pnd.web.config.SystemConstants;
import site.bitinit.pnd.web.controller.dto.FileDetailDto;
import site.bitinit.pnd.web.dao.FileDao;
import site.bitinit.pnd.web.dao.FileResourceDao;
import site.bitinit.pnd.web.dao.ResourceDao;
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
        fileDao.createFolder(parentId, folderName);
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

    @Override
    public List<PndFile> getSubfolder(long id) {
        return fileDao.findSubfolderByParentId(id);
    }

    @Override
    public void moveFile(long id, long targetId) {
        if (id == targetId){
            throw new IllegalDataException("源文件与目标文件不能一样");
        }
        PndFile targetFile = fileDao.findById(targetId);
        if (Objects.isNull(targetFile) && targetId != 0){
            throw new IllegalDataException("目标文件夹不存在");
        }
        targetFile = targetId != 0? targetFile: PndFile.ROOT_PND_FILE;

        if (isChild(id, targetFile)){
            throw new IllegalDataException("目标文件夹不能为源文件夹的子目录");
        } else {
            fileDao.moveFile(id, targetId);
        }
    }

    @Override
    public void copyFile(long id, Long[] targetIds) {
        if (Objects.isNull(targetIds) || targetIds.length == 0){
            throw new IllegalDataException("目标文件夹不能为空");
        }

        PndFile file = fileDao.findById(id);
        if (Objects.isNull(file) || SystemConstants.FileType.FOLDER.toString().equals(file.getType())){
            throw new IllegalDataException("源文件不存在或源文件不能是文件夹");
        }
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                int num = 0;
                for (Long targetId: targetIds) {
                    PndFile targetFile = fileDao.findById(targetId);
                    // 可能复制到根目录
                    targetFile = targetId == 0? PndFile.ROOT_PND_FILE: targetFile;
                    if (Objects.isNull(targetFile) || !SystemConstants.FileType.FOLDER.toString().equals(targetFile.getType())){
                        continue;
                    }

                    PndFile copyFile = new PndFile();
                    copyFile.setName(file.getName());
                    copyFile.setParentId(targetId);
                    copyFile.setType(file.getType());
                    copyFile.setResourceId(file.getResourceId());
                    fileDao.save(copyFile);
                    num++;
                }

                final int finalNum = num;
                updateResourceLink(file.getResourceId(), new ResourceLinkOperation() {
                    @Override
                    public long operate(long originVal) {
                        return originVal + finalNum;
                    }
                });
                return Boolean.TRUE;
            }
        });
    }

    /**
     * targetFile 是否是 id 的子节点
     * @param id
     * @param targetFile
     * @return
     */
    private boolean isChild(long id, PndFile targetFile){
        if (targetFile.getParentId() == 0){
            return false;
        }
        if (targetFile.getParentId() == id){
            return true;
        }

        return isChild(id, fileDao.findById(targetFile.getParentId()));
    }

    /**
     * 删除文件夹及其子文件
     * @param file
     */
    private void deleteFolder(PndFile file){
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                deleteFolder0(file);
                return Boolean.TRUE;
            }
        });
    }

    private void deleteFolder0(PndFile file){
        if (!SystemConstants.FileType.FOLDER.toString().equals(file.getType())){
            throw new IllegalArgumentException("文件类型应该为文件夹");
        }

        List<PndFile> fileList = fileDao.findByParentIdSortByGmtModified(file.getId());
        for (PndFile f :
                fileList) {
            if (!SystemConstants.FileType.FOLDER.toString().equals(f.getType())) {
                deleteCommonFile0(f);
            } else {
                deleteFolder0(f);
            }
        }
        fileDao.deleteFile(file.getId());
    }

    /**
     * 删除除了文件夹以外的其他文件
     * @param file
     */
    private void deleteCommonFile(PndFile file){
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                deleteCommonFile0(file);
                return Boolean.TRUE;
            }
        });
    }

    /**
     * 删除普通文件
     * @param file
     */
    private void deleteCommonFile0(PndFile file){
        if (Objects.isNull(file.getResourceId())){
            logger.error("索引资源失败 fileId-{}", file.getId());
            return;
        }

        fileDao.deleteFile(file.getId());
        updateResourceLink(file.getResourceId(), new ResourceLinkOperation() {
            @Override
            public long operate(long originVal) {
                return originVal - 1;
            }
        });
    }

    private void updateResourceLink(long id, ResourceLinkOperation rlo){
        if (Objects.isNull(rlo)){
            throw new IllegalArgumentException("rlo can't be null");
        }
        int i = 0;
        while (i < HANDLING_TIMES) {
            PndResource resource = resourceDao.findById(id);
            if (Objects.isNull(resource)){
                logger.error("索引资源失败 resourceId-{}", id);
                return;
            }
            int expectedVal = resource.getLink();
            if (expectedVal < 0){
                throw new RuntimeException("系统存在脏数据");
            }
            int affectedRows = resourceDao.updateIndex(resource.getId(), expectedVal, rlo.operate(expectedVal));
            if (affectedRows > 0){
                break;
            }
            i++;
        }
        if (i >= HANDLING_TIMES){
            throw new SystemDealFailException("资源操作失败");
        }
    }

    private static final int HANDLING_TIMES = 10;

    @FunctionalInterface
    interface ResourceLinkOperation{
        /**
         * 资源操作
         * @param originVal
         * @return
         */
        long operate(long originVal);
    }
}
