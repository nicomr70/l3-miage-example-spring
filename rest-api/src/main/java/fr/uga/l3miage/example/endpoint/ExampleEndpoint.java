package fr.uga.l3miage.example.endpoint;

import fr.uga.l3miage.example.annotations.Error400Custom;
import fr.uga.l3miage.example.error.IsNotTestErrorResponse;
import fr.uga.l3miage.example.error.TestEntityNotDeletedErrorResponse;
import fr.uga.l3miage.example.error.TestIntIsZeroErrorResponse;
import fr.uga.l3miage.example.error.TestNotFoundErrorResponse;
import fr.uga.l3miage.example.error.DescriptionAlreadyUseErrorResponse;
import fr.uga.l3miage.example.request.CreateTestRequest;
import fr.uga.l3miage.example.response.Test;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


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
@Tag(name = "Exemple tag")
@CrossOrigin
@RestController
@RequestMapping("exemple/")
public interface ExampleEndpoint {

    /**
     * Ici on définit un endpoint en mode <b>GET</b> qui nous renvoie "Hello Word"<br>
     *
     * @param isInError correspond au fait de vouloir qu'une erreur se produise ou non sur cet endpoint
     * @return <ul>
     *     <li>"Hello word" si <b>isInError</b> est égal à false</li>
     *     <li>Un message d'erreur si <b>isInError</b> est égal à true</li>
     * </ul>
     * Les annotations :
     * <ul>
     *     <li>{@link Operation} permet de créer une description de l'opération de ce endpoint dans le swagger<br>
     *     Exemple:<br>
     *     <img src="../doc/pictures/descriptionSwagger.png" alt="swagger description"/><br>
     *     La <a href="https://docs.swagger.io/swagger-core/v2.0.0-RC3/apidocs/io/swagger/v3/oas/annotations/Operation.html">doc</a> !</li>
     *     <li>{@link ApiResponse} permet de documenter les réponses prévues par l'API<br>
     *     Les annotations internes :
     *     <ul>
     *         <li>{@link Content} permet de préciser le contenu d'une {@link ApiResponse}<br>La <a href="https://docs.swagger.io/swagger-core/v2.0.8/apidocs/io/swagger/v3/oas/annotations/media/Content.html">doc</a> !</li>
     *         <li>{@link Schema} permet de préciser la classe qui sera renvoyée dans  le content de l'{@link ApiResponse}<br>La <a href="https://docs.swagger.io/swagger-core/v2.0.0-RC3/apidocs/io/swagger/v3/oas/annotations/media/Schema.html">doc</a> !</li>
     *     </ul>
     *     Exemple:<br>
     *     <img src="../doc/pictures/responsesApi.png" alt ="Response API"/><br>
     *     La <a href="https://docs.swagger.io/swagger-core/v1.5.0/apidocs/io/swagger/annotations/ApiResponse.html">doc</a> !</li></li>
     *     <li>{@link ResponseStatus} permet de renvoyer le statut http donné si l'appel s'est bien passé<br>Ici on renverra un statut 200 si tout s'est bien passé !<br>
     *     La <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ResponseStatus.html">doc</a> !
     *     <li>{@link GetMapping} permet de spécifier dans quelle méthode http ce endpoint doit être utilisé<br>Ici ce endpoint est en mode <b>GET</b><br>
     *     La <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/GetMapping.html">doc</a> !</li>
     *     <li>{@link Parameter} permet de créer un paramètre de requête dans le swagger<br>
     *     Exemple :<br>
     *     <img src="../doc/pictures/requestParameterSwagger.png" alt="Request Parameter Swagger"/><br>
     *     La <a href="https://docs.swagger.io/swagger-core/v2.0.6/apidocs/io/swagger/v3/oas/annotations/Parameter.html">doc</a> !
     *     </li>
     *     <li>{@link RequestParam} Permet de dire à spring de trouver le paramètre isInError dans les paramètres de la requête REST <br>
     *     La <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestParam.html">doc</a> !</li>
     * </ul>
     *
     */
    @Operation(description = "Récupérer la phrase 'Hello word'")
    @ApiResponse(responseCode = "200", description = "Si isInError est à false alors 'Hello word' est renvoyé.",
            content = @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.TEXT_PLAIN_VALUE))
    @ApiResponse(responseCode = "500", description = "Si isInError est à true alors nous avons une erreur 500 qui est renvoyée.",
            content = @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.TEXT_PLAIN_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("hello")
    ResponseEntity<String> getHelloWord(@Parameter(description = "correspond a si vous voulez avoir une erreur ou non" ,example = "true") @RequestParam boolean isInError);


    /**
     * Ici on définit un endpoint en mode <b>GET</b> qui nous permet de récupérer le DTO d'une entité Test en fonction de sa description<br>
     * @param description correspond à la description d'une entité que l'on recherche
     * @return
     * <ul>
     *     <li>le <b color="yellow">DTO</b> de l'entité Test : {@link Test}</li>
     *     <li>En cas <b color="red">d'erreurs</b> :
     *     <ul>
     *         <li>L'entité n'a pas été trouvée : {@link TestNotFoundErrorResponse}</li>
     *     </ul>
     *     </li>
     * </ul>
     *
     * Les annotations :
     * <ul>
     *     <li>{@link Operation} permet de créer une description de l'opération de ce endpoint dans le swagger<br>
     *     La <a href="https://docs.swagger.io/swagger-core/v2.0.0-RC3/apidocs/io/swagger/v3/oas/annotations/Operation.html">doc</a> !</li>
     *     <li>{@link ApiResponse} permet de documenter les réponses prévues par l'API<br>
     *     Les annotations internes :
     *     <ul>
     *         <li>{@link Content} permet de préciser le contenu d'une {@link ApiResponse}<br>La <a href="https://docs.swagger.io/swagger-core/v2.0.8/apidocs/io/swagger/v3/oas/annotations/media/Content.html">doc</a> !</li>
     *         <li>{@link Schema} permet de préciser la classe qui sera renvoyée dans le content de l'{@link ApiResponse}<br>La <a href="https://docs.swagger.io/swagger-core/v2.0.0-RC3/apidocs/io/swagger/v3/oas/annotations/media/Schema.html">doc</a> !</li>
     *     </ul>
     *     La <a href="https://docs.swagger.io/swagger-core/v1.5.0/apidocs/io/swagger/annotations/ApiResponse.html">doc</a> !</li></li>
     *     <li>{@link ResponseStatus} permet de renvoyer le statut http donné si l'appel s'est bien passé<br>Ici on renverra un statut 200 si tout s'est bien passé !<br>
     *     La <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ResponseStatus.html">doc</a> !
     *     <li>{@link GetMapping} permet de spécifier dans quelle méthode http ce endpoint doit être utilisé<br>Ici ce endpoint est en mode <b>GET</b><br>
     *     La <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/GetMapping.html">doc</a> !</li>
     *     <li>{@link PathVariable} permet de dire à spring où trouver le paramètre de la fonction<br>Ici dans le path de l'endpoint, donc "exemple/<b color="blue">{description}</b>"</li>
     *      La <a href="">doc</a> !</li>
     * </ul>
     */
    @Operation(description = "Récupérer le DTO de l'entité test qui a pour id celui passé en paramètre")
    @ApiResponse(responseCode = "200", description = "Renvoie le DTO de l'entité test demandée",
            content = @Content(schema = @Schema(implementation = Test.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
    content = @Content(schema = @Schema(implementation = TestNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{description}")
    Test getEntityTest(@PathVariable String description);


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
     *     <li>{@link RequestBody} Permet de dire à spring de trouver le paramètre request dans les corps de la requête REST <br>
     *     La <a href="">doc</a> !</li>
     *     <li>{@link Valid} si vous rajoutez un validateur elle permet de vérifier que tous les champs sont conformes à vos attentes (NonNull, NotBlank, etc...)</li>
     * </ul>
     *
     */
    @Operation(description = "Création d'une entité Test")
    @ApiResponse(responseCode = "201", description = "L'entité Test a bien été créée.")
    @Error400Custom
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createEntityTest(@Valid @RequestBody CreateTestRequest request);

    /**
     * Ici on définit un endpoint en mode <b>PATCH</b> pour mettre à jour une entité Test<br>
     *
     * @param lastDescription correspond à la description courante de l'entité test à modifier
     * @param test correspond à l'objet métier modifié.
     * @return En cas <b color="red">d'erreur</b>:<br>
     * <ul>
     *     <li>{@link IsNotTestErrorResponse} si le champ isTest est égal  à false</li>
     *     <li>{@link TestIntIsZeroErrorResponse} si la somme des 2 entiers de la requête est égale à 0</li>
     *     <li>{@link DescriptionAlreadyUseErrorResponse} si la description existe déjà en BD</li>
     *     <li>{@link TestNotFoundErrorResponse} si l'entité test n'est pas trouvé en BD</li>
     * </ul>
     * Les annotations :
     * <ul>
     *     <li>{@link Operation} permet de créer une description de l'opération de ce endpoint dans le swagger<br>
     *     La <a href="https://docs.swagger.io/swagger-core/v2.0.0-RC3/apidocs/io/swagger/v3/oas/annotations/Operation.html">doc</a> !</li>
     *     <li>{@link ApiResponse} permet de documenter les réponses prévues par l'API<br>
     *     Les annotations internes :
     *     <ul>
     *         <li>{@link Content} permet de préciser le contenu d'une {@link ApiResponse}<br>La <a href="https://docs.swagger.io/swagger-core/v2.0.8/apidocs/io/swagger/v3/oas/annotations/media/Content.html">doc</a> !</li>
     *         <li>{@link Schema} permet de préciser la classe qui sera renvoyée dans le content de l'{@link ApiResponse}<br>La <a href="https://docs.swagger.io/swagger-core/v2.0.0-RC3/apidocs/io/swagger/v3/oas/annotations/media/Schema.html">doc</a> !</li>
     *     </ul>
     *     </li>
     *     <li>{@link Error400Custom} Annotation custom créée afin de faire un raccourci d'annotations</li>
     *     <li>{@link ResponseStatus} permet de renvoyer le statut http donné si l'appel s'est bien passé<br>Ici on renverra un statut 202 si tout s'est bien passé !<br>
     *     La <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ResponseStatus.html">doc</a> !
     *     <li>{@link PatchMapping} permet de spécifier dans quelle méthode http ce endpoint doit être utilisé<br>Ici ce endpoint est en mode <b>PATCH</b><br>
     *     La <a href="">doc</a> !</li>
     *     </li>
     *     <li>{@link RequestBody} Permet de dire à spring de trouver le paramètre request dans les corps de la requête REST <br>
     *     La <a href="">doc</a> !</li>
     *     <li>{@link Valid} si vous rajoutez un validateur elle permet de vérifier que tous les champs sont conformes à vos attentes (NonNull, NotBlank, etc...)</li>
     * </ul>
     *
     */
    @Operation(description = "Mise à jour d'une entité test")
    @ApiResponse(responseCode = "202", description = "L'entité à bien été mis à jour")
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
            content = @Content(schema = @Schema(implementation = TestNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @Error400Custom
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("{lastDescription}")
    void updateTestEntity(@PathVariable final String lastDescription,@RequestBody final Test test);


    /**
     * Ici on définit un endpoint en mode <b>DELETE</b> pour supprimer une entité Test<br>
     *
     * @param description correspond à description de l'entité Test à supprimer
     * @return En cas <b color="red">d'erreur</b>:<br>
     * <ul>
     *     <li>{@link TestEntityNotDeletedErrorResponse} peut arriver pour 2 raisons :
     *     <ul>
     *         <li>si aucune entité Test n'est trouvé en fonction de la description</li>
     *         <li>si plusieurs entités ont la même description, ce n'est pas censé arriver !</li>
     *     </ul>
     *     </li>
     * </ul>
     * Les annotations :
     * <ul>
     *     <li>{@link Operation} permet de créer une description de l'opération de ce endpoint dans le swagger<br>
     *     La <a href="https://docs.swagger.io/swagger-core/v2.0.0-RC3/apidocs/io/swagger/v3/oas/annotations/Operation.html">doc</a> !</li>
     *     <li>{@link ApiResponse} permet de documenter les réponses prévues par l'API<br>
     *     Les annotations internes :
     *     <ul>
     *         <li>{@link Content} permet de préciser le contenu d'une {@link ApiResponse}<br>La <a href="https://docs.swagger.io/swagger-core/v2.0.8/apidocs/io/swagger/v3/oas/annotations/media/Content.html">doc</a> !</li>
     *         <li>{@link Schema} permet de préciser la classe qui sera renvoyée dans  le content de l'{@link ApiResponse}<br>La <a href="https://docs.swagger.io/swagger-core/v2.0.0-RC3/apidocs/io/swagger/v3/oas/annotations/media/Schema.html">doc</a> !</li>
     *     </ul>
     *     </li>
     *     <li>{@link ResponseStatus} permet de renvoyer le statut http donné si l'appel s'est bien passé<br>Ici on renverra un statut 202 si tout s'est bien passé !<br>
     *     La <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ResponseStatus.html">doc</a> !
     *     <li>{@link DeleteMapping} permet de spécifier dans quelle méthode http ce endpoint doit être utilisé<br>Ici ce endpoint est en mode <b>DELETE</b><br>
     *     La <a href="">doc</a> !</li>
     *     <li>{@link PathVariable} permet de dire à spring où trouver le paramètre de la fonction<br>Ici dans le path de l'endpoint, donc "exemple/<b color="blue">{description}</b>"</li>
     *      La <a href="">doc</a> !</li>
     * </ul>
     *
     */
    @Operation(description = "Suppression d'une entité Test en bd")
    @ApiResponse(responseCode = "200", description = "si isInError est à false alors 'Hello word' est renvoyé")
    @ApiResponse(responseCode = "418", description = "Renvoie une erreur 418 si l'entité n'a pu être supprimée",
            content = @Content(schema = @Schema(implementation = TestEntityNotDeletedErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{description}")
    void deleteTestEntity(@PathVariable String description);
}
