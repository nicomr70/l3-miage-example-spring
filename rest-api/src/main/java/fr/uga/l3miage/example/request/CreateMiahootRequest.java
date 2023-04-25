package fr.uga.l3miage.example.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Correspond à la requête permettant de créer une entité test")
public class CreateMiahootRequest {

    @Schema(description = "correspond à l'Id de l'utilisateur de l'objet",example = "23")
    private Long userId;

    @Schema(description = "correspond au nom de l'objet",example = "qcm ihm")
    private String nom;
}
