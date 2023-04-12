package fr.uga.l3miage.example.exception.technical;

import fr.uga.l3miage.example.models.TestEntity;
import lombok.Getter;

/**
 * Exception technique<br>
 * Les annotations :
 * <ul>
 *     <li>{@link Getter} permet de créer tout les getters de tous les attributs. Voir la doc <a href="https://projectlombok.org/features/GetterSetter">projetlombok.org/features/Getter</a></li>
 * </ul>
 */
@Getter
public class IsNotTestException extends Exception{
    private final TestEntity testEntity;

    public IsNotTestException(String message, TestEntity testEntity) {
        super(message);
        this.testEntity = testEntity;
    }

    public IsNotTestException(String message, TestEntity testEntity, Throwable cause) {
        super(message, cause);
        this.testEntity = testEntity;
    }
}
