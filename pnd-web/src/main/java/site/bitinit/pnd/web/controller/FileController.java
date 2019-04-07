package site.bitinit.pnd.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.bitinit.pnd.common.ResponseEntity;
import site.bitinit.pnd.common.util.Assert;
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

    @PostMapping("/file")
    @ResponseStatus(HttpStatus.CREATED)
    public void createFile(@RequestParam(defaultValue = "0") long parentId,
                           String folderName){
        fileService.createFolder(parentId, folderName);
    }

    @PutMapping("/file/{id}")
    public void renameFile(@PathVariable("id") long id, String fileName){
        fileService.renameFile(id, fileName);
    }

    @PutMapping("/file/{id}/move/{targetId}")
    public void moveFile(@PathVariable("id") long id, @PathVariable("targetId") long targetId){
        fileService.moveFile(id, targetId);
    }

    @DeleteMapping("/file/{id}")
    public void deleteFile(@PathVariable("id") long id){
        fileService.deleteFile(id);
    }

    @GetMapping("/file/{id}/subfolder")
    public ResponseEntity getSubfolder(@PathVariable(value = "id") long id){
        return ResponseUtils.ok(fileService.getSubfolder(id));
    }
}
