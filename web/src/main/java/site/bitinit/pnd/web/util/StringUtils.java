package site.bitinit.pnd.web.util;

/**
 * @author john
 * @date 2020-01-11
 */
public class StringUtils {

    public static final String EMPTY = "";

    public static boolean hasLength(String str) {
        return str != null && !str.isEmpty();
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    private StringUtils() {}
}
