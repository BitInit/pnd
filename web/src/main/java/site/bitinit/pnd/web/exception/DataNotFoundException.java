package site.bitinit.pnd.web.exception;

/**
 * @author john
 * @date 2020-01-11
 */
public class DataNotFoundException extends PndException {
    private static final long serialVersionUID = -1731288043175679993L;

    public DataNotFoundException() {
        super();
    }

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotFoundException(Throwable cause) {
        super(cause);
    }

    protected DataNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
