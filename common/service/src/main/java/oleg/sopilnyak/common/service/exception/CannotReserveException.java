package oleg.sopilnyak.common.service.exception;

/**
 * Exception occurred if system cannot satisfy reservation request
 */
public class CannotReserveException extends RuntimeException {
    public CannotReserveException() {
    }

    public CannotReserveException(String message) {
        super(message);
    }

    public CannotReserveException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotReserveException(Throwable cause) {
        super(cause);
    }

    public CannotReserveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
