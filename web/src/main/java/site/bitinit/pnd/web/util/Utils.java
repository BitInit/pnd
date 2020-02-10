package site.bitinit.pnd.web.util;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author john
 * @date 2020-01-27
 */
public class Utils {

    public static void createFolders(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void deleteFile(File file) {
        if (!file.isDirectory()) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            for (File f: files) {
                deleteFile(f);
            }
            file.delete();
        }
    }

    public static void deleteFile(String path) {
        deleteFile(new File(path));
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private Utils(){}
}
