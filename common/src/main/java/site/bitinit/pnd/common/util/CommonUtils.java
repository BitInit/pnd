package site.bitinit.pnd.common.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author: john
 * @date: 2019/4/6
 */
public class CommonUtils {

    public static String formatDate(){
        return formatDate(new Date());
    }

    public static String formatDate(Date date){
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDate(Date date, String format){
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(date);
    }

    public static String getResourceSubfolder(){
        return getResourceSubfolder(new Date());
    }

    public static String getResourceSubfolder(Date date){
        String[] basePaths = formatDate(date, "yyyy-MM").split("-");
        return basePaths[0] + File.separator + basePaths[1];
    }

    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String extractFileExtensionName(String fileName) {
        String[] split = fileName.split("\\.");
        String fileExtension = "";
        if (split.length > 1) {
            if (split.length > 0) {
                fileExtension = '.' + split[split.length - 1];
            }
        }
        return fileExtension;
    }

    private CommonUtils(){}
}
