package site.bitinit.pnd.common.util;

import site.bitinit.pnd.common.exception.IllegalDataException;

/**
 * @author: john
 * @date: 2019/4/6
 */
public class Assert {

    public static void notEmpty(String str){
        notEmpty(str, "string can't be empty");
    }

    public static void notEmpty(String str, String msg){
        if (StringUtils.isBlank(str)){
            throw new IllegalDataException(msg);
        }
    }
}
