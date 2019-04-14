package site.bitinit.pnd.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.bitinit.pnd.web.config.SystemConstants;

/**
 * @author: john
 * @date: 2019/4/13
 */
@RestController
@RequestMapping(SystemConstants.API_VERSION)
public class ResourceController {

    @PostMapping("/resource/preparation")
    public void prepareFileUpload(){

    }
}
