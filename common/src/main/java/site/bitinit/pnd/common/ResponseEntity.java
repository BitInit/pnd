package site.bitinit.pnd.common;

import site.bitinit.pnd.common.util.StringUtils;

/**
 * @author: john
 * @date: 2019/3/28
 */
public class ResponseEntity<T> {

    private String msg;
    private T data;

    public ResponseEntity() {
    }

    public ResponseEntity(String msg) {
        this.msg = msg;
        this.data = (T) StringUtils.EMPTY;
    }

    public ResponseEntity(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseEntity{" +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
