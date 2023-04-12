package fr.uga.l3miage.example.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Représente le <b color="yellow">DTO</b> de l'entité Test
 * <br>
 * Les annotations :
 * <ul>
 *     <li>{@link Data} est une annotation raccourci pour plusieurs annotations de lombok<br>Aller voir la doc sur <a href="https://projectlombok.org/features/Data">projetlombok.org/features/data</a></a></li></li>
 *     <li>{@link Builder} permet de créer un builder(<a href="https://refactoring.guru/fr/design-patterns/builder">patron builder</a>) pour la classe.<br>Aller voir la doc sur <a href="https://projectlombok.org/features/Builder">projetlombok.org/features/Builder</a></a></li></li></li>
 * </ul>
 */
@Data
@Builder
@Schema(description = "correspond au DTO de l'entité Test")
public class Test {
    @Schema(description = "correspond à la description de l'objet",example = "cet objet est un test")
    String description;

    @Schema(description = "correspond au champs qui devra être mappé explicitement par le mapper TestMapper",example = "quelconque")
    String fieldMapping;

    @Schema(description = "Correspond à la somme de test1 et test2 dans la requête CreateTest",example = "12")
    int testInt;

    @Schema(description = "Correspond à un boolean quelconque",example = "true")
    Boolean isTest;
}
