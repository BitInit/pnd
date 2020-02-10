package site.bitinit.pnd.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import site.bitinit.pnd.web.util.StringUtils;

import java.util.Collections;

/**
 * @author john
 * @date 2020-01-05
 */
@Setter
@Getter
@AllArgsConstructor
public class ResponseDto {

    private String msg;
    private Object data;
    private Object extra;

    public static ResponseDto success(){
        return success(Collections.EMPTY_LIST);
    }

    public static ResponseDto success(Object data){
        return success(data, Collections.EMPTY_LIST);
    }

    public static ResponseDto success(Object data, Object extra){
        return new ResponseDto("success", data, extra);
    }

    public static ResponseDto fail(String msg){
        return fail(msg, Collections.EMPTY_LIST);
    }

    public static ResponseDto fail(Object data){
        return fail("fail", data);
    }

    public static ResponseDto fail(String msg, Object data){
        return fail(msg, data, Collections.EMPTY_LIST);
    }

    public static ResponseDto fail(String msg, Object data, Object extra){
        return new ResponseDto(msg, data, extra);
    }
}
