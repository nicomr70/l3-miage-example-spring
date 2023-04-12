package fr.uga.l3miage.example.exception.technical;

/**
 * Exception technique
 */
public class MultipleEntityHaveSameDescriptionException extends Exception{
    public MultipleEntityHaveSameDescriptionException(String message) {
        super(message);
    }

    public MultipleEntityHaveSameDescriptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
