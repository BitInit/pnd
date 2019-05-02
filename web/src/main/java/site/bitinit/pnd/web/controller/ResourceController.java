package site.bitinit.pnd.web.controller;

import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.bitinit.pnd.common.ResponseEntity;
import site.bitinit.pnd.common.util.Assert;
import site.bitinit.pnd.common.util.ResponseUtils;
import site.bitinit.pnd.web.config.SystemConstants;
import site.bitinit.pnd.web.service.ResourceService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * @author: john
 * @date: 2019/4/13
 */
@RestController
@RequestMapping(SystemConstants.API_VERSION)
public class ResourceController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/rs/config")
    public ResponseEntity getConfig(){
        return ResponseUtils.ok(resourceService.getConfig());
    }

    @PostMapping("/rs/preparation")
    public ResponseEntity prepareFileUpload(String clientId, String md5,
                                  Long size, long parentId, String fileName){
        return ResponseUtils.ok(resourceService.prepareFileUpload(clientId, md5, size, parentId, fileName));
    }

    @GetMapping("/rs/fingerPrint")
    public ResponseEntity resourceExists(String fingerPrint){
        return ResponseUtils.ok(resourceService.resourceExists(fingerPrint));
    }

    @PostMapping("/rs")
    public void resourceUpload(String clientId, Long resourceId, @RequestParam("file") MultipartFile file,
                                         HttpServletRequest request) throws IOException {
        Assert.notEmpty(clientId, "客户端id不能为空");
        Assert.notNull(resourceId, "资源id不能为空");
        Assert.notNull(file, "上传文件不能为空");

        resourceService.fileUpload(clientId, resourceId, file.getInputStream(), request);
    }

    @PutMapping("/rs/state")
    public ResponseEntity pauseFileUpload(String clientId, Long resourceId, String type){
        resourceService.changeResourceState(clientId, resourceId, type);
        return ResponseUtils.ok("");
    }

    @GetMapping("/rs/{resourceId}")
    public org.springframework.http.ResponseEntity<Resource> downloadFile(@PathVariable Long resourceId, String fileName, HttpServletRequest request) throws UnsupportedEncodingException {
        Resource resource = resourceService.loadResource(resourceId);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Objects.isNull(contentType)){
            contentType = "application/octet-stream";
        }

        return org.springframework.http.ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF-8"))
                .body(resource);
    }

    @ExceptionHandler
    public void clientAbortException(ClientAbortException e){
        logger.warn("client cancelled file download {}", e.getMessage());
    }
}
