package site.bitinit.pnd.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

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
}
