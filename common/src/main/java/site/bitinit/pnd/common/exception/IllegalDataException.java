package site.bitinit.pnd.common.exception;

/**
 * @author: john
 * @date: 2019/4/6
 */
public class IllegalDataException extends PndException {

    public IllegalDataException() {
    }

    public IllegalDataException(String message) {
        super(message);
    }

    public IllegalDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalDataException(Throwable cause) {
        super(cause);
    }

    public IllegalDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
