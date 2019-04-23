package site.bitinit.pnd.web.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import site.bitinit.pnd.common.util.CommonUtils;
import site.bitinit.pnd.common.util.StringUtils;
import site.bitinit.pnd.web.config.Properties;

import java.io.File;
import java.util.Date;

/**
 * @author: john
 * @date: 2019/4/23
 */
@Component
public class PathUtils implements ApplicationContextAware {

    public String getResourceAbsolutionPath(){
        return getResourceBasePath() + File.separator + getResourceBasePath();
    }

    public String getResourceAbsolutionPath(String subPath){
        return getResourceBasePath() + File.separator + subPath;
    }

    public String getResourceBasePath(){
        String basePath = properties.getDataDir();
        if (StringUtils.isBlank(properties.getDataDir())){
            basePath = properties.getPndHome() + File.separator + "data";
        }
        return basePath;
    }

    public String getResourceSubfolder(){
        return getResourceSubfolder(new Date());
    }

    public String getResourceSubfolder(Date date){
        String[] basePaths = CommonUtils.formatDate(date, "yyyy-MM").split("-");
        return "resource" + File.separator +basePaths[0] +
                File.separator + basePaths[1];
    }

    private PathUtils(){}

    private Properties properties;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.properties = applicationContext.getBean(Properties.class);
    }
}
