package fr.uga.l3miage.example.endpoint;


import fr.uga.l3miage.example.annotations.Error400Custom;
import fr.uga.l3miage.example.error.MiahootEntityNotDeletedErrorResponse;
import fr.uga.l3miage.example.error.MiahootNotFoundErrorResponse;
import fr.uga.l3miage.example.request.CreateMiahootRequest;
import fr.uga.l3miage.example.response.Miahoot;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Miahoot tag")
@CrossOrigin
@RestController
@RequestMapping("miahoot/")
public interface MiahootEndpoint {

    //GET
    @Operation(description = "Récupérer le DTO de l'entité miahoot qui a pour id celui passé en paramètre")
    @ApiResponse(responseCode = "200", description = "Renvoie le DTO de l'entité test demandée",
            content = @Content(schema = @Schema(implementation = Miahoot.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
            content = @Content(schema = @Schema(implementation = MiahootNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{userId}/{nom}")
    Miahoot getEntityMiahoot(@PathVariable long userId, @PathVariable String nom);

    @Operation(description = "Récupérer le DTO de l'entité miahoot qui a pour id celui passé en paramètre")
    @ApiResponse(responseCode = "200", description = "Renvoie le DTO de l'entité test demandée",
            content = @Content(schema = @Schema(implementation = Miahoot.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
            content = @Content(schema = @Schema(implementation = MiahootNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{nom}")
    List<Miahoot> getEntityMiahoot(@PathVariable String nom);
    
    @GetMapping("{userId}")
    List<Miahoot> getEntityMiahoot(@PathVariable long userId);


    //POST
    @Operation(description = "Création d'une entité Miahoot")
    @ApiResponse(responseCode = "201", description = "L'entité Miahoot a bien été créée.")
    @Error400Custom
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createEntityMiahoot(@Valid @RequestBody CreateMiahootRequest request);

    //PATCH
    @Operation(description = "Mise à jour d'une entité Miahoot")
    @ApiResponse(responseCode = "202", description = "L'entité à bien été mis à jour")
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
            content = @Content(schema = @Schema(implementation = MiahootNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @Error400Custom
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("{userId}/{nom}")
    void updateMiahootEntity(@PathVariable final long userId, @PathVariable final String nom, @RequestBody final Miahoot miahoot);


    //DELETE
    @Operation(description = "Suppression d'une entité Miahoot en bd")
    @ApiResponse(responseCode = "200", description = "si isInError est à false alors 'Hello word' est renvoyé")
    @ApiResponse(responseCode = "418", description = "Renvoie une erreur 418 si l'entité n'a pu être supprimée",
            content = @Content(schema = @Schema(implementation = MiahootNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{userId}/{nom}")
    void deleteMiahootEntity(@PathVariable long userId, @PathVariable String nom);

    @DeleteMapping("{nom}")
    void deleteMiahootEntity(@PathVariable String nom);

    @DeleteMapping("{userId}")
    void deleteMiahootEntity(@PathVariable long userId);


}
