package site.bitinit.pnd.common.exception;

/**
 * @author: john
 * @date: 2019/4/13
 */
public class ResourceDealException extends Exception {

    public ResourceDealException() {
    }

    public ResourceDealException(String message) {
        super(message);
    }

    public ResourceDealException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceDealException(Throwable cause) {
        super(cause);
    }

    public ResourceDealException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
