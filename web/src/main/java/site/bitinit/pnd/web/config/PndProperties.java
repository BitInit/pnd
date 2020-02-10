package site.bitinit.pnd.web.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import site.bitinit.pnd.web.Constants;
import site.bitinit.pnd.web.util.StringUtils;
import site.bitinit.pnd.web.util.Utils;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @author john
 * @date 2020-01-10
 */
@Getter
@Setter
@org.springframework.context.annotation.Configuration
public class PndProperties {

    /**
     * mysql config
     */
    private boolean useMysql;
    private String mysqlUrl;
    private String mysqlUsername;
    private String mysqlPassword;
    private String mysqlDriver = "com.mysql.cj.jdbc.Driver";

    private String embedDbDriver = "org.apache.derby.jdbc.EmbeddedDriver";
    private String embedDbUrl;
    private String embedDbUsername = "pnd";
    private String embedDbPassword = "pnd";

    private String pndHome;
    private String pndDataDir;

    private String maxFileUploadSize;
    private String maxRequestSize;

    private final Environment env;

    @Autowired
    public PndProperties(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void init() {
        setPndHome(getEnvProperty(Constants.PND_HOME, getSystemProperty("user.home", StringUtils.EMPTY) + File.separator + "pnd"));
        setPndDataDir(getPndHome() + File.separator + "data");
        setEmbedDbUrl("jdbc:derby:" + getPndDataDir() + File.separator + "db;create=true");

        setUseMysql(Boolean.valueOf(getEnvProperty(Constants.USE_MYSQL, "false")));
        setMysqlUrl(getEnvProperty(Constants.MYSQL_URL, StringUtils.EMPTY));
        setMysqlUsername(getEnvProperty(Constants.MYSQL_USERNAME, StringUtils.EMPTY));
        setMysqlPassword(getEnvProperty(Constants.MYSQL_PASSWORD, StringUtils.EMPTY));

        setMaxFileUploadSize(getEnvProperty(Constants.MAX_FILE_UPLOAD_SIZE, "10MB"));
        setMaxRequestSize(getEnvProperty(Constants.MAX_REQUEST_SIZE, "12MB"));
    }

    public String getBasicResourcePath() {
        String basicPath = getPndDataDir() + File.separator + "resources";
        Utils.createFolders(basicPath);
        return basicPath;
    }

    public String getResourceTmpDir() {
        String resourceTmpPath = getBasicResourcePath() + File.separator + "tmp";
        Utils.createFolders(resourceTmpPath);
        return resourceTmpPath;
    }

    private String getEnvProperty(String key, String defaultVal){
        String val = env.getProperty(key, "");
        if (StringUtils.isBlank(val)){
            return defaultVal;
        }
        return val;
    }

    private static String getSystemProperty(String key, String defaultVal){
        String val = System.getProperty(key, "");
        if (StringUtils.isBlank(val)){
            return defaultVal;
        }
        return val;
    }
}
