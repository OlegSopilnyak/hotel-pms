package oleg.sopilnyak.common.service.exception;

/**
 * Exception when cannot cancel agreement
 */
public class CannotCancelException  extends RuntimeException {
    public CannotCancelException() {
    }

    public CannotCancelException(String message) {
        super(message);
    }

    public CannotCancelException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotCancelException(Throwable cause) {
        super(cause);
    }

    public CannotCancelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}