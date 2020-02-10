package site.bitinit.pnd.web.exception;

/**
 * @author john
 * @date 2020-01-11
 */
public class PndException extends RuntimeException {
    private static final long serialVersionUID = -455084301379506105L;

    public PndException() {
        super();
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

    protected PndException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
