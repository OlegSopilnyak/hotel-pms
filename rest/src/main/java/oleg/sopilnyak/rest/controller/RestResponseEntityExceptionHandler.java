package oleg.sopilnyak.rest.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import oleg.sopilnyak.common.service.exception.CannotCancelException;
import oleg.sopilnyak.common.service.exception.CannotChangeReservationException;
import oleg.sopilnyak.common.service.exception.CannotReserveException;
import oleg.sopilnyak.common.service.exception.ResourceNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * Exception handler adviser
 */
@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(value = { Throwable.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorMessage unknownException(Throwable ex, WebRequest req) {
        return RestErrorMessage.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage(ex.getMessage())
                .errorUrl(req.getContextPath())
                .build();
    }

    @ExceptionHandler(value = { DataAccessException.class })
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public RestErrorMessage onException(DataAccessException ex, WebRequest req) {
        return RestErrorMessage.builder()
                .errorCode(HttpStatus.SERVICE_UNAVAILABLE.value())
                .errorMessage(ex.getMessage())
                .errorUrl(req.getContextPath())
                .build();
    }

    @ExceptionHandler(value = { IllegalStateException.class })
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public RestErrorMessage onException(IllegalStateException ex, WebRequest req) {
        return RestErrorMessage.builder()
                .errorCode(HttpStatus.SERVICE_UNAVAILABLE.value())
                .errorMessage(ex.getMessage())
                .errorUrl(req.getContextPath())
                .build();
    }

    @ExceptionHandler(value = { CannotReserveException.class })
    @ResponseStatus(HttpStatus.CONFLICT)
    public RestErrorMessage onException(CannotReserveException ex, WebRequest req) {
        return RestErrorMessage.builder()
                .errorCode(HttpStatus.CONFLICT.value())
                .errorMessage(ex.getMessage())
                .errorUrl(req.getContextPath())
                .build();
    }


    @ExceptionHandler(value = { CannotChangeReservationException.class })
    @ResponseStatus(HttpStatus.CONFLICT)
    public RestErrorMessage onException(CannotChangeReservationException ex, WebRequest req) {
        return RestErrorMessage.builder()
                .errorCode(HttpStatus.CONFLICT.value())
                .errorMessage(ex.getAgreementId()+" - "+ex.getMessage())
                .errorUrl(req.getContextPath())
                .build();
    }

    @ExceptionHandler(value = { CannotCancelException.class })
    @ResponseStatus(HttpStatus.CONFLICT)
    public RestErrorMessage onException(CannotCancelException ex, WebRequest req) {
        return RestErrorMessage.builder()
                .errorCode(HttpStatus.CONFLICT.value())
                .errorMessage(ex.getMessage())
                .errorUrl(req.getContextPath())
                .build();
    }

    @ExceptionHandler(value = { ResourceNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RestErrorMessage onException(ResourceNotFoundException ex, WebRequest req) {
        return RestErrorMessage.builder()
                .errorCode(HttpStatus.NOT_FOUND.value())
                .errorMessage(ex.getMessage())
                .errorUrl(req.getContextPath())
                .build();
    }

    // inner classes
    @Data
    @AllArgsConstructor
    @Builder
    public static class RestErrorMessage{
        private int errorCode;
        private String errorMessage;
        private String errorUrl;
    }
}
