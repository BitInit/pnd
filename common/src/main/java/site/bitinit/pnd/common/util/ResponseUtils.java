package site.bitinit.pnd.common.util;

import site.bitinit.pnd.common.ResponseEntity;

/**
 * @author: john
 * @date: 2019/4/15
 */
public class ResponseUtils {

    public static <T> ResponseEntity<T> ok(T data){
        return ok("success", data);
    }

    public static <T> ResponseEntity<T> ok(String msg, T data){
        return new ResponseEntity<>(msg, data);
    }

    public static <T> ResponseEntity<T> badRequest(T data){
        return badRequest("bad request", data);
    }

    public static <T> ResponseEntity<T> badRequest(String msg){
        return badRequest(msg, null);
    }

    public static <T> ResponseEntity<T> badRequest(String msg, T data){
        return new ResponseEntity<>(msg, data);
    }

    public static <T> ResponseEntity<T> serverError(String msg){
        return new ResponseEntity<>("server error", null);
    }

    public static <T> ResponseEntity<T> serverError(String msg, T data){
        return new ResponseEntity<>(msg, data);
    }
}
