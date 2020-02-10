package site.bitinit.pnd.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import site.bitinit.pnd.web.Constants;
import site.bitinit.pnd.web.controller.dto.MoveAndCopyFileDto;
import site.bitinit.pnd.web.controller.dto.ResponseDto;
import site.bitinit.pnd.web.dao.FileMapper;
import site.bitinit.pnd.web.entity.File;
import site.bitinit.pnd.web.exception.DataFormatException;
import site.bitinit.pnd.web.service.FileService;
import site.bitinit.pnd.web.service.ResourceService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;

/**
 * @author john
 * @date 2020-01-05
 */
@Slf4j
@RestController
@RequestMapping(Constants.API_VERSION)
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/file/parent/{parentId}")
    public ResponseEntity<ResponseDto> getFiles(@PathVariable Long parentId){
        return ResponseEntity.ok(fileService.findByParentId(parentId));
    }

    @GetMapping("/file/{fileId}")
    public ResponseEntity<ResponseDto> getFile(@PathVariable Long fileId) {
        return ResponseEntity.ok(fileService.findByFileId(fileId));
    }

    @PostMapping("/file")
    public ResponseEntity<ResponseDto> createFile(@Valid @RequestBody File file, BindingResult result){
        if (result.hasErrors()){
            StringBuilder errors = new StringBuilder();
            for (FieldError fe: result.getFieldErrors()){
                errors.append(fe.getDefaultMessage()).append("; ");
            }
            throw new DataFormatException(errors.toString());
        }

        fileService.createFile(file);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success());
    }

    @PutMapping("/file/{fileId}/rename")
    public ResponseEntity<ResponseDto> renameFile(@PathVariable Long fileId,
                                                  @RequestBody File file){
        fileService.renameFile(file.getFileName(), fileId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.success());
    }

    @PutMapping("/file")
    public ResponseEntity<ResponseDto> copyOrMoveFiles(@RequestBody MoveAndCopyFileDto dto){

        if (MoveAndCopyFileDto.MOVE_TYPE.equals(dto.getType())) {
            if (Objects.isNull(dto.getTargetIds()) || dto.getTargetIds().size() != 1){
                throw new DataFormatException("targetIds必须只有一个值");
            }
            fileService.moveFiles(dto.getFileIds(), dto.getTargetIds().get(0));
        } else if (MoveAndCopyFileDto.COPY_TYPE.equals(dto.getType())) {
            fileService.copyFiles(dto.getFileIds(), dto.getTargetIds());
        } else {
            throw new DataFormatException("type类型不正确");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.success());
    }

    @DeleteMapping("/file")
    public ResponseEntity<ResponseDto> deleteFiles(@RequestBody List<Long> fileIds){
        fileService.deleteFiles(fileIds);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.success());
    }

    @GetMapping("/file/{fileId}/download")
    public org.springframework.http.ResponseEntity<Resource> downloadFile(@PathVariable Long fileId,
                                                                          HttpServletRequest request) throws UnsupportedEncodingException {
        FileService.ResourceWrapper resourceWrapper = fileService.loadResource(fileId);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resourceWrapper.resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            // pass
        }
        if (Objects.isNull(contentType)){
            contentType = "application/octet-stream";
        }

        return org.springframework.http.ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + URLEncoder.encode(resourceWrapper.file.getFileName(), "UTF-8"))
                .body(resourceWrapper.resource);
    }

    @ExceptionHandler
    public void clientAbortException(ClientAbortException e){
        log.warn("client cancelled file download {}", e.getMessage());
    }
}
