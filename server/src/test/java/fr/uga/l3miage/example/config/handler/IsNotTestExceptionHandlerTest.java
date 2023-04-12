package fr.uga.l3miage.example.config.handler;

import fr.uga.l3miage.example.error.ErrorResponse;
import fr.uga.l3miage.example.error.IsNotTestErrorResponse;
import fr.uga.l3miage.example.exception.rest.IsNotTestRestException;
import fr.uga.l3miage.example.request.CreateTestRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Permet de tester le handler de l'exception d'API {@link IsNotTestRestException}
 */
class IsNotTestExceptionHandlerTest {

    @Test
    void testHandle() {
        IsNotTestExceptionHandler handler = new IsNotTestExceptionHandler();
        IsNotTestRestException exception = new IsNotTestRestException("Is not TestConfigWithProperties", CreateTestRequest.builder().build());
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("TestConfigWithProperties");
        ResponseEntity<IsNotTestErrorResponse> expected = ResponseEntity.status(exception.getHttpStatus())
                .body(IsNotTestErrorResponse.builder()
                        .errorMessage(exception.getMessage())
                        .uri(request.getRequestURI())
                        .errorCode(exception.getErrorCode())
                        .httpStatus(exception.getHttpStatus())
                        .request(exception.getRequest())
                        .build());

        ResponseEntity<ErrorResponse> response = handler.handle(request, exception);
        assertThat(response).usingRecursiveComparison().isEqualTo(expected);
    }
}
