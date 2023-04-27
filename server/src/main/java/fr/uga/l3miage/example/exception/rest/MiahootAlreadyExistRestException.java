package fr.uga.l3miage.example.exception.rest;

import fr.uga.l3miage.example.error.ErrorCode;
import fr.uga.l3miage.example.request.CreateMiahootRequest;
import fr.uga.l3miage.example.request.CreateTestRequest;
import org.springframework.http.HttpStatus;

public class MiahootAlreadyExistRestException extends RuntimeException {

    public MiahootAlreadyExistRestException(String message){
        super(message);
    };

    public MiahootAlreadyExistRestException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

}
