package fr.uga.l3miage.example.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "correspond au DTO de l'entité Miahoot")
public class Miahoot {
    @Schema(description = "correspond à l'Id de l'utilisateur de l'objet",example = "23")
    Long userId;

    @Schema(description = "correspond au nom de l'objet",example = "qcm ihm")
    String nom;

//    mettre les questions?
}
