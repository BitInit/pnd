package site.bitinit.pnd.web.service;

import site.bitinit.pnd.web.controller.dto.SystemInfoDto;

/**
 * @author john
 * @date 2020-02-10
 */
public interface SystemService {

    /**
     * 系统信息
     * @return SystemInfoDto
     */
    SystemInfoDto systemInfo();
}
