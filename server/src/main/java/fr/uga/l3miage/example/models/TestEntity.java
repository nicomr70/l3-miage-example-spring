package fr.uga.l3miage.example.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Correspond à l'entité Test
 * Les Annotations :
 * <ul>
 *     <li>{@link Getter} permet de créer tout les getters de tous les attributs. Voir la doc <a href="https://projectlombok.org/features/GetterSetter">projetlombok.org/features/Getter</a></li>
 *     <li>{@link Setter} permet de créer tout les setters de tous les attributs. Voir la doc <a href="https://projectlombok.org/features/GetterSetter">projetlombok.org/features/Setter</a></li>
 *     <li>{@link Builder} permet de créer un builder(<a href="https://refactoring.guru/fr/design-patterns/builder">patron builder</a>) pour la classe.<br>Aller voir la doc sur <a href="https://projectlombok.org/features/Builder">projetlombok.org/features/Builder</a></a></li></li></li>
 *     <li>{@link Entity} permet de déclarer cette classe comme une entité JPA</li>
 *     <li>{@link AllArgsConstructor} permet de créer un constructor avec tous les attributs de la classe. Voir la doc <a href="https://projectlombok.org/features/constructor">projetlombok.org/features/AllArgConstructor</a></li>
 *     <li>{@link NoArgsConstructor} permet de créer un constructeur avec aucun paramètre. Voir la doc <a href="https://projectlombok.org/features/constructor">projetlombok.org/features/NoArgConstructor</a></li>
 * </ul>
 */
@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TestEntity {
    /**
     * Les annotations :
     * <ul>
     *     <li>{@link Id} correspond à l'id de l'entité, l'id est clé primaire</li>
     *     <li>{@link GeneratedValue} permet de générer automatiquement l'id via un compteur interne dans hibernate</li>
     * </ul>
     */
    @Id
    @GeneratedValue
    private Long id;

    private String description;

    private String fieldMapping;

    private int testInt;

    private Boolean isTest;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TestEntity that = (TestEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
