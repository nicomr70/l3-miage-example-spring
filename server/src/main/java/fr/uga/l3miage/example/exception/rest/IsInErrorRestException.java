package fr.uga.l3miage.example.exception.rest;

import fr.uga.l3miage.example.error.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * Exception d'API
 */
public class IsInErrorRestException extends RuntimeException {
    public IsInErrorRestException(String message) {
        super(message);
    }

    public IsInErrorRestException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ErrorCode  getErrorCode(){return ErrorCode.IS_NOT_TEST_ERROR;}
}
