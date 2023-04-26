package fr.uga.l3miage.example.exception.technical;

import lombok.Getter;

/**
 * Exception levée lorsqu'on essaye de modifier le userId d'un miahoot
 */
@Getter
public class MiahootUserIdNotSameException extends Exception {

    public MiahootUserIdNotSameException(String message) {
        super(message);
    }

}
