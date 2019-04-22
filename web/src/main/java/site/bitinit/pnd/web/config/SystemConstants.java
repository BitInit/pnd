package site.bitinit.pnd.web.config;

import site.bitinit.pnd.common.util.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author: john
 * @date: 2019/4/3
 */
public class SystemConstants {

    public static final String API_VERSION = "v1";
    public static final Map<String, FileType> fileTypeAccordingToSuffix = new HashMap<>();

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

    public static FileType getFileType(String fileName){
        String fileSuffix = CommonUtils.extractFileExtensionName(fileName);
        return fileTypeAccordingToSuffix.getOrDefault(fileSuffix, FileType.DEFAULT);
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

    static {
        // pdf
        fileTypeAccordingToSuffix.put(".pdf", FileType.PDF);

        // compress_file
        fileTypeAccordingToSuffix.put(".tar.gz", FileType.COMPRESS_FILE);
        fileTypeAccordingToSuffix.put(".zip", FileType.COMPRESS_FILE);

        // video
        fileTypeAccordingToSuffix.put(".mp4", FileType.VIDEO);
        fileTypeAccordingToSuffix.put(".flv", FileType.VIDEO);

        // picture
        fileTypeAccordingToSuffix.put(".png", FileType.PICTURE);
    }
}
