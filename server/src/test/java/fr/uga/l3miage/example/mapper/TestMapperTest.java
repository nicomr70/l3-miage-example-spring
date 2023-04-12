package fr.uga.l3miage.example.mapper;

import fr.uga.l3miage.example.models.TestEntity;
import fr.uga.l3miage.example.request.CreateTestRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


/**
 * Permet de tester le Mapper {@link TestMapper}<br>
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
class TestMapperTest {
    @Autowired
    private TestMapper testMapper;

    @Test
    void toEntityByCreateTestRequest() {
        CreateTestRequest request = CreateTestRequest
                .builder()
                .description("description")
                .fieldNotMappingAutomatically("TestConfigWithProperties")
                .testIntMapperUtil1(1)
                .testIntMapperUtil2(0)
                .isTest(true)
                .build();

        TestEntity testEntityResponse = testMapper.toEntity(request);

        TestEntity testEntityExpected = TestEntity
                .builder()
                .description("description")
                .fieldMapping("TestConfigWithProperties")
                .testInt(1)
                .isTest(true)
                .build();

        assertThat(testEntityResponse).usingRecursiveComparison()
                .isEqualTo(testEntityExpected);
    }

    @Test
    void mergeTestEntity() {
        TestEntity targetEntity = TestEntity
                .builder()
                .description("description")
                .fieldMapping("TestConfigWithProperties")
                .testInt(1)
                .isTest(true)
                .build();

        fr.uga.l3miage.example.response.Test testToMerge = fr.uga.l3miage.example.response.Test
                .builder()
                .description("description merge")
                .fieldMapping("TestConfigWithProperties")
                .testInt(2)
                .isTest(true)
                .build();

        testMapper.mergeTestEntity(targetEntity, testToMerge);

        TestEntity testEntityExpected = TestEntity
                .builder()
                .description("description merge")
                .fieldMapping("TestConfigWithProperties")
                .testInt(2)
                .isTest(true)
                .build();

        assertThat(targetEntity).usingRecursiveComparison()
                .isEqualTo(testEntityExpected);
    }

    @Test
    void toDto() {
        TestEntity testEntity = TestEntity
                .builder()
                .description("description")
                .fieldMapping("TestConfigWithProperties")
                .testInt(1)
                .isTest(true)
                .build();

        fr.uga.l3miage.example.response.Test testResponse = testMapper.toDto(testEntity);

        fr.uga.l3miage.example.response.Test testExcepted = fr.uga.l3miage.example.response.Test
                .builder()
                .description("description")
                .fieldMapping("TestConfigWithProperties")
                .testInt(1)
                .isTest(true)
                .build();

        assertThat(testResponse).usingRecursiveComparison()
                .isEqualTo(testExcepted);
    }

}
