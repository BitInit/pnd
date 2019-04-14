package site.bitinit.pnd.web.config;

/**
 * @author: john
 * @date: 2019/4/3
 */
public class SystemConstants {

    public static final String API_VERSION = "v1";

    public enum FileType{
        /**
         * 文件类型，例如FOLDER表示文件夹
         */
        DEFAULT, FOLDER, PDF, COMPRESS_FILE, VIDEO, PICTURE;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * 资源的状态
     */
    public enum ResourceState {
        /**
         * 资源已准备好，可以使用
         */
        succeeded,
        /**
         * 资源正在准备中，还未完成
         */
        pending
    }
}
