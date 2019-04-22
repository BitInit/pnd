package site.bitinit.pnd.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import site.bitinit.pnd.common.EnvironmentConstants;
import site.bitinit.pnd.common.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @author: john
 * @date: 2019/3/25
 */
@Configuration
public class Properties {

    public static final String DEFAULT_PND_HOME = getSystemProperty("user.home", "") + File.separator + "pnd";

    private String pndHome;
    private String dataDir;
    private boolean isUseMysql;
    private String dbURL;
    private String dbUsername;
    private String dbPassword;
    private int dbInitialSize;
    private int dbMaxActive;
    private int dbMaxIdle;

    private String embedDBDriver = "org.apache.derby.jdbc.EmbeddedDriver";
    private String embedDBBaseDir = "db";
    private String embedDBUsername = "pnd";
    private String embedDBPassword = "pnd";

    private Integer maxConcurrentUploadNumbers;
    private Long chunkByteSize;

    @Autowired
    private Environment env;

    @PostConstruct
    public void init(){
        // environment properties
        setPndHome(getEnvProperty(EnvironmentConstants.PND_HOME, DEFAULT_PND_HOME));
        setDataDir(getEnvProperty(EnvironmentConstants.PND_DATA_DIR, ""));
        setUseMysql(getEnvProperty(EnvironmentConstants.IS_USE_MYSQL, "false"));
        setDbURL(env.getProperty(EnvironmentConstants.DB_URL));
        setDbUsername(env.getProperty(EnvironmentConstants.DB_USERNAME));
        setDbPassword(env.getProperty(EnvironmentConstants.DB_PASSWORD));
        setDbInitialSize(Integer.parseInt(getEnvProperty(EnvironmentConstants.DB_INITIAL_SIZE, "10")));
        setDbMaxActive(Integer.parseInt(getEnvProperty(EnvironmentConstants.DB_MAX_ACTIVE, "20")));
        setDbMaxIdle(Integer.parseInt(getEnvProperty(EnvironmentConstants.DB_MAX_IDLE, "50")));

        setMaxConcurrentUploadNumbers(Integer.parseInt(getEnvProperty(EnvironmentConstants.MAX_CONCURRENT_UPLOAD_NUMBERS, "4")));
        setChunkByteSize(Long.parseLong(getEnvProperty(EnvironmentConstants.CHUNK_BYTES_SIZE, "10485760")));

        // system environment variable
        setPndHome(getSystemProperty(EnvironmentConstants.PND_HOME, getPndHome()));
        setDataDir(getSystemProperty(EnvironmentConstants.PND_DATA_DIR, getDataDir()));
    }

    public String getPndHome() {
        return pndHome;
    }

    public void setPndHome(String pndHome) {
        this.pndHome = pndHome;
    }

    public String getDataDir() {
        return dataDir;
    }

    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }

    public boolean isUseMysql() {
        return isUseMysql;
    }

    public void setUseMysql(String useMysql) {
        isUseMysql = Boolean.valueOf(useMysql);
    }

    public String getDbURL() {
        return dbURL;
    }

    public void setDbURL(String dbURL) {
        this.dbURL = dbURL;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public int getDbInitialSize() {
        return dbInitialSize;
    }

    public void setDbInitialSize(int dbInitialSize) {
        this.dbInitialSize = dbInitialSize;
    }

    public int getDbMaxActive() {
        return dbMaxActive;
    }

    public void setDbMaxActive(int dbMaxActive) {
        this.dbMaxActive = dbMaxActive;
    }

    public int getDbMaxIdle() {
        return dbMaxIdle;
    }

    public void setDbMaxIdle(int dbMaxIdle) {
        this.dbMaxIdle = dbMaxIdle;
    }

    public String getEmbedDBDriver() {
        return embedDBDriver;
    }

    public void setEmbedDBDriver(String embedDBDriver) {
        this.embedDBDriver = embedDBDriver;
    }

    public String getEmbedDBBaseDir() {
        return embedDBBaseDir;
    }

    public void setEmbedDBBaseDir(String embedDBBaseDir) {
        this.embedDBBaseDir = embedDBBaseDir;
    }

    public String getEmbedDBUsername() {
        return embedDBUsername;
    }

    public void setEmbedDBUsername(String embedDBUsername) {
        this.embedDBUsername = embedDBUsername;
    }

    public String getEmbedDBPassword() {
        return embedDBPassword;
    }

    public void setEmbedDBPassword(String embedDBPassword) {
        this.embedDBPassword = embedDBPassword;
    }

    public Integer getMaxConcurrentUploadNumbers() {
        return maxConcurrentUploadNumbers;
    }

    public void setMaxConcurrentUploadNumbers(Integer maxConcurrentUploadNumbers) {
        this.maxConcurrentUploadNumbers = maxConcurrentUploadNumbers;
    }

    public Long getChunkByteSize() {
        return chunkByteSize;
    }

    public void setChunkByteSize(Long chunkByteSize) {
        this.chunkByteSize = chunkByteSize;
    }

    private String getEnvProperty(String key, String defaultVal){
        String val = env.getProperty(key, "");
        if (StringUtils.isBlank(val)){
            return defaultVal;
        }
        return val;
    }

    private static String getSystemProperty(String key, String defultVal){
        String val = System.getProperty(key, "");
        if (StringUtils.isBlank(val)){
            return defultVal;
        }
        return val;
    }
}
