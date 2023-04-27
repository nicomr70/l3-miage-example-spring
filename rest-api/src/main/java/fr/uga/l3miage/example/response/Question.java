package fr.uga.l3miage.example.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@Schema(description = "correspond au DTO de l'entité Question")
public class Question {
    @Schema(description = "correspond à la description de l'objet",example = "cet objet est un test")
    String label;
    //@Schema(description = "correspond au champs qui devra être mappé explicitement par le mapper TestMapper",example = "quelconque")
    //Set<ReponseEntity> reponses;
}
