package fr.uga.l3miage.example.error;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

/**
 * Permet de renvoyer toujours un message d'erreur générique avec des champs obligatoires communs, car tous les messages ont pour classe mère
 * cette classe abstraite, donc par <b>inversion de dépendance</b> le client aura une <b>ErrorResponse</b><br>
 * <br>
 * Les annotations :
 * <ul>
 *     <li>{@link JsonTypeInfo} permet de définir les informations utiles pour la désérialisation d'un json correspondant à une errorResponse</li>
 *     <li>{@link JsonSubTypes} permet de définir tous les types possibles de classe fille de ErrorResponse
 *     Les annotations internes :
 *      <ul>
 *          <li>{@link JsonSubTypes.Type} permet de définir les types possibles qui héritent de la classe ErrorResponse</li>
 *      </ul>
 *     </li>
 *     <li>{@link SuperBuilder} permet de créer un builder à une classe qui va être redéfini, et qui va être utilisé par la classe fille. Voir la doc <a href="https://projectlombok.org/features/experimental/SuperBuilder">projetlombok.org/features/SuperBuilder</a></li>
 *     <li>{@link Data} est une annotation raccourcie pour plusieurs annotations de lombok<br>Aller voir la doc sur <a href="https://projectlombok.org/features/Data">projetlombok.org/features/data</a></a></li></li>
 * </ul>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "errorCode", visible = true,
        defaultImpl = ErrorResponse.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TestIntIsZeroErrorResponse.class),
        @JsonSubTypes.Type(value = IsNotTestErrorResponse.class),
        @JsonSubTypes.Type(value = DescriptionAlreadyUseErrorResponse.class),
        @JsonSubTypes.Type(value = TestEntityNotDeletedErrorResponse.class),
        @JsonSubTypes.Type(value = TestNotFoundErrorResponse.class)
})
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Data
public abstract class ErrorResponse {
    @Schema(description = "endpoint appelé", example = "/exemple/{id}")
    private final String uri;
    @Schema(description = "code http de la réponse", example = "409")
    private final HttpStatus httpStatus;
    @Schema(description = "code de l'erreur", example="TEST_INT_IS_ZERO_ERROR")
    private final ErrorCode errorCode;
    @Schema(description = "message d'erreur")
    private final String errorMessage;

    protected ErrorResponse(final ErrorCode errorCode){
        this(null,null,errorCode,null);
    }
}
