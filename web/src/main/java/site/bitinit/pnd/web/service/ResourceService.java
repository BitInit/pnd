package site.bitinit.pnd.web.service;

import site.bitinit.pnd.web.controller.dto.ResourceConfigDto;
import site.bitinit.pnd.web.controller.dto.ResourceUploadResponseDto;

import javax.servlet.AsyncContext;
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
     * @param fingerPrint
     * @param size
     * @param parentId
     * @param fileName
     * @return
     */
    Map<String, Object> prepareFileUpload(String clientId, String fingerPrint, long size, long parentId, String fileName);

    /**
     * 文件片段上传
     * @param clientId
     * @param resourceId
     * @param is
     */
    void fileUpload(String clientId, Long resourceId, InputStream is, AsyncContext context);
}
