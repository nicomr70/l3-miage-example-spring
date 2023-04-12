package fr.uga.l3miage.example.exception.technical;

/**
 * Cette classe correspond à une exception technique, elle ne peut être levée que dans un <p color="red">@Service</p> ou dans un <p color="red">@Component</p>
 */
public class IsInErrorException extends Exception {
    public IsInErrorException(final String message) {
        super(message);
    }

    public IsInErrorException(final String message, Throwable cause) {
        super(message, cause);
    }
}
