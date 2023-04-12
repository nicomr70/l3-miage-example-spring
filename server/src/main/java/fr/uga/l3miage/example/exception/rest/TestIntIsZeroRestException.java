package fr.uga.l3miage.example.exception.rest;

import fr.uga.l3miage.example.error.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * Correspond à l'exception d'API d'une entité qui à testInt égale à zéro
 */
public class TestIntIsZeroRestException extends RuntimeException{
    public TestIntIsZeroRestException(String message) {
        super(message);
    }

    public TestIntIsZeroRestException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public ErrorCode getErrorCode(){return ErrorCode.TEST_INT_IS_ZERO_ERROR;}
}
