package fr.uga.l3miage.example.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import org.springframework.http.HttpStatus;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * Cette classe correspond à l'erreur renvoyée au client lorsqu'une description est déjà utilisée sur une autre entité, lors de la création ou l'update d'une entité<br>
 * Elle hérite de la classe {@link ErrorResponse} afin de permettre du polymorphisme.<br>
 *
 * Les annotations :
 * <ul>
 *     <li>{@link JsonTypeName} permet de donner l'id du sous type d'une {@link ErrorResponse} pour pouvoir désérialiser la bonne erreur.</li>
 *     <li>{@link EqualsAndHashCode} permet de redéfinir la fonction <u>equals</u> et <u>hashCode</u>. Voir la doc <a href="https://projectlombok.org/features/EqualsAndHashCode">projetlombok.org/features/EqualsHashCode</a></li>
 *     <li>{@link Getter} permet de créer tout les getters de tous les attributs. Voir la doc <a href="https://projectlombok.org/features/GetterSetter">projetlombok.org/features/Getter</a></li>
 *     <li>{@link ToString} permet de redéfinir la fonction <u>toString</u> avec tous les champs de l'objet. Voir la  doc <a href="https://projectlombok.org/features/ToString">projetlombok.org/features/toString</a></li>
 * </ul>
 */
@JsonTypeName(DescriptionAlreadyUseErrorResponse.TYPE_NAME)
@EqualsAndHashCode(callSuper = true)
@Getter
@ToString(callSuper = true, exclude = "errorCodeSwaggerDocumentation")
public class DescriptionAlreadyUseErrorResponse extends ErrorResponse {
    /**
     * Match {@link ErrorCode#DESCRIPTION_ALREADY_USE_ERROR}
     */
    protected static final String TYPE_NAME = "DESCRIPTION_ALREADY_USE_ERROR";

    /**
     * Cette variable est utilisée que pour la doc dans le swagger<br>
     * Les annotations :
     * <ul>
     *     <li>{@link JsonProperty}</li>
     * </ul>
     */
    @SuppressWarnings({ "java:S115", "java:S1170" }) // Use only to generate documentation
    @Schema(name = "errorCode", description = "Ce code d'erreur est aussi le discriminant pour le polymorphisme", allowableValues = TYPE_NAME,
            implementation = String.class, accessMode = Schema.AccessMode.READ_WRITE)
    @JsonProperty(access = WRITE_ONLY)
    private final String errorCodeSwaggerDocumentation = "Field used only to generate documentation, do not use it";

    @Schema(description = "Description déjà utilisée dans une autre entité Test")
    private final String description;

    /**
     * Ce constructeur permet de désérialiser l'erreur.<br>
     *
     * Les annotations :
     * <ul>
     *     <li>{@link Builder} permet de créer un builder(<a href="https://refactoring.guru/fr/design-patterns/builder">patron builder</a>) pour la classe.<br>Aller voir la doc sur <a href="https://projectlombok.org/features/Builder">projetlombok.org/features/Builder</a></a></li></li></li>
     *     <li>{@link Jacksonized} permet de dire que c'est ce constructeur que le builder doit utiliser pour la désérialisation. Voir la doc <a href="https://projectlombok.org/features/experimental/Jacksonized">projetlombok.org/features/Jacksonized</a> </li>
     * </ul>
     *
     * @param uri élément de la classe {@link ErrorResponse}
     * @param httpStatus élément de la classe {@link ErrorResponse}
     * @param errorCode élément de la classe {@link ErrorResponse}
     * @param errorMessage élément de la classe {@link ErrorResponse}
     * @param description correspond à la description utilisée qui est en doublon.
     */
    @Builder
    @Jacksonized
    public DescriptionAlreadyUseErrorResponse(String uri, HttpStatus httpStatus, ErrorCode errorCode, String errorMessage, String description) {
        super(uri, httpStatus, errorCode, errorMessage);
        this.description = description;
    }
}
