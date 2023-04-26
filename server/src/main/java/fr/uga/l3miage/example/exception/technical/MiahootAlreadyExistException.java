package fr.uga.l3miage.example.exception.technical;

import lombok.Getter;

/**
 * Exception levée lorsqu'un Miahoot existe déjà (même userId et nom)
 */
@Getter
public class MiahootAlreadyExistException extends Exception {

    public MiahootAlreadyExistException(String message) {
        super(message);
    }

}
