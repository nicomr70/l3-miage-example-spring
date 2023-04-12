package fr.uga.l3miage.example.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Représente la requête faite lorsqu'on veut créer une entité Test
 * <br>
 * Les annotations :
 * <ul>
 *     <li>{@link Data} est une annotation raccourcie pour plusieurs annotations de lombok<br>Aller voir la doc sur <a href="https://projectlombok.org/features/Data">projetlombok.org/features/data</a></a></li></li>
 *     <li>{@link Builder} permet de créer un builder(<a href="https://refactoring.guru/fr/design-patterns/builder">patron builder</a>) pour la classe.<br>Aller voir la doc sur <a href="https://projectlombok.org/features/Builder">projetlombok.org/features/Builder</a></a></li></li></li>
 * </ul>
 */
@Data
@Builder
@Schema(description = "Correspond à la requête permettant de créer une entité test")
public class CreateTestRequest {

    @Schema(description = "description de l'objet test", example = "description test")
    private String description;

    @Schema(description = "description d'un champs", example = "description")
    private String fieldNotMappingAutomatically;

    @Schema(description = "description d'un entier", example = "12")
    private int testIntMapperUtil1;

    @Schema(description = "description d'un entier", example = "-13")
    private int testIntMapperUtil2;

    @Schema(description = "description d'un bool", example = "true")
    private Boolean isTest;
}
