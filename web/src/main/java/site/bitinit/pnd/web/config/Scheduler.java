package site.bitinit.pnd.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import site.bitinit.pnd.web.dao.ResourceDao;
import site.bitinit.pnd.web.model.PndResource;
import site.bitinit.pnd.web.utils.PathUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: john
 * @date: 2019/5/2
 */
@Component
public class Scheduler {

    private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);

    @Autowired
    private PathUtils pathUtils;
    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * 每天凌晨 2 点执行脏数据清理
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void clearDirtyData(){
        cleanResourceFile(cleanResourceTable());
    }

    /**
     * 若清除资源表数据成功，再清理脏文件
     * @param resources
     */
    private void cleanResourceFile(List<PndResource> resources) {
        resources.stream()
                .filter(p -> shouldDelete(p))
                .forEach(resource -> {
                    String subPath = resource.getPath() + File.separator + resource.getUuid();
                    String pathStr = pathUtils.getResourceAbsolutionPath(subPath);

                    File file = new File(pathStr);
                    if (file.exists()){
                        file.delete();
                        logger.info("clear resource {} success!", resource.getUuid());
                    }
                });
    }

    /**
     * 先通过事务清理资源表无效数据
     * @return
     */
    private List<PndResource> cleanResourceTable() {
        return transactionTemplate.execute(transactionStatus -> {
            List<PndResource> pndResources = resourceDao.findDirtyResources();
            List<Long> deleteIds = new ArrayList<>();
            pndResources.stream()
                    .filter(p -> shouldDelete(p))
                    .forEach(resource -> {
                        deleteIds.add(resource.getId());
                    });
            resourceDao.deleteBatch(deleteIds);
            return pndResources;
        });
    }

    private boolean shouldDelete(PndResource resource){
        if (System.currentTimeMillis() - resource.getGmtModified() >= RESOURCE_CLEAN_INTERVAL_TIME
                || resource.getLink() == 0){
            return true;
        }
        return false;
    }

    /**
     * 间隔时间 6h
     */
    private static final long RESOURCE_CLEAN_INTERVAL_TIME = 6 * 3600 * 1000;
}
