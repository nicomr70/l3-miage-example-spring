package fr.uga.l3miage.example.exception.rest;

import fr.uga.l3miage.example.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class MiahootEntityNotFoundRestException extends RuntimeException {

    public MiahootEntityNotFoundRestException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public ErrorCode getErrorCode(){return ErrorCode.TEST_IS_NOT_FOUND;}
}
