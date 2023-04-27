package fr.uga.l3miage.example.exception.rest;

import fr.uga.l3miage.example.error.ErrorCode;
import fr.uga.l3miage.example.request.CreateTestRequest;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class MiahootUserIdNotSameRestException extends RuntimeException {

    public MiahootUserIdNotSameRestException(String message) {
        super(message);
    }

    public MiahootUserIdNotSameRestException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public ErrorCode getErrorCode(){return ErrorCode.IS_NOT_TEST_ERROR;}

}
