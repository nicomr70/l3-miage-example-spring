package fr.uga.l3miage.example.service;

import fr.uga.l3miage.example.component.ExampleComponent;
import fr.uga.l3miage.example.config.HelloWordConfig;
import fr.uga.l3miage.example.exception.rest.*;
import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.models.TestEntity;
import fr.uga.l3miage.example.request.CreateTestRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Permet de tester la configuration {@link HelloWordConfig}<br>
 <br>
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
 * </ul>
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class ExampleServiceTest {
    /**
     * Ici on utilise un mock afin de simuler tous les comportements du composant appelés par le service
     */
    @MockBean
    private ExampleComponent exampleComponent;
    @Autowired
    private ExampleService exampleService;
    @Autowired
    private HelloWordConfig helloWordConfig;

    @Test
    void getHelloWord() throws IsInErrorException {
        when(exampleComponent.getHelloWord(true)).thenThrow(IsInErrorException.class);
        assertThrows(IsInErrorRestException.class, () -> exampleService.helloWord(true));
        when(exampleComponent.getHelloWord(false)).thenReturn(helloWordConfig.getHelloWord());
        assertThat(exampleService.helloWord(false)).isEqualTo(helloWordConfig.getHelloWord());
    }

    @Test
    void getTest() throws TestEntityNotFoundException {
        TestEntity testEntity = TestEntity
                .builder()
                .description("description")
                .fieldMapping("TestConfigWithProperties")
                .testInt(1)
                .isTest(true)
                .build();

        when(exampleComponent.getTest("description")).thenReturn(testEntity);
        when(exampleComponent.getTest("une mauvaise description")).thenThrow(TestEntityNotFoundException.class);

        fr.uga.l3miage.example.response.Test testExpected = fr.uga.l3miage.example.response.Test
                .builder()
                .description("description")
                .fieldMapping("TestConfigWithProperties")
                .testInt(1)
                .isTest(true)
                .build();


        fr.uga.l3miage.example.response.Test testResponse = exampleService.getTest("description");

        assertThrows(TestEntityNotFoundRestException.class, () -> exampleService.getTest("une mauvaise description"));
        assertThat(testResponse).usingRecursiveComparison()
                .isEqualTo(testExpected);
        verify(exampleComponent).getTest("description");
        verify(exampleComponent).getTest("une mauvaise description");
    }

    @Test
    void createTest() throws IsNotTestException, DescriptionAlreadyExistException {
        CreateTestRequest testRequest = CreateTestRequest
                .builder()
                .description("description")
                .fieldNotMappingAutomatically("TestConfigWithProperties")
                .testIntMapperUtil1(1)
                .testIntMapperUtil2(-1)
                .isTest(true)
                .build();

        assertThrows(TestIntIsZeroRestException.class, () -> exampleService.createTest(testRequest));

        testRequest.setTestIntMapperUtil2(9);

        doThrow(IsNotTestException.class).when(exampleComponent).createTest(any(TestEntity.class));
        assertThrows(IsNotTestRestException.class, () -> exampleService.createTest(testRequest));
        doThrow(DescriptionAlreadyExistException.class).when(exampleComponent).createTest(any(TestEntity.class));
        assertThrows(DescriptionAlreadyUseRestException.class, () -> exampleService.createTest(testRequest));
        doNothing().when(exampleComponent).createTest(any(TestEntity.class));
        assertDoesNotThrow(() -> exampleService.createTest(testRequest));

        /*
         * Avec les mocks et les spy on peut vérifier que les appels on bien été fait de la bonne manière
         */
        verify(exampleComponent, times(3)).createTest(any(TestEntity.class));
    }

    @Test
    void updateTest() throws IsNotTestException, DescriptionAlreadyExistException, TestEntityNotFoundException {
        fr.uga.l3miage.example.response.Test test = fr.uga.l3miage.example.response.Test
                .builder()
                .description("description")
                .fieldMapping("TestConfigWithProperties")
                .testInt(0)
                .isTest(true)
                .build();

        assertThrows(TestIntIsZeroRestException.class, () -> exampleService.updateTest("description", test));

        test.setTestInt(1);

        doThrow(IsNotTestException.class).when(exampleComponent).updateTest(eq("description"),any(fr.uga.l3miage.example.response.Test.class));
        assertThrows(IsNotTestRestException.class, () -> exampleService.updateTest("description",test));
        doThrow(DescriptionAlreadyExistException.class).when(exampleComponent).updateTest(eq("description"),any(fr.uga.l3miage.example.response.Test.class));
        assertThrows(DescriptionAlreadyUseRestException.class, () -> exampleService.updateTest("description",test));
        doThrow(TestEntityNotFoundException.class).when(exampleComponent).updateTest(eq("description"),any(fr.uga.l3miage.example.response.Test.class));
        assertThrows(TestEntityNotFoundRestException.class, () -> exampleService.updateTest("description",test));
        doNothing().when(exampleComponent).updateTest(eq("description"),any(fr.uga.l3miage.example.response.Test.class));
        assertDoesNotThrow(() -> exampleService.updateTest("description",test));

        verify(exampleComponent, times(4)).updateTest(eq("description"),any(fr.uga.l3miage.example.response.Test.class));
    }

    @Test
    void deleteTest() throws MultipleEntityHaveSameDescriptionException, TestEntityNotFoundException {
        doThrow(MultipleEntityHaveSameDescriptionException.class).when(exampleComponent).deleteTest("description");
        assertThrows(TestEntityNotDeletedRestException.class, () -> exampleService.deleteTest("description"));
        doThrow(TestEntityNotFoundException.class).when(exampleComponent).deleteTest("description");
        assertThrows(TestEntityNotDeletedRestException.class, () -> exampleService.deleteTest("description"));
        verify(exampleComponent,times(2)).deleteTest("description");
    }
}
