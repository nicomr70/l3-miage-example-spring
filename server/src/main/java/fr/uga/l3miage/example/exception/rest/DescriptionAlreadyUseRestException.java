package fr.uga.l3miage.example.exception.rest;

import fr.uga.l3miage.example.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;


/**
 * Correspond à l'exception d'API d'une description déjà utilisée<br>
 * Les annotations :
 * <ul>
 *     <li>{@link Getter} permet de créer tout les getters de tous les attributs. Voir la doc <a href="https://projectlombok.org/features/GetterSetter">projetlombok.org/features/Getter</a></li>
 * </ul>
 */
@Getter
public class DescriptionAlreadyUseRestException extends RuntimeException {
    private final String description;

    public DescriptionAlreadyUseRestException(String message, String description) {
        super(message);
        this.description = description;
    }

    public DescriptionAlreadyUseRestException(String message, String description, Throwable cause) {
        super(message, cause);
        this.description = description;
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public ErrorCode getErrorCode(){return ErrorCode.DESCRIPTION_ALREADY_USE_ERROR;}
}
