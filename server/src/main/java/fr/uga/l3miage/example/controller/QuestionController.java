package fr.uga.l3miage.example.controller;

import fr.uga.l3miage.example.endpoint.ExampleEndpoint;
import fr.uga.l3miage.example.endpoint.QuestionEndpoint;
import fr.uga.l3miage.example.service.QuestionService;
import lombok.RequiredArgsConstructor;
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
public class QuestionController implements QuestionEndpoint {
    private final QuestionService questionService;

    @Override
    public void createEntityQuestion(String label) {

        questionService.createQuestion(label);
    }
}