package site.bitinit.pnd.web.config;

/**
 * @author: john
 * @date: 2019/4/3
 */
public class SystemConstants {

    public static final String API_VERSION = "v1";

    /**
     * 系统文件类型
     */
    public enum FileType{
        DEFAULT, FOLDER, PDF, COMPRESS_FILE, VIDEO, PICTURE;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}
