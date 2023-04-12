package fr.uga.l3miage.example.exception.rest;

import fr.uga.l3miage.example.error.ErrorCode;
import fr.uga.l3miage.example.request.CreateTestRequest;
import lombok.Getter;
import org.springframework.http.HttpStatus;


/**
 * Correspond à l'exception d'API de la création d'une entité qui a le champ isTest à false<br>
 * Les annotations :
 * <ul>
 *     <li>{@link Getter} permet de créer tout les getters de tous les attributs. Voir la doc <a href="https://projectlombok.org/features/GetterSetter">projetlombok.org/features/Getter</a></li>
 * </ul>
 */
@Getter
public class IsNotTestRestException extends RuntimeException {
    private final CreateTestRequest request;

    public IsNotTestRestException(String message, CreateTestRequest request) {
        super(message);
        this.request = request;
    }

    public IsNotTestRestException(String message,CreateTestRequest request, Throwable cause) {
        super(message, cause);
        this.request = request;
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public ErrorCode getErrorCode(){return ErrorCode.IS_NOT_TEST_ERROR;}

}
