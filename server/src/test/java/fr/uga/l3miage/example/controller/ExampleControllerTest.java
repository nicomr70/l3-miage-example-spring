package fr.uga.l3miage.example.controller;

import fr.uga.l3miage.example.config.HelloWordConfig;
import fr.uga.l3miage.example.error.*;
import fr.uga.l3miage.example.exception.rest.DescriptionAlreadyUseRestException;
import fr.uga.l3miage.example.exception.rest.IsNotTestRestException;
import fr.uga.l3miage.example.exception.rest.TestEntityNotFoundRestException;
import fr.uga.l3miage.example.models.TestEntity;
import fr.uga.l3miage.example.repository.TestRepository;
import fr.uga.l3miage.example.request.CreateTestRequest;
import fr.uga.l3miage.example.service.ExampleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;


/**
 * Ici on teste le composant Example<br>
 * <br>
 * Les annotations :
 * <ul>
 *     <li>{@link SpringBootTest} permet de monter le framework spring pour les tests<br>
 *     <b color="red">indispensable si vous avez besoin d'injection</b><br>
 *     Les 2 arguments importants sont :
 *     <ul>
 *         <li><b>webEnvironnement</b> qui correspond à comment démarrer spring boot, options possibles : <br>
 *         <ul>
 *             <li>{@link SpringBootTest.WebEnvironment#MOCK} permet de dire que l'environnement web est simulé</li>
 *             <li>{@link SpringBootTest.WebEnvironment#RANDOM_PORT} permet de dire qu'un port random est utilisé pour lancer réellement le serveur</li>
 *             <li>{@link SpringBootTest.WebEnvironment#DEFINED_PORT} permet de donner le port où le serveur doit démarrer</li>
 *             <li>{@link SpringBootTest.WebEnvironment#NONE} permet de dire qu'aucun environment web est utile</li>
 *         </ul>
 *         </li>
 *         <li><b>properties</b> permet d'override des configurations dans l'application.yml, lorsque vous utilisez :
 *         <ul>
 *             <li><b>spring.jpa.database-platform=org.hibernate.dialect.H2Dialect</b> permet de dire que vous utilisez une base H2 pour vos tests</li>
 *         </ul>
 *         </li>
 *     </ul>
 *     </li>
 *     <li>{@link AutoConfigureTestDatabase} permet de configurer le serveur de manière automatique sur la BD H2</li>
 *     <li>{@link AutoConfigureWebTestClient} permet de configurer le testRestTemplate automatiquement</li>
 * </ul>
 */
@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
class ExampleControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private HelloWordConfig helloWordConfig;
    @SpyBean
    private ExampleService spyExampleService;
    @Autowired
    private TestRepository testRepository;

    /**
     * Éxécuté à la fin de chaque test
     */
    @AfterEach
    void clear() {
        testRepository.deleteAll();
    }

    @Test
    void getHelloWord() {
        final HttpHeaders headers = new HttpHeaders();

        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("isInError", "false");

        ResponseEntity<String> response = testRestTemplate.exchange(
                "/exemple/hello?isInError={isInError}", HttpMethod.GET, new HttpEntity<>(null, headers), String.class, urlParams);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(helloWordConfig.getHelloWord());
    }

    @Test
    void getHelloWordIsInError() {
        final HttpHeaders headers = new HttpHeaders();

        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("isInError", "true");

        ResponseEntity<String> response = testRestTemplate.exchange(
                "/exemple/hello?isInError={isInError}", HttpMethod.GET, new HttpEntity<>(null, headers), String.class, urlParams);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotEqualTo(helloWordConfig.getHelloWord());
    }

    @Test
    void getEntityTest() {
        final String description = "description";
        final HttpHeaders headers = new HttpHeaders();

        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("description", description);

        fr.uga.l3miage.example.response.Test excepted = fr.uga.l3miage.example.response.Test
                .builder()
                .description("description")
                .fieldMapping("TestConfigWithProperties field mapping")
                .testInt(1)
                .isTest(true)
                .build();

        doReturn(excepted).when(spyExampleService).getTest(description);

        ResponseEntity<fr.uga.l3miage.example.response.Test> response = testRestTemplate.exchange(
                "/exemple/{description}", HttpMethod.GET, new HttpEntity<>(null, headers),
                fr.uga.l3miage.example.response.Test.class, urlParams);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(excepted);
    }

    /**
     * Ici c'est un TestConfigWithProperties intégratif
     */
    @Test
    void getEntityTestError404() {
        final String description = "description";
        final HttpHeaders headers = new HttpHeaders();

        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("description", description);

        ResponseEntity<fr.uga.l3miage.example.response.Test> response = testRestTemplate.exchange(
                "/exemple/{description}", HttpMethod.GET, new HttpEntity<>(null, headers),
                fr.uga.l3miage.example.response.Test.class, urlParams);

        fr.uga.l3miage.example.response.Test excepted = fr.uga.l3miage.example.response.Test
                .builder()
                .description("description")
                .fieldMapping("TestConfigWithProperties field mapping")
                .testInt(1)
                .isTest(true)
                .build();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * TestConfigWithProperties intégratif
     */
    @Test
    void createTestEntity200() {
        final HttpHeaders headers = new HttpHeaders();
        final CreateTestRequest testRequest = CreateTestRequest
                .builder()
                .description("TestConfigWithProperties")
                .isTest(true)
                .testIntMapperUtil1(1)
                .testIntMapperUtil2(1)
                .fieldNotMappingAutomatically("fieldNotMappingAutomatically")
                .build();

        ResponseEntity<Void> response = testRestTemplate.exchange(
                "/exemple/", HttpMethod.POST, new HttpEntity<>(testRequest, headers),
                Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(testRepository.count()).isEqualTo(1);
        verify(spyExampleService, times(1)).createTest(testRequest);
    }

    @Test
    void createTestEntity400_TestIntZeroError() {
        final HttpHeaders headers = new HttpHeaders();
        final CreateTestRequest testRequest = CreateTestRequest
                .builder()
                .description("TestConfigWithProperties")
                .isTest(true)
                .testIntMapperUtil1(1)
                .testIntMapperUtil2(-1)
                .fieldNotMappingAutomatically("fieldNotMappingAutomatically")
                .build();

        ResponseEntity<TestIntIsZeroErrorResponse> response = testRestTemplate.exchange(
                "/exemple/", HttpMethod.POST, new HttpEntity<>(testRequest, headers),
                TestIntIsZeroErrorResponse.class);

        TestIntIsZeroErrorResponse responseExpected = TestIntIsZeroErrorResponse
                .builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .uri("/exemple/")
                .errorCode(ErrorCode.TEST_INT_IS_ZERO_ERROR)
                .build();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(testRepository.count()).isZero();
        assertThat(Objects.requireNonNull(response.getBody()).getErrorCode()).isEqualTo(ErrorCode.TEST_INT_IS_ZERO_ERROR);
        assertThat(response.getBody()).usingRecursiveComparison()
                .ignoringFields("errorMessage")
                .isEqualTo(responseExpected);
        verify(spyExampleService, times(1)).createTest(testRequest);
    }

    @Test
    void createTestEntity400_IsNotTestError() {
        final HttpHeaders headers = new HttpHeaders();
        final CreateTestRequest testRequest = CreateTestRequest
                .builder()
                .description("TestConfigWithProperties")
                .isTest(false)
                .testIntMapperUtil1(1)
                .testIntMapperUtil2(1)
                .fieldNotMappingAutomatically("fieldNotMappingAutomatically")
                .build();

        ResponseEntity<IsNotTestErrorResponse> response = testRestTemplate.exchange(
                "/exemple/", HttpMethod.POST, new HttpEntity<>(testRequest, headers),
                IsNotTestErrorResponse.class);

        final IsNotTestErrorResponse responseExpected = IsNotTestErrorResponse
                .builder()
                .errorCode(ErrorCode.IS_NOT_TEST_ERROR)
                .uri("/exemple/")
                .request(testRequest)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(testRepository.count()).isZero();
        assertThat(Objects.requireNonNull(response.getBody()).getErrorCode()).isEqualTo(ErrorCode.IS_NOT_TEST_ERROR);
        assertThat(response.getBody()).usingRecursiveComparison()
                .ignoringFields("errorMessage")
                .isEqualTo(responseExpected);
        verify(spyExampleService, times(1)).createTest(testRequest);
    }

    @Test
    void createTestEntity400_DescriptionAlreadyUseError() {
        final HttpHeaders headers = new HttpHeaders();
        final CreateTestRequest testRequest = CreateTestRequest
                .builder()
                .description("description")
                .isTest(true)
                .testIntMapperUtil1(1)
                .testIntMapperUtil2(1)
                .fieldNotMappingAutomatically("fieldNotMappingAutomatically")
                .build();

        doThrow(new DescriptionAlreadyUseRestException("", "description")).when(spyExampleService).createTest(testRequest);

        ResponseEntity<DescriptionAlreadyUseErrorResponse> response = testRestTemplate.exchange(
                "/exemple/", HttpMethod.POST, new HttpEntity<>(testRequest, headers),
                DescriptionAlreadyUseErrorResponse.class);

        final DescriptionAlreadyUseErrorResponse responseExpected = DescriptionAlreadyUseErrorResponse
                .builder()
                .errorCode(ErrorCode.DESCRIPTION_ALREADY_USE_ERROR)
                .uri("/exemple/")
                .description("description")
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(testRepository.count()).isZero();
        assertThat(Objects.requireNonNull(response.getBody()).getErrorCode()).isEqualTo(ErrorCode.DESCRIPTION_ALREADY_USE_ERROR);
        assertThat(response.getBody()).usingRecursiveComparison()
                .ignoringFields("errorMessage")
                .isEqualTo(responseExpected);
        verify(spyExampleService, times(1)).createTest(testRequest);
    }

    @Test
    void updateTestEntity202() {
        final TestEntity testEntity = TestEntity
                .builder()
                .description("description")
                .fieldMapping("TestConfigWithProperties")
                .testInt(1)
                .isTest(true)
                .build();

        testRepository.save(testEntity);


        final fr.uga.l3miage.example.response.Test test = fr.uga.l3miage.example.response.Test
                .builder()
                .description("TestConfigWithProperties")
                .fieldMapping("TestConfigWithProperties")
                .testInt(10)
                .isTest(true)
                .build();

        ResponseEntity<Void> response = testRestTemplate.exchange(
                "/exemple/{lastDescription}", HttpMethod.PATCH, new HttpEntity<>(test), Void.class, Map.of("lastDescription", "description"));


        final TestEntity testEntityExpected = TestEntity
                .builder()
                .description("TestConfigWithProperties")
                .fieldMapping("TestConfigWithProperties")
                .testInt(10)
                .isTest(true)
                .build();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(testRepository.findByDescription("TestConfigWithProperties")).isPresent();
        assertThat(testRepository.findByDescription("TestConfigWithProperties")).get()
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(testEntityExpected);
    }

    @Test
    void updateTestEntity404() {
        final fr.uga.l3miage.example.response.Test test = fr.uga.l3miage.example.response.Test
                .builder()
                .description("TestConfigWithProperties")
                .fieldMapping("TestConfigWithProperties")
                .testInt(10)
                .isTest(true)
                .build();

        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("lastDescription", "description");

        doThrow(new TestEntityNotFoundRestException("", "description")).when(spyExampleService).updateTest(eq("description"), any(fr.uga.l3miage.example.response.Test.class));

        ResponseEntity<TestNotFoundErrorResponse> response = testRestTemplate.exchange(
                "/exemple/{lastDescription}", HttpMethod.PATCH, new HttpEntity<>(test, null),
                TestNotFoundErrorResponse.class, urlParams);

        final TestNotFoundErrorResponse responseExpected = TestNotFoundErrorResponse
                .builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .uri("/exemple/description")
                .errorCode(ErrorCode.TEST_IS_NOT_FOUND)
                .description("description")
                .build();


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).usingRecursiveComparison()
                .ignoringFields("errorMessage")
                .isEqualTo(responseExpected);
    }

    @Test
    void updateTestEntity400_TestIntZeroError() {
        final HttpHeaders headers = new HttpHeaders();
        final fr.uga.l3miage.example.response.Test test = fr.uga.l3miage.example.response.Test
                .builder()
                .description("TestConfigWithProperties")
                .fieldMapping("TestConfigWithProperties")
                .testInt(0)
                .isTest(true)
                .build();

        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("lastDescription", "description");

        ResponseEntity<TestIntIsZeroErrorResponse> response = testRestTemplate.exchange(
                "/exemple/{lastDescription}", HttpMethod.PATCH, new HttpEntity<>(test, headers),
                TestIntIsZeroErrorResponse.class, urlParams);

        final TestIntIsZeroErrorResponse responseExpected = TestIntIsZeroErrorResponse
                .builder()
                .uri("/exemple/description")
                .errorCode(ErrorCode.TEST_INT_IS_ZERO_ERROR)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(Objects.requireNonNull(response.getBody()).getErrorCode()).isEqualTo(ErrorCode.TEST_INT_IS_ZERO_ERROR);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .ignoringFields("errorMessage")
                .isEqualTo(responseExpected);
        verify(spyExampleService,times(1)).updateTest("description",test);
    }

    @Test
    void updateTestEntity400_IsNotTestError() {
        final HttpHeaders headers = new HttpHeaders();
        final fr.uga.l3miage.example.response.Test test = fr.uga.l3miage.example.response.Test
                .builder()
                .description("TestConfigWithProperties")
                .fieldMapping("TestConfigWithProperties")
                .testInt(10)
                .isTest(false)
                .build();

        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("lastDescription", "description");

        doThrow(IsNotTestRestException.class).when(spyExampleService).updateTest(eq("description"),any(fr.uga.l3miage.example.response.Test.class));

        ResponseEntity<IsNotTestErrorResponse> response = testRestTemplate.exchange(
                "/exemple/{lastDescription}", HttpMethod.PATCH, new HttpEntity<>(test, headers),
                IsNotTestErrorResponse.class, urlParams);

        final IsNotTestErrorResponse responseExpected = IsNotTestErrorResponse
                .builder()
                .uri("/exemple/description")
                .errorCode(ErrorCode.IS_NOT_TEST_ERROR)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(Objects.requireNonNull(response.getBody()).getErrorCode()).isEqualTo(ErrorCode.IS_NOT_TEST_ERROR);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .ignoringFields("errorMessage")
                .isEqualTo(responseExpected);
        verify(spyExampleService,times(1)).updateTest("description",test);
    }

    @Test
    void updateTestEntity400_DescriptionAlreadyUseError() {
        final HttpHeaders headers = new HttpHeaders();
        final fr.uga.l3miage.example.response.Test test = fr.uga.l3miage.example.response.Test
                .builder()
                .description("TestConfigWithProperties")
                .fieldMapping("TestConfigWithProperties")
                .testInt(10)
                .isTest(true)
                .build();

        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("lastDescription", "description");

        doThrow(new DescriptionAlreadyUseRestException("","TestConfigWithProperties")).when(spyExampleService).updateTest(eq("description"),any(fr.uga.l3miage.example.response.Test.class));

        ResponseEntity<DescriptionAlreadyUseErrorResponse> response = testRestTemplate.exchange(
                "/exemple/{lastDescription}", HttpMethod.PATCH, new HttpEntity<>(test, headers),
                DescriptionAlreadyUseErrorResponse.class, urlParams);


        final DescriptionAlreadyUseErrorResponse responseExpected = DescriptionAlreadyUseErrorResponse
                .builder()
                .uri("/exemple/description")
                .errorCode(ErrorCode.DESCRIPTION_ALREADY_USE_ERROR)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .description("TestConfigWithProperties")
                .build();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(Objects.requireNonNull(response.getBody()).getErrorCode()).isEqualTo(ErrorCode.DESCRIPTION_ALREADY_USE_ERROR);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .ignoringFields("errorMessage")
                .isEqualTo(responseExpected);
        verify(spyExampleService,times(1)).updateTest("description",test);
    }

    /**
     * Test de bout en bout
     */
    @Test
    void deleteTest200(){
        final HttpHeaders headers = new HttpHeaders();
        final TestEntity testEntity = TestEntity
                .builder()
                .description("test")
                .isTest(true)
                .testInt(10)
                .fieldMapping("fieldMapping")
                .build();
        testRepository.save(testEntity);

        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("description", "test");

        ResponseEntity<Void> response = testRestTemplate.exchange(
                "/exemple/{description}", HttpMethod.DELETE, new HttpEntity<>(null, headers),
                Void.class, urlParams);

        assertThat(testRepository.count()).isZero();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(spyExampleService,times(1)).deleteTest("test");
    }


    /**
     * Test de bout en bout
     */
    @Test
    void deleteTest418_TestEntityNotFoundException(){
        final HttpHeaders headers = new HttpHeaders();

        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("description", "test");

        ResponseEntity<TestEntityNotDeletedErrorResponse> response = testRestTemplate.exchange(
                "/exemple/{description}", HttpMethod.DELETE, new HttpEntity<>(null, headers),
                TestEntityNotDeletedErrorResponse.class, urlParams);

        final TestEntityNotDeletedErrorResponse responseExpected = TestEntityNotDeletedErrorResponse
                .builder()
                .uri("/exemple/test")
                .errorCode(ErrorCode.TEST_ENTITY_NOT_DELETED_ERROR)
                .httpStatus(HttpStatus.I_AM_A_TEAPOT)
                .build();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.I_AM_A_TEAPOT);
        assertThat(Objects.requireNonNull(response.getBody()).getErrorCode()).isEqualTo(ErrorCode.TEST_ENTITY_NOT_DELETED_ERROR);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .ignoringFields("errorMessage")
                .isEqualTo(responseExpected);
        verify(spyExampleService,times(1)).deleteTest("test");

    }


    /**
     * Test de bout en bout
     */
    @Test
    void deleteTest418_multipleEntityHaveSameDescriptionException() {
        TestEntity testEntity = TestEntity.builder()
                .description("test")
                .testInt(10)
                .isTest(true)
                .fieldMapping("fde")
                .build();
        TestEntity testEntity1 = TestEntity.builder()
                        .description("test")
                        .testInt(10)
                        .isTest(true)
                        .fieldMapping("fde")
                        .build();
        testRepository.save(testEntity);
        testRepository.save(testEntity1);

        final HttpHeaders headers = new HttpHeaders();

        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("description", "test");


        ResponseEntity<TestEntityNotDeletedErrorResponse> response = testRestTemplate.exchange(
                "/exemple/{description}", HttpMethod.DELETE, new HttpEntity<>(null, headers),
                TestEntityNotDeletedErrorResponse.class, urlParams);

        final TestEntityNotDeletedErrorResponse responseExpected = TestEntityNotDeletedErrorResponse
                .builder()
                .uri("/exemple/test")
                .errorCode(ErrorCode.TEST_ENTITY_NOT_DELETED_ERROR)
                .httpStatus(HttpStatus.I_AM_A_TEAPOT)
                .build();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.I_AM_A_TEAPOT);
        assertThat(Objects.requireNonNull(response.getBody()).getErrorCode()).isEqualTo(ErrorCode.TEST_ENTITY_NOT_DELETED_ERROR);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .ignoringFields("errorMessage")
                .isEqualTo(responseExpected);
        verify(spyExampleService,times(1)).deleteTest("test");
    }


}
