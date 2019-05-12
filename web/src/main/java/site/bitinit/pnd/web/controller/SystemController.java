package site.bitinit.pnd.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.bitinit.pnd.common.ResponseEntity;
import site.bitinit.pnd.common.util.ResponseUtils;
import site.bitinit.pnd.web.config.SystemConstants;
import site.bitinit.pnd.web.service.SystemService;

/**
 * @author: john
 * @date: 2019/5/6
 */
@RestController
@RequestMapping(SystemConstants.API_VERSION)
public class SystemController {
    @Autowired
    private SystemService systemService;

    @GetMapping("/sys")
    public ResponseEntity sysInfo(){
        return ResponseUtils.ok(systemService.sysInfo());
    }
}
