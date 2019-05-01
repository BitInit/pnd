package site.bitinit.pnd.web.service;

import site.bitinit.pnd.web.config.SystemConstants;
import site.bitinit.pnd.web.controller.dto.ResourceConfigDto;
import site.bitinit.pnd.web.controller.dto.ResourceUploadResponseDto;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Map;

/**
 * @author: john
 * @date: 2019/4/19
 */
public interface ResourceService {

    /**
     * 客户端获取配置和clientId
     * @return
     */
    ResourceConfigDto getConfig();

    /**
     * 根据fingerPrint判断该资源是否存在
     * @param fingerPrint
     * @return
     */
    Map<String, Object> resourceExists(String fingerPrint);

    /**
     * 预处理文件上传
     * @param clientId
     * @param md5
     * @param size
     * @param parentId
     * @param fileName
     * @return
     */
    Map<String, Object> prepareFileUpload(String clientId, String md5, Long size, Long parentId, String fileName);

    /**
     * 更新资源状态
     * @param resourceId
     * @param resourceState
     */
    void updateResourceState(long resourceId, SystemConstants.ResourceState resourceState);

    /**
     * 文件片段上传
     * @param clientId
     * @param resourceId
     * @param is
     * @param request
     */
    void fileUpload(String clientId, Long resourceId, InputStream is, HttpServletRequest request);

    /**
     * 中断/恢复 文件上传
     * @param clientId
     * @param resourceId
     * @param type
     */
    void changeResourceState(String clientId, Long resourceId, String type);

}
