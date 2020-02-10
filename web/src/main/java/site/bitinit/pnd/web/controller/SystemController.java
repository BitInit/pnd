package site.bitinit.pnd.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.bitinit.pnd.web.Constants;
import site.bitinit.pnd.web.controller.dto.ResponseDto;
import site.bitinit.pnd.web.controller.dto.SystemInfoDto;
import site.bitinit.pnd.web.service.SystemService;

/**
 * @author john
 * @date 2020-02-10
 */
@RestController
@RequestMapping(Constants.API_VERSION)
public class SystemController {

    private final SystemService systemService;

    @Autowired
    public SystemController(SystemService systemService) {
        this.systemService = systemService;
    }

    @GetMapping("/system")
    public ResponseEntity<ResponseDto> getSystemInfo() {
        return ResponseEntity.ok(ResponseDto.success(systemService.systemInfo()));
    }
}
