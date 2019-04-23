package site.bitinit.pnd.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.bitinit.pnd.common.ResponseEntity;
import site.bitinit.pnd.common.util.Assert;
import site.bitinit.pnd.common.util.ResponseUtils;
import site.bitinit.pnd.web.config.SystemConstants;
import site.bitinit.pnd.web.service.ResourceService;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author: john
 * @date: 2019/4/13
 */
@RestController
@RequestMapping(SystemConstants.API_VERSION)
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/rs/config")
    public ResponseEntity getConfig(){
        return ResponseUtils.ok(resourceService.getConfig());
    }

    @PostMapping("/rs/preparation")
    public ResponseEntity prepareFileUpload(String clientId, String fileFingerPrint,
                                  long size, long parentId, String fileName){
        return ResponseUtils.ok(resourceService.prepareFileUpload(clientId, fileFingerPrint, size, parentId, fileName));
    }

    @GetMapping("/rs/fingerPrint")
    public ResponseEntity resourceExists(String fingerPrint){
        System.out.println(fingerPrint);
        return ResponseUtils.ok(resourceService.resourceExists(fingerPrint));
    }

    @PostMapping("/rs/upload")
    public void resourceUpload(String clientId, Long resourceId, MultipartFile file,
                                         HttpServletRequest request) throws IOException {
        Assert.notEmpty(clientId, "客户端id不能为空");
        Assert.notNull(resourceId, "资源id不能为空");
        Assert.notNull(file, "上传文件不能为空");

        resourceService.fileUpload(clientId, resourceId, file.getInputStream(), request);
    }

    @PutMapping("/rs")
    public ResponseEntity pauseFileUpload(String client, Long resourceId){
        resourceService.pauseResource(client, resourceId);
        return ResponseUtils.ok("");
    }
}
