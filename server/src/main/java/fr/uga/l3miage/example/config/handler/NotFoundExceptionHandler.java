package fr.uga.l3miage.example.config.handler;

import fr.uga.l3miage.example.error.ErrorResponse;
import fr.uga.l3miage.example.error.TestNotFoundErrorResponse;
import fr.uga.l3miage.example.exception.rest.TestEntityNotFoundRestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;


/**
 * Cette classe correspond au handler d'exception rest. Ici, on catch l'exception {@link TestEntityNotFoundRestException}<br>
 * Les annotations :
 * <ul>
 *     <li>{@link ConditionalOnWebApplication} permet de dire que cette classe est utilisée si nous sommes sur une application web</li>
 *     <li>{@link RequiredArgsConstructor} crée un constructeur avec les attributs finaux, ou les attributs annotés par {@link lombok.NonNull}.<br>Voir la doc <a href="https://projectlombok.org/features/constructor">projectlombok/features/RequiredArgConstructor</a></li>
 *     <li>{@link ControllerAdvice} va de paire avec l'annotation {@link ExceptionHandler}, elle permet de dire à spring que les réponses des controllers peuvent être modifiées par cette classe</li>
 *     <li>{@link Slf4j} permet de récupérer un logger nommé <b color="orange">log</b> afin de pouvoir afficher des messages lors de l'exécution.<br>Voir la doc <a href="https://projectlombok.org/features/log">projectlombok/features/log</a</li>
 * </ul>
 */
@ConditionalOnWebApplication
@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
public class NotFoundExceptionHandler {

    /**
     * Cette classe correspond au handler de l'exception {@link TestEntityNotFoundRestException}.<br>
     * Ici lorsque le code va lever l'exception {@link TestEntityNotFoundRestException} alors la fonction <b color="blue">handle()</b> sera appelée.<br>
     * Les annotations :
     * <ul>
     *     <li>{@link ExceptionHandler} permet de donner tous les types d'exceptions qui vont être catch par ce handler</li>
     * </ul>
     * @param httpServletRequest correspond à la requête effectuée par le client
     * @param exception L'exception qui a été levée dans le code server, et qui a été catch par ce handler
     * @return {@link ResponseEntity}<{@link TestNotFoundErrorResponse}></li>
     */
    @ExceptionHandler(TestEntityNotFoundRestException.class)
    public ResponseEntity<ErrorResponse> handle(HttpServletRequest httpServletRequest, Exception exception) {
        TestEntityNotFoundRestException ex = (TestEntityNotFoundRestException) exception;
        final TestNotFoundErrorResponse response = TestNotFoundErrorResponse.builder()
                .uri(httpServletRequest.getRequestURI())
                .httpStatus(ex.getHttpStatus())
                .errorMessage(ex.getMessage())
                .errorCode(ex.getErrorCode())
                .description(ex.getDescription())
                .build();
        log.warn(ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }
}
