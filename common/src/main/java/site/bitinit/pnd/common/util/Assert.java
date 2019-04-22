package site.bitinit.pnd.common.util;

import site.bitinit.pnd.common.exception.IllegalDataException;

import java.util.Objects;

/**
 * @author: john
 * @date: 2019/4/6
 */
public class Assert {

    public static void notNull(Object obj){
        notNull(obj, "object can't be null");
    }

    public static void notNull(Object obj, String msg){
        if (Objects.isNull(obj)){
            throw new IllegalDataException(msg);
        }
    }

    public static void notEmpty(String str){
        notEmpty(str, "string can't be empty");
    }

    public static void notEmpty(String str, String msg){
        if (StringUtils.isBlank(str)){
            throw new IllegalDataException(msg);
        }
    }
}
