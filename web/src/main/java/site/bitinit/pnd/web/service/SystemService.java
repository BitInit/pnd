package site.bitinit.pnd.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.bitinit.pnd.web.config.SystemConstants;
import site.bitinit.pnd.web.controller.dto.SystemInfoDto;
import site.bitinit.pnd.web.dao.FileDao;
import site.bitinit.pnd.web.dao.ResourceDao;
import site.bitinit.pnd.web.utils.PathUtils;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @author: john
 * @date: 2019/5/6
 */
@Service
public class SystemService {

    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private PathUtils pathUtils;

    public SystemInfoDto sysInfo(){
        SystemInfoDto infoDto = new SystemInfoDto();
        infoDto.setDiskCapacity(baseFile.getTotalSpace());
        infoDto.setDiskFree(baseFile.getFreeSpace());

        infoDto.setFileNum(resourceDao.findFileNum());
        infoDto.setGarbageFileNum((long)resourceDao.findDirtyResources().size());

        infoDto.setFolderNum(fileDao.findNumByType(SystemConstants.FileType.FOLDER));
        infoDto.setVideoNum(fileDao.findNumByType(SystemConstants.FileType.VIDEO));
        infoDto.setAudioNum(fileDao.findNumByType(SystemConstants.FileType.AUDIO));

        return infoDto;
    }

    private static File baseFile;

    @PostConstruct
    private void init(){
        baseFile = new File(pathUtils.getResourceBasePath());
    }
}
