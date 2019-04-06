package site.bitinit.pnd.common.util;

import site.bitinit.pnd.common.ResponseEntity;

/**
 * @author: john
 * @date: 2019/3/28
 */
public class ResponseUtils {

    public static final int OK = 200;
    public static final int BAD_REQUEST = 400;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int SERVER_ERROR = 500;

    public static <T> ResponseEntity<T> ok(T data){
        return ok("success", data);
    }

    public static <T> ResponseEntity<T> ok(String msg, T data){
        return new ResponseEntity<>(OK, msg, data);
    }

    public static <T> ResponseEntity<T> badRequest(String msg){
        return badRequest(msg, null);
    }

    public static <T> ResponseEntity<T> badRequest(String msg, T data){
        return new ResponseEntity<>(BAD_REQUEST, msg, data);
    }

    public static <T> ResponseEntity<T> notFound(String msg){
        return notFound(msg, null);
    }

    public static <T> ResponseEntity<T> notFound(String msg, T data){
        return new ResponseEntity<>(NOT_FOUND, msg, data);
    }

    public static <T> ResponseEntity<T> serverError(String msg){
        return serverError(msg, null);
    }

    public static <T> ResponseEntity<T> serverError(String msg, T data){
        return new ResponseEntity<>(SERVER_ERROR, msg, data);
    }
}
