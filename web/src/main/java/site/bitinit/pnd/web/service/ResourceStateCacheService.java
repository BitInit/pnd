package site.bitinit.pnd.web.service;

import com.google.common.cache.*;
import org.springframework.stereotype.Service;
import site.bitinit.pnd.common.exception.IllegalDataException;
import site.bitinit.pnd.common.util.Assert;
import site.bitinit.pnd.web.model.PndResourceState;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author: john
 * @date: 2019/4/20
 */
@Service
public class ResourceStateCacheService {

    private final LoadingCache<String, ConcurrentHashMap<Long, PndResourceState>> resourceStateCache = CacheBuilder.newBuilder()
            .expireAfterAccess(30, TimeUnit.MINUTES)
            //TODO 缓存过期需要进行资源清除
            .build(new CacheLoader<String, ConcurrentHashMap<Long, PndResourceState>>() {
                @Override
                public ConcurrentHashMap<Long, PndResourceState> load(String s) {
                    return new ConcurrentHashMap<>(5);
                }
            });

    public void addResource(String clientId, Long resourceId, PndResourceState state){
        Assert.notNull(clientId, "clientId can't be null");
        Assert.notNull(resourceId, "resourceId can't be null");

        resourceStateCache.getUnchecked(clientId).putIfAbsent(resourceId, state);
    }

    public PndResourceState getResourceState(String clientId, Long resourceId){
        Assert.notNull(clientId, "clientId can't be null");
        Assert.notNull(resourceId, "resourceId can't be null");

        ConcurrentHashMap<Long, PndResourceState> ifPresent = resourceStateCache.getIfPresent(clientId);
        if (ifPresent == null || !ifPresent.containsKey(resourceId)){
            throw new IllegalDataException("文件上传前必须进行初始化");
        }
        return ifPresent.get(resourceId);
    }

    public void deleteResource(String clientId, Long resourceId){
        resourceStateCache.getUnchecked(clientId).remove(resourceId);
    }
}
