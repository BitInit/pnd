package site.bitinit.pnd.web.exception;

/**
 * @author john
 * @date 2020-01-11
 */
public class DataFormatException extends PndException {
    private static final long serialVersionUID = 586909787278516210L;

    public DataFormatException() {
        super();
    }

    public DataFormatException(String message) {
        super(message);
    }

    public DataFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataFormatException(Throwable cause) {
        super(cause);
    }

    protected DataFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
