package site.bitinit.pnd.common;

/**
 * @author: john
 * @date: 2019/3/25
 */
public class EnvironmentConstants {

    /**
     * 项目根目录
     */
    public static final String PND_HOME = "pnd.home";

    /**
     * 数据存储根目录
     */
    public static final String PND_DATA_DIR = "pnd.data.dir";

    /**
     * 是否使用MySQL，如果设置为true则屏蔽系统内置derby数据库
     */
    public static final String IS_USE_MYSQL = "pnd.useMysql";

    /**
     * MySQL url
     */
    public static final String DB_URL = "pnd.mysql.url";

    /**
     * MySQL username
     */
    public static final String DB_USERNAME = "pnd.mysql.username";

    /**
     * MySQL password
     */
    public static final String DB_PASSWORD = "pnd.mysql.password";

    public static final String DB_INITIAL_SIZE = "pnd.mysql.initialSize";
    public static final String DB_MAX_ACTIVE = "pnd.mysql.maxActive";
    public static final String DB_MAX_IDLE = "pnd.mysql.maxIdle";

    public static final String MAX_CONCURRENT_UPLOAD_NUMBERS = "pnd.fileUpload.maxConcurrentUploadNumbers";
    public static final String CHUNK_BYTES_SIZE = "pnd.fileUpload.chunkBytesSize";
}
