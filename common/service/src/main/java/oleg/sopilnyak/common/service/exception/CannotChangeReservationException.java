package oleg.sopilnyak.common.service.exception;

/**
 * Exception throws when system cannot change parameters of reservation
 */
public class CannotChangeReservationException extends CannotReserveException {
    private final String agreementId;

    public CannotChangeReservationException(String agreementId) {
        this.agreementId = agreementId;
    }

    public CannotChangeReservationException(String message, String agreementId) {
        super(message);
        this.agreementId = agreementId;
    }

    public CannotChangeReservationException(String message, Throwable cause, String agreementId) {
        super(message, cause);
        this.agreementId = agreementId;
    }

    public CannotChangeReservationException(Throwable cause, String agreementId) {
        super(cause);
        this.agreementId = agreementId;
    }

    public CannotChangeReservationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String agreementId) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.agreementId = agreementId;
    }

    public String getAgreementId() {
        return agreementId;
    }
}
