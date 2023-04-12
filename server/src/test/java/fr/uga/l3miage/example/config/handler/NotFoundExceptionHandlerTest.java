package fr.uga.l3miage.example.config.handler;

import fr.uga.l3miage.example.error.ErrorResponse;
import fr.uga.l3miage.example.error.TestNotFoundErrorResponse;
import fr.uga.l3miage.example.exception.rest.TestEntityNotFoundRestException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Permet de tester le handler de l'exception d'API {@link TestEntityNotFoundRestException}
 */
class NotFoundExceptionHandlerTest {
    @Test
    void testHandle() {
        NotFoundExceptionHandler handler = new NotFoundExceptionHandler();
        TestEntityNotFoundRestException exception = new TestEntityNotFoundRestException("TestConfigWithProperties not found","description");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("TestConfigWithProperties");
        ResponseEntity<TestNotFoundErrorResponse> expected = ResponseEntity.status(exception.getHttpStatus())
                .body(TestNotFoundErrorResponse.builder()
                        .errorMessage(exception.getMessage())
                        .uri(request.getRequestURI())
                        .errorCode(exception.getErrorCode())
                        .httpStatus(exception.getHttpStatus())
                        .description("description")
                        .build());

        ResponseEntity<ErrorResponse> response = handler.handle(request, exception);
        assertThat(response).usingRecursiveComparison().isEqualTo(expected);
    }
}
