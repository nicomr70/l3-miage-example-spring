package fr.uga.l3miage.example.component;

import fr.uga.l3miage.example.config.HelloWordConfig;
import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.TestMapper;
import fr.uga.l3miage.example.models.TestEntity;
import fr.uga.l3miage.example.repository.TestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


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
 * </ul>
 */
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
class ExampleComponentTest {
    @Autowired
    private HelloWordConfig helloWordConfig;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private ExampleComponent exampleComponent;
    @Autowired
    private TestMapper testMapper;

    /**
     * Éxécuté à chaque fin de test
     */
    @AfterEach
    void clear() {
        testRepository.deleteAll();
    }

    @Test
    void getHelloWord() throws IsInErrorException {
        assertThat(exampleComponent.getHelloWord(false)).isEqualTo(helloWordConfig.getHelloWord());
        assertThrows(IsInErrorException.class, () -> exampleComponent.getHelloWord(true));
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
        testRepository.save(testEntity);

        assertThrows(TestEntityNotFoundException.class, () -> exampleComponent.getTest("une fausse description"));

        TestEntity testEntityResponse = exampleComponent.getTest("description");

        assertThat(testEntityResponse).usingRecursiveComparison()
                .isEqualTo(testEntity);
    }

    @Test
    void createTest() throws IsNotTestException, DescriptionAlreadyExistException {
        TestEntity testEntity = TestEntity
                .builder()
                .description("description")
                .fieldMapping("TestConfigWithProperties")
                .testInt(1)
                .isTest(false)
                .build();

        assertThrows(IsNotTestException.class, () -> exampleComponent.createTest(testEntity));

        testEntity.setIsTest(true);

        exampleComponent.createTest(testEntity);

        assertThat(testRepository.count()).isOne();
        assertThrows(DescriptionAlreadyExistException.class, () -> exampleComponent.createTest(testEntity));
    }

    @Test
    void updateTest() throws IsNotTestException, DescriptionAlreadyExistException, TestEntityNotFoundException {
        TestEntity testEntity = TestEntity
                .builder()
                .description("description")
                .fieldMapping("TestConfigWithProperties")
                .testInt(1)
                .isTest(false)
                .build();

        TestEntity testEntity1 = TestEntity
                .builder()
                .description("description 1")
                .fieldMapping("TestConfigWithProperties")
                .testInt(1)
                .isTest(false)
                .build();

        testRepository.save(testEntity);
        testRepository.save(testEntity1);

        fr.uga.l3miage.example.response.Test test = fr.uga.l3miage.example.response.Test
                .builder()
                .isTest(false)
                .description("description")
                .fieldMapping("TestConfigWithProperties")
                .testInt(1)
                .build();

        assertThrows(IsNotTestException.class, () -> exampleComponent.updateTest("description", test));
        test.setIsTest(true);
        test.setDescription("description 1");
        assertThrows(DescriptionAlreadyExistException.class, () -> exampleComponent.updateTest("description", test));
        test.setDescription("une description qui n'existe pas");
        assertThrows(TestEntityNotFoundException.class, () -> exampleComponent.updateTest("une description qui n'existe pas", test));
        test.setDescription("description");
        test.setTestInt(10);

        exampleComponent.updateTest("description", test);

        fr.uga.l3miage.example.response.Test testExpected = fr.uga.l3miage.example.response.Test
                .builder()
                .description("description")
                .fieldMapping("TestConfigWithProperties")
                .testInt(10)
                .isTest(true)
                .build();

        assertThat(testMapper.toDto(exampleComponent.getTest("description")))
                .usingRecursiveComparison()
                .isEqualTo(testExpected);

    }

    @Transactional
    @Test
    void deleteTest() throws MultipleEntityHaveSameDescriptionException, TestEntityNotFoundException {
        TestEntity testEntity1 = TestEntity
                .builder()
                .description("description")
                .fieldMapping("TestConfigWithProperties")
                .testInt(1)
                .isTest(true)
                .build();
        TestEntity testEntity2 = TestEntity
                .builder()
                .description("description")
                .fieldMapping("TestConfigWithProperties")
                .testInt(1)
                .isTest(true)
                .build();

        TestEntity testEntity = TestEntity
                .builder()
                .description("description unique")
                .fieldMapping("TestConfigWithProperties")
                .testInt(1)
                .isTest(true)
                .build();

        testRepository.save(testEntity1);
        testRepository.save(testEntity2);
        testRepository.save(testEntity);

        assertThrows(MultipleEntityHaveSameDescriptionException.class, () -> exampleComponent.deleteTest("description"));
        assertThrows(TestEntityNotFoundException.class, () -> exampleComponent.deleteTest("une description qui n'existe pas"));

        exampleComponent.deleteTest("description unique");

        assertThat(testRepository.count()).isZero();
        assertThrows(TestEntityNotFoundException.class, () -> exampleComponent.getTest("description unique"));
    }
}
