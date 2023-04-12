package fr.uga.l3miage.example.config.handler;

import fr.uga.l3miage.example.error.ErrorResponse;
import fr.uga.l3miage.example.error.TestIntIsZeroErrorResponse;
import fr.uga.l3miage.example.exception.rest.TestIntIsZeroRestException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Permet de tester le handler de l'exception d'API {@link TestIntIsZeroRestException}
 */
class TestIntIsZeroExceptionHandlerTest {

    @Test
    void testHandle() {
        TestIntIsZeroExceptionHandler handler = new TestIntIsZeroExceptionHandler();
        TestIntIsZeroRestException exception = new TestIntIsZeroRestException("testInt est égale à 0");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("TestConfigWithProperties");
        ResponseEntity<TestIntIsZeroErrorResponse> expected = ResponseEntity.status(exception.getHttpStatus())
                .body(TestIntIsZeroErrorResponse.builder()
                        .errorMessage(exception.getMessage())
                        .uri(request.getRequestURI())
                        .errorCode(exception.getErrorCode())
                        .httpStatus(exception.getHttpStatus())
                        .build());

        ResponseEntity<ErrorResponse> response = handler.handle(request, exception);
        assertThat(response).usingRecursiveComparison().isEqualTo(expected);
    }
}
