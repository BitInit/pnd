package site.bitinit.pnd.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.bitinit.pnd.common.ResponseEntity;
import site.bitinit.pnd.common.util.ResponseUtils;
import site.bitinit.pnd.web.config.SystemConstants;
import site.bitinit.pnd.web.controller.dto.FileDetailDto;
import site.bitinit.pnd.web.service.FileService;

import java.util.List;

/**
 * @author: john
 * @date: 2019/4/3
 */
@RestController
@RequestMapping(SystemConstants.API_VERSION)
public class FileController {
    @Autowired
    private FileService fileService;

    @GetMapping("/file")
    public ResponseEntity<List<FileDetailDto>> getFileList(@RequestParam(defaultValue = "0") long parentId){
        List<FileDetailDto> list = fileService.getFileList(parentId);
        return ResponseUtils.ok(list);
    }
}
