package site.bitinit.pnd.exception;

import site.bitinit.pnd.common.exception.PndException;

/**
 * @author: john
 * @date: 2019/4/6
 */
public class SystemDealFailException extends PndException {

    public SystemDealFailException() {
    }

    public SystemDealFailException(String message) {
        super(message);
    }

    public SystemDealFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemDealFailException(Throwable cause) {
        super(cause);
    }

    public SystemDealFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
