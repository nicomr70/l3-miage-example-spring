package fr.uga.l3miage.example.annotations;

import fr.uga.l3miage.example.error.DescriptionAlreadyUseErrorResponse;
import fr.uga.l3miage.example.error.IsNotTestErrorResponse;
import fr.uga.l3miage.example.error.TestIntIsZeroErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Cette annotation est un raccourci d'annotation afin de ne pas devoir toujours réécrire cette erreur qui correspond à des soucis de requête<br>
 * Les Annotations :<br>
 * <ul>
 *     <li>{@link ApiResponse} permet de documenter les réponses prévues par l'API<br>
 *           Les annotations internes :
 *           <ul>
 *               <li>{@link Content} permet de préciser le contenu d'une {@link ApiResponse}<br>La <a href="https://docs.swagger.io/swagger-core/v2.0.8/apidocs/io/swagger/v3/oas/annotations/media/Content.html">doc</a> !</li>
 *               <li>{@link Schema} permet de préciser la classe qui sera renvoyée dans  le content de l'{@link ApiResponse}<br>
 *               Les Annotations internes à {@link Schema} :
 *               <ul>
 *                   <li>{@link DiscriminatorMapping} permet de préciser tous les types de réponses possibles sortant de l'erreur 400<br>
 *                   Exemple :<br>
 *                   <img src="../doc/pictures/oneOf.png" alt="One of"/><br>
 *                   La <a href="">doc</a> !</li>
 *                   </li>
 *               </ul>
 *             La <a href="https://docs.swagger.io/swagger-core/v2.0.0-RC3/apidocs/io/swagger/v3/oas/annotations/media/Schema.html">doc @Schema</a> !</li>
 *          </ul>
 *          La <a href="https://docs.swagger.io/swagger-core/v1.5.0/apidocs/io/swagger/annotations/ApiResponse.html">doc @ApiResponse</a> !
 *      </li>
 *      <li>{@link Target} Une annotation <b color="red">obligatoire</b> lors de la création d'une <b>@interface</b>.<br>
 *      Elle permet de renseigner où l'on peut placer notre annotation. Sont possibles :
 *          <ul>
 *              <li>{@link ElementType#METHOD} : sur une méthode</li>
 *              <li>{@link ElementType#TYPE} : sur une classe</li>
 *              <li>{@link ElementType#ANNOTATION_TYPE} : sur une annotation</li>
 *              <li>{@link ElementType#CONSTRUCTOR} : sur un constructeur</li>
 *              <li>{@link ElementType#FIELD} : sur un attribut</li>
 *              <li><b>etc...</b></li>
 *              <li>La <a href=""><b> doc</b></a> !</li>
 *          <ul/></li>
 *      <li>{@link Retention} une annotation <b color="red">obligatoire</b> lors de la création d'une <b>@interface</b>.<br>
 *      Elle permet de renseigner quoi faire avec les annotations, 3 choix possibles :
 *      <ul>
 *          <li>{@link RetentionPolicy#SOURCE}: mettre les résultats des annotations dans le .class et les supprime par la suite</li>
 *          <li>{@link RetentionPolicy#CLASS}: mettre les résultats des annotations dans le .class, mais garde aussi les annotations dans le .class</li>
 *          <li>{@link RetentionPolicy#RUNTIME}: comme le {@link RetentionPolicy#CLASS}, sauf que les Annotations sont gardées dans la JVM lors de l'exécution d'un programme et peuvent être utilisées lors du run</li>
 *          <li>La <a href="">doc</a> !</li>
 *      </ul>
 *      </li>
 * </ul>
 */
@ApiResponse(responseCode = "400", description = "Une erreur s'est produite lors de la création ou de la mise à jour de l'entité Test.",
        content = @Content(
                schema = @Schema(oneOf = {IsNotTestErrorResponse.class, TestIntIsZeroErrorResponse.class, DescriptionAlreadyUseErrorResponse.class},
                        discriminatorProperty = "errorCode",
                        discriminatorMapping = {
                                @DiscriminatorMapping(value = "TEST_INT_IS_ZERO_ERROR", schema = TestIntIsZeroErrorResponse.class),
                                @DiscriminatorMapping(value = "IS_NOT_TEST_ERROR", schema = IsNotTestErrorResponse.class),
                                @DiscriminatorMapping(value = "DESCRIPTION_ALREADY_USE_ERROR", schema = DescriptionAlreadyUseErrorResponse.class)
                        }),
                mediaType = MediaType.APPLICATION_JSON_VALUE))
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Error400Custom {
}
