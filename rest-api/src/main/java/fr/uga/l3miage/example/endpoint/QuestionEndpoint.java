package fr.uga.l3miage.example.endpoint;

import fr.uga.l3miage.example.annotations.Error400Custom;
import fr.uga.l3miage.example.error.DescriptionAlreadyUseErrorResponse;
import fr.uga.l3miage.example.error.IsNotTestErrorResponse;
import fr.uga.l3miage.example.error.TestIntIsZeroErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p>Cette interface correspond à la définition du controller <b>REST</b>, elle vous permet de vous assurer
 * que votre controller respecte ce contrat d'interface. Mais aussi si un jour vous voulez partager votre API,
 * alors vous n'avez qu'à partager cette interface.</p>
 * <u>Les annotations :</u>
 * <ul>
 *  <li>{@link Tag} correspond au nom de la section de tous les endpoints dans le <b>swagger</b><br>
 *  Exemple :<br>
 *  <img src="../doc/pictures/tagSwagger.png" alt="Swagger tag"/><br>
 *  La <a href="https://docs.swagger.io/swagger-core/v2.0.0-RC3/apidocs/io/swagger/v3/oas/annotations/tags/Tag.html">doc</a> !</li></li>
 *  <li>{@link CrossOrigin} Permet d'avoir plusieurs points d'accès différents. <br>
 *  La <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/CrossOrigin.html">doc</a> !</li>
 *  <li>{@link RestController} permet d'indiquer que votre controller est de type <b>REST</b>.<br>
 *  La <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RestController.html">doc</a> !</li>
 *  <li>{@link RequestMapping} permet de dire que tous les endpoints de la classe commencent par <b>'exemple/'</b><br>
 *  La <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestMapping.html">doc</a> !</li>
 * </ul>
 */
@Tag(name = "EndPoints Question")
@CrossOrigin
@RestController
@RequestMapping("api/v0/")
public interface QuestionEndpoint {

    /**
     * Ici on définit un endpoint en mode <b>POST</b> pour créer une entité Test<br>
     *
     * @param request correspond à la requête à effectuer avec les informations utiles pour la création d'une entité Test
     * @return En cas <b color="red">d'erreur</b>:<br>
     * <ul>
     *     <li>{@link IsNotTestErrorResponse} si le champ isTest est égal  à false</li>
     *     <li>{@link TestIntIsZeroErrorResponse} si la somme des 2 entiers de la requête est égale à 0</li>
     *     <li>{@link DescriptionAlreadyUseErrorResponse} si la description existe déjà en BD</li>
     * </ul>
     *
     * Les annotations :
     * <ul>
     *     <li>{@link Operation} permet de créer une description de l'opération de ce endpoint dans le swagger<br>
     *     La <a href="https://docs.swagger.io/swagger-core/v2.0.0-RC3/apidocs/io/swagger/v3/oas/annotations/Operation.html">doc</a> !</li>
     *     <li>{@link ApiResponse} permet de documenter les réponses prévues par l'API</li>
     *     <li>{@link Error400Custom} Annotation custom créée afin de faire un raccourci d'annotations</li>
     *     <li>{@link ResponseStatus} permet de renvoyer le statut http donné si l'appel s'est bien passé<br>Ici on renverra un statut 201 si tout s'est bien passé !<br>
     *     La <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ResponseStatus.html">doc</a> !
     *     <li>{@link PostMapping} permet de spécifier dans quelle méthode http ce endpoint doit être utilisé<br>Ici ce endpoint est en mode <b>POST</b><br>
     *     La <a href="">doc</a> !</li>
     *     </li>
     *     <li>{@link PathVariable} permet de dire à spring où trouver le paramètre de la fonction<br>Ici dans le path de l'endpoint, donc "exemple/<b color="blue">{description}</b>"</li>
     *     La <a href="">doc</a> !</li>
     *     <li>{@link Valid} si vous rajoutez un validateur elle permet de vérifier que tous les champs sont conformes à vos attentes (NonNull, NotBlank, etc...)</li>
     * </ul>
     *
     */
    @Operation(description = "Création d'une entité question")
    @ApiResponse(responseCode = "201", description = "L'entité Test a bien été crée.")
    @Error400Custom
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{label}")
    void createEntityQuestion(@NotNull @PathVariable String label);


}
