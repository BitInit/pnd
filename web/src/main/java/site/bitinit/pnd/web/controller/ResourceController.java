package site.bitinit.pnd.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.bitinit.pnd.web.Constants;
import site.bitinit.pnd.web.controller.dto.MergeFileDto;
import site.bitinit.pnd.web.controller.dto.ResponseDto;
import site.bitinit.pnd.web.entity.File;
import site.bitinit.pnd.web.entity.ResourceChunk;
import site.bitinit.pnd.web.service.FileService;
import site.bitinit.pnd.web.service.ResourceService;

import javax.servlet.http.HttpServletRequest;
import java.io.EOFException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * @author john
 * @date 2020-01-27
 */
@Slf4j
@RestController
@RequestMapping(Constants.API_VERSION)
public class ResourceController {

    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/resource/chunk")
    public ResponseEntity<ResponseDto> checkChunk(ResourceChunk chunk) {

        if (resourceService.checkChunk(chunk)) {
            return ResponseEntity.ok(ResponseDto.success("该文件块已经上传"));
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                .body(ResponseDto.success());
    }

    @PostMapping("/resource/chunk")
    public ResponseEntity<ResponseDto> uploadChunk(ResourceChunk chunk) {

        resourceService.saveChunk(chunk);
        return ResponseEntity.ok(ResponseDto.success());
    }

    @PostMapping("/resource/merge")
    public ResponseEntity<ResponseDto> mergeResource(@RequestBody MergeFileDto fileDto) {

        resourceService.mergeChunk(fileDto);
        return ResponseEntity.ok(ResponseDto.success());
    }

    @ExceptionHandler
    public void eofException(EOFException e) {
        log.warn("eof exception");
    }
}
