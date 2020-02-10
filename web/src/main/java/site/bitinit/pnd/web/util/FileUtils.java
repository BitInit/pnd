package site.bitinit.pnd.web.util;

import site.bitinit.pnd.web.config.FileType;
import site.bitinit.pnd.web.exception.DataFormatException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author john
 * @date 2020-01-11
 */
public class FileUtils {

    /**
     * 根据文件名获取文件类型
     * @param fileName 文件名
     * @return FileType
     */
    public static FileType getFileType(String fileName){
        String extensionName = extractFileExtensionName(fileName);
        return fileTypeMap.getOrDefault(extensionName, FileType.DEFAULT);
    }

    public static void checkFileType(String fileType){
        try {
            FileType.valueOf(fileType.toUpperCase());
        } catch (Exception e){
            throw new DataFormatException("文件类型不正确");
        }
    }

    public static boolean equals(String type, FileType fileType){
        return fileType.toString().equals(type);
    }

    public static boolean validFileName(String fileName){
        return fileName.length() <= 100;
    }

    public static void checkFileName(String fileName){
        if (!validFileName(fileName)){
            throw new DataFormatException("文件名最大长度为100");
        }
    }

    public static String extractFileExtensionName(String fileName) {
        String[] split = fileName.split("\\.");
        String fileExtension = "";
        if (split.length > 1) {
            fileExtension = '.' + split[split.length - 1];
        }
        return fileExtension;
    }

    private static Map<String, FileType> fileTypeMap = new HashMap<>();

    static {
        // pdf
        fileTypeMap.put(".pdf", FileType.PDF);

        // compress_file
        fileTypeMap.put(".tar.gz", FileType.COMPRESS_FILE);
        fileTypeMap.put(".zip", FileType.COMPRESS_FILE);
        fileTypeMap.put(".7z", FileType.COMPRESS_FILE);
        fileTypeMap.put(".rar", FileType.COMPRESS_FILE);

        // video
        fileTypeMap.put(".mp4", FileType.VIDEO);
        fileTypeMap.put(".flv", FileType.VIDEO);
        fileTypeMap.put(".rmvb", FileType.VIDEO);
        fileTypeMap.put(".avi", FileType.VIDEO);
        fileTypeMap.put(".mkv", FileType.VIDEO);

        // audio
        fileTypeMap.put(".mp3", FileType.AUDIO);

        // picture
        fileTypeMap.put(".png", FileType.PICTURE);
        fileTypeMap.put(".jpg", FileType.PICTURE);
        fileTypeMap.put(".jpeg", FileType.PICTURE);
        fileTypeMap.put(".gif", FileType.PICTURE);
        fileTypeMap.put(".ico", FileType.PICTURE);

        // doc
        fileTypeMap.put(".doc", FileType.DOC);
        fileTypeMap.put(".docx", FileType.DOC);

        // txt
        fileTypeMap.put(".txt", FileType.TXT);

        // ppt
        fileTypeMap.put(".ppt", FileType.PPT);
        fileTypeMap.put(".pptx", FileType.PPT);

        // torrent
        fileTypeMap.put(".torrent", FileType.TORRENT);

        // web
        fileTypeMap.put(".html", FileType.WEB);
        fileTypeMap.put(".htm", FileType.WEB);

        // code
        fileTypeMap.put(".js", FileType.CODE);
        fileTypeMap.put(".json", FileType.CODE);
        fileTypeMap.put(".java", FileType.CODE);
        fileTypeMap.put(".c", FileType.CODE);
        fileTypeMap.put(".cpp", FileType.CODE);
        fileTypeMap.put(".h", FileType.CODE);
        fileTypeMap.put(".py", FileType.CODE);
        fileTypeMap.put(".go", FileType.CODE);
        fileTypeMap.put(".vue", FileType.CODE);
    }

    private FileUtils(){}
}
