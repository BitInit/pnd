package site.bitinit.pnd.common.exception;

/**
 * @author: john
 * @date: 2019/4/2
 */
public class PndException extends RuntimeException {

    public PndException() {
    }

    public PndException(String message) {
        super(message);
    }

    public PndException(String message, Throwable cause) {
        super(message, cause);
    }

    public PndException(Throwable cause) {
        super(cause);
    }

    public PndException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
