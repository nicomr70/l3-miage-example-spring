package fr.uga.l3miage.example.exception.technical;

import lombok.Getter;

@Getter
public class MiahootEntityNotFoundException extends Exception {

    public MiahootEntityNotFoundException(String message) {
        super(message);
    }
}
