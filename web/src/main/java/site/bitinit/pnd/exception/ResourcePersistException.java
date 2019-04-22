package site.bitinit.pnd.exception;

import site.bitinit.pnd.common.exception.PndException;

/**
 * @author: john
 * @date: 2019/4/21
 */
public class ResourcePersistException extends PndException {
    public ResourcePersistException() {
    }

    public ResourcePersistException(String message) {
        super(message);
    }

    public ResourcePersistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourcePersistException(Throwable cause) {
        super(cause);
    }

    public ResourcePersistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
