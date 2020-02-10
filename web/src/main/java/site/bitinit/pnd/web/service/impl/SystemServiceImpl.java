package site.bitinit.pnd.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.bitinit.pnd.web.config.FileType;
import site.bitinit.pnd.web.config.PndProperties;
import site.bitinit.pnd.web.controller.dto.SystemInfoDto;
import site.bitinit.pnd.web.dao.FileMapper;
import site.bitinit.pnd.web.service.SystemService;

import java.io.File;
import java.util.List;

/**
 * @author john
 * @date 2020-02-10
 */
@Service
public class SystemServiceImpl implements SystemService {

    private final FileMapper fileMapper;
    private final PndProperties pndProperties;

    @Autowired
    public SystemServiceImpl(FileMapper fileMapper, PndProperties pndProperties) {
        this.fileMapper = fileMapper;
        this.pndProperties = pndProperties;
    }

    @Override
    public SystemInfoDto systemInfo() {
        SystemInfoDto.SystemInfoDtoBuilder builder = SystemInfoDto.builder();
        File systemFile = new File(pndProperties.getPndDataDir());
        builder.totalCap(systemFile.getTotalSpace())
                .usableCap(systemFile.getUsableSpace());

        List<site.bitinit.pnd.web.entity.File> files = fileMapper.getAllFileType();

        int totalFileNum = 0, folderNum = 0, videoNum = 0, audioNum = 0;
        for (site.bitinit.pnd.web.entity.File file :
                files) {
            totalFileNum++;
            if (FileType.FOLDER.toString().equals(file.getType())) {
                folderNum++;
            }
            if (FileType.VIDEO.toString().equals(file.getType())) {
                videoNum++;
            }
            if (FileType.AUDIO.toString().equals(file.getType())) {
                audioNum++;
            }
        }

        builder.totalNum(totalFileNum).fileNum(totalFileNum - folderNum)
                .folderNum(folderNum).videoNum(videoNum).audioNum(audioNum);
        return builder.build();
    }
}
