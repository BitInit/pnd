package site.bitinit.pnd.web.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import site.bitinit.pnd.common.ResponseEntity;
import site.bitinit.pnd.common.exception.PndException;
import site.bitinit.pnd.common.util.Assert;
import site.bitinit.pnd.common.util.CommonUtils;
import site.bitinit.pnd.common.util.ResponseUtils;
import site.bitinit.pnd.common.util.StringUtils;
import site.bitinit.pnd.exception.ResourcePersistException;
import site.bitinit.pnd.web.config.Properties;
import site.bitinit.pnd.web.config.SystemConstants;
import site.bitinit.pnd.web.controller.dto.ResourceConfigDto;
import site.bitinit.pnd.web.controller.dto.ResourceUploadResponseDto;
import site.bitinit.pnd.web.dao.ResourceDao;
import site.bitinit.pnd.web.model.PndResource;
import site.bitinit.pnd.web.model.PndResourceState;
import site.bitinit.pnd.web.service.PersistResourceService;
import site.bitinit.pnd.web.service.ResourceProcessCallback;
import site.bitinit.pnd.web.service.ResourceService;
import site.bitinit.pnd.web.service.ResourceStateCacheService;

import javax.servlet.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author: john
 * @date: 2019/4/19
 */
@Service
public class ResourceServiceImpl implements ResourceService {
    private static final Logger logger = LoggerFactory.getLogger(ResourceService.class);

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private Properties properties;
    @Autowired
    private ResourceStateCacheService cacheService;
    @Autowired
    private PersistResourceService persistResourceService;

    @Override
    public ResourceConfigDto getConfig() {
        ResourceConfigDto configDto = new ResourceConfigDto();
        configDto.setMaxConcurrentUploadNumbers(properties.getMaxConcurrentUploadNumbers());
        configDto.setChunkByteSize(properties.getChunkByteSize());
        configDto.setClientId(CommonUtils.uuid());
        return configDto;
    }

    @Override
    public Map<String, Object> resourceExists(String fingerPrint) {
        Assert.notEmpty(fingerPrint, "fileFingerPrint can't be empty");
        Map<String, Object> result = new HashMap<>(2);
        PndResource resource = resourceDao.findByFingerPrint(fingerPrint, SystemConstants.ResourceState.succeeded);
        if (Objects.isNull(resource)){
            result.put("exist", false);
        } else {
            result.put("exist", true);
            result.put("resourceId", resource.getId());
        }
        return result;
    }

    @Override
    public Map<String, Object> prepareFileUpload(String clientId, String fingerPrint, long size, long parentId, String fileName) {
        PndResource resource = new PndResource();
        resource.setFingerPrint(fingerPrint);
        resource.setStatus(SystemConstants.ResourceState.pending.toString());
        resource.setUuid(CommonUtils.uuid());
        long currentTime = System.currentTimeMillis();
        resource.setGmtCreate(currentTime);
        resource.setGmtModified(currentTime);
        resource.setLink(0);
        String resourceSubfolder = CommonUtils.getResourceSubfolder();
        String filePath = properties.getDataDir() + File.separator + "resource" + File.separator + resourceSubfolder;
        if (StringUtils.isBlank(properties.getDataDir())){
            filePath = properties.getPndHome() + File.separator + "data" + File.separator + "resource" + File.separator + resourceSubfolder;
        }
        resource.setPath(filePath);
        resource.setSize(size);

        Long id = transactionTemplate.execute(new TransactionCallback<Long>() {
            @Override
            public Long doInTransaction(TransactionStatus transactionStatus) {
                long id = resourceDao.save(resource);
                resource.setId(id);

                File pathFile = new File(resource.getPath());
                File file = new File(pathFile, resource.getUuid() + CommonUtils.extractFileExtensionName(fileName));
                try {
                    if (!pathFile.exists()){
                        pathFile.mkdirs();
                    }
                    file.createNewFile();
                    PndResourceState state = new PndResourceState(resource, fileName, parentId, file);
                    cacheService.addResource(clientId, id, state);
                } catch (IOException e) {
                    logger.error("create " + fileName + " error");
                    throw new PndException();
                }
                return id;
            }
        });

        Map<String, Object> result =new HashMap<>(1);
        result.put("resourceId", id);
        return result;
    }

    @Override
    public void fileUpload(String clientId, Long resourceId
            , InputStream is, AsyncContext context) {
        PndResourceState state = cacheService.getResourceState(clientId, resourceId);

        context.addListener(new UploadAsyncListener(state));
        final ResourceProcessCallback processCallback = new ResourceProcessCallback() {
            @Override
            public void onStart(PndResourceState state) {
            }

            @Override
            public void onComplete(PndResourceState state) {
                context.complete();
            }

            @Override
            public void onSuccess(PndResourceState state) {
                //TODO 存储file，清除缓存
                close(state);
                context.complete();
            }

            @Override
            public void onError(Exception e) {
                // TODO 处理错误
                context.complete();
            }

            private void close(PndResourceState state){
                if (state.getOutputStream() != null){
                    try {
                        state.getOutputStream().close();
                    } catch (IOException e) {
                    }
                }
            }
        };

        persistResourceService.process(state, is, processCallback);
    }

    class UploadAsyncListener implements AsyncListener{

        private PndResourceState state;

        public UploadAsyncListener(PndResourceState state) {
            this.state = state;
        }

        @Override
        public void onComplete(AsyncEvent event) throws IOException {
            ResponseEntity entity;
            if (state.isPaused()){
                entity = ResponseUtils.ok(ResourceUploadResponseDto.paused(state.getFinishedUploadBytes()));
            } else {
                entity = ResponseUtils.ok(ResourceUploadResponseDto.success(state.getFinishedUploadBytes()));
            }

            ServletResponse response = event.getSuppliedResponse();
            response.setContentType("application/json; charset=utf8");
            ServletOutputStream outputStream = response.getOutputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(outputStream, entity);
            outputStream.flush();
        }

        @Override
        public void onTimeout(AsyncEvent event) throws IOException {
        }

        @Override
        public void onError(AsyncEvent event) throws IOException {
        }

        @Override
        public void onStartAsync(AsyncEvent event) throws IOException {
        }
    }
}
