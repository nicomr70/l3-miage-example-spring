package fr.uga.l3miage.example.controller;

import fr.uga.l3miage.example.endpoint.ExampleEndpoint;
import fr.uga.l3miage.example.exception.rest.IsInErrorRestException;
import fr.uga.l3miage.example.request.CreateTestRequest;
import fr.uga.l3miage.example.response.Test;
import fr.uga.l3miage.example.service.ExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Cette classe correspond à l'implémentation de l'interface {@link ExampleEndpoint}<br>
 * Les annotations :
 * <ul>
 *   <li>{@link RestController} permet d'indiquer que votre controller est de type <b>REST</b>.<br>
 *    La <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RestController.html">doc</a> !</li>
 *   <li>{@link RequiredArgsConstructor} crée un constructeur avec les attributs finaux, ou les attributs annotés par {@link lombok.NonNull}.<br>Voir la doc <a href="https://projectlombok.org/features/constructor">projectlombok/feature/RequiredArgConstructor</a></li>
 * </ul>
 */
@RestController
@RequiredArgsConstructor
public class ExampleController implements ExampleEndpoint {
    private final ExampleService exampleService;


    /**
     * Dans ce endpoint c'est dans le controller que nous créons la responseEntity, non par un handler
     */
    @Override
    public ResponseEntity<String> getHelloWord(final boolean isInError) {
        try {
            return ResponseEntity.ok().body(exampleService.helloWord(isInError));
        } catch (IsInErrorRestException ex) {
            return ResponseEntity.status(ex.getHttpStatus())
                    .body(String.format("Ici une erreur 500 est levée.\n\t le message des exceptions : \n\t\t%s", ex.getMessage()));
        }
    }

    @Override
    public Test getEntityTest(final String description) {
        return exampleService.getTest(description);
    }

    @Override
    public void createEntityTest(final CreateTestRequest request) {
        exampleService.createTest(request);
    }

    @Override
    public void updateTestEntity(final String lastDescription,final Test test) {
        exampleService.updateTest(lastDescription,test);
    }

    @Override
    public void deleteTestEntity(final String description) {
        exampleService.deleteTest(description);
    }
}
