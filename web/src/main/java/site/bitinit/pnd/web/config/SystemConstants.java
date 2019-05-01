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
        DEFAULT, FOLDER, PDF, COMPRESS_FILE,
        VIDEO, AUDIO, PICTURE, DOC, PPT, TXT,
        TORRENT, WEB, CODE;

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
        fileTypeAccordingToSuffix.put(".7z", FileType.COMPRESS_FILE);
        fileTypeAccordingToSuffix.put(".rar", FileType.COMPRESS_FILE);

        // video
        fileTypeAccordingToSuffix.put(".mp4", FileType.VIDEO);
        fileTypeAccordingToSuffix.put(".flv", FileType.VIDEO);
        fileTypeAccordingToSuffix.put(".rmvb", FileType.VIDEO);
        fileTypeAccordingToSuffix.put(".avi", FileType.VIDEO);
        fileTypeAccordingToSuffix.put(".mkv", FileType.PICTURE);

        // audio
        fileTypeAccordingToSuffix.put(".mp3", FileType.AUDIO);

        // picture
        fileTypeAccordingToSuffix.put(".png", FileType.PICTURE);
        fileTypeAccordingToSuffix.put(".jpg", FileType.PICTURE);
        fileTypeAccordingToSuffix.put(".jpeg", FileType.PICTURE);
        fileTypeAccordingToSuffix.put(".gif", FileType.PICTURE);
        fileTypeAccordingToSuffix.put(".ico", FileType.PICTURE);

        // doc
        fileTypeAccordingToSuffix.put(".doc", FileType.DOC);
        fileTypeAccordingToSuffix.put(".docx", FileType.DOC);

        // txt
        fileTypeAccordingToSuffix.put(".txt", FileType.TXT);

        // ppt
        fileTypeAccordingToSuffix.put(".ppt", FileType.PPT);
        fileTypeAccordingToSuffix.put(".pptx", FileType.PPT);

        // torrent
        fileTypeAccordingToSuffix.put(".torrent", FileType.TORRENT);

        // web
        fileTypeAccordingToSuffix.put(".html", FileType.WEB);
        fileTypeAccordingToSuffix.put(".htm", FileType.WEB);

        // code
        fileTypeAccordingToSuffix.put(".js", FileType.CODE);
        fileTypeAccordingToSuffix.put(".json", FileType.CODE);
        fileTypeAccordingToSuffix.put(".java", FileType.CODE);
        fileTypeAccordingToSuffix.put(".c", FileType.CODE);
        fileTypeAccordingToSuffix.put(".cpp", FileType.CODE);
        fileTypeAccordingToSuffix.put(".h", FileType.CODE);
        fileTypeAccordingToSuffix.put(".py", FileType.CODE);
        fileTypeAccordingToSuffix.put(".go", FileType.CODE);
    }
}
