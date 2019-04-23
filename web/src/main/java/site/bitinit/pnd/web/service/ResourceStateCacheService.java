package site.bitinit.pnd.web.service;

import com.google.common.cache.*;
import org.springframework.stereotype.Service;
import site.bitinit.pnd.common.exception.IllegalDataException;
import site.bitinit.pnd.common.util.Assert;
import site.bitinit.pnd.web.model.PndResource;
import site.bitinit.pnd.web.model.PndResourceState;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: john
 * @date: 2019/4/20
 */
@Service
public class ResourceStateCacheService {

    private final LoadingCache<String, PndResourceState> resourceStateCache = CacheBuilder.newBuilder()
            .expireAfterAccess(30, TimeUnit.MINUTES)
            .removalListener(new RemovalListener<String, PndResourceState>() {
                @Override
                public void onRemoval(RemovalNotification<String, PndResourceState> removalNotification) {
                    PndResourceState state = removalNotification.getValue();
                    OutputStream os = state.getOutputStream();
                    if (!Objects.isNull(os)){
                        try {
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            })
            .build(new CacheLoader<String, PndResourceState>() {
                @Override
                public PndResourceState load(String s) {
                    return new PndResourceState();
                }
            });

    public void addResource(String clientId, Long resourceId, PndResourceState.PndResourceStateBuilder stateBuilder){
        String cacheKey = getCacheKey(clientId, resourceId);
        stateBuilder.build(resourceStateCache.getUnchecked(cacheKey));
    }

    public PndResourceState getResourceState(String clientId, Long resourceId){
        String cacheKey = getCacheKey(clientId, resourceId);
        PndResourceState ifPresent = resourceStateCache.getIfPresent(cacheKey);
        if (ifPresent == null){
            throw new IllegalDataException("资源id为 " + resourceId + " 未进行上传前初始化");
        }
        return ifPresent;
    }

    public void deleteResource(String clientId, Long resourceId){
        String cacheKey = getCacheKey(clientId, resourceId);
        resourceStateCache.invalidate(cacheKey);
    }

    String getCacheKey(String clientId, Long resourceId){
        Assert.notNull(clientId, "clientId can't be null");
        Assert.notNull(resourceId, "resourceId can't be null");

        return clientId + "-" + resourceId;
    }
}
