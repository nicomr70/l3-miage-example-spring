package fr.uga.l3miage.example.exception.rest;

import fr.uga.l3miage.example.error.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * Exception d'API d'une entité qui ne peu pas être supprimée
 */
public class TestEntityNotDeletedRestException extends RuntimeException{
    public TestEntityNotDeletedRestException(String message) {
        super(message);
    }

    public TestEntityNotDeletedRestException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getHttpStatus() {return HttpStatus.I_AM_A_TEAPOT;}

    public ErrorCode getErrorCode(){return ErrorCode.TEST_ENTITY_NOT_DELETED_ERROR;}
}
