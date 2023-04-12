package fr.uga.l3miage.example.config.handler;

import fr.uga.l3miage.example.error.DescriptionAlreadyUseErrorResponse;
import fr.uga.l3miage.example.error.ErrorResponse;
import fr.uga.l3miage.example.exception.rest.DescriptionAlreadyUseRestException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Permet de tester le handler de l'exception d'API {@link DescriptionAlreadyUseRestException}
 */
class DescriptionAlreadyUseExceptionHandlerTest {
    @Test
    void testHandle() {
        DescriptionAlreadyUseExceptionHandler handler = new DescriptionAlreadyUseExceptionHandler();
        DescriptionAlreadyUseRestException exception = new DescriptionAlreadyUseRestException("Description already use","description");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("TestConfigWithProperties");
        ResponseEntity<DescriptionAlreadyUseErrorResponse> expected = ResponseEntity.status(exception.getHttpStatus())
                .body(DescriptionAlreadyUseErrorResponse.builder()
                        .errorMessage(exception.getMessage())
                        .uri(request.getRequestURI())
                        .errorCode(exception.getErrorCode())
                        .httpStatus(exception.getHttpStatus())
                        .description(exception.getDescription())
                        .build());

        ResponseEntity<ErrorResponse> response = handler.handle(request, exception);
        assertThat(response).usingRecursiveComparison().isEqualTo(expected);
    }
}
