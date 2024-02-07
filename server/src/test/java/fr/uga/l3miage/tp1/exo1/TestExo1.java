package fr.uga.l3miage.tp1.exo1;

import fr.uga.l3miage.tp1.exo1.utils.DatabaseScriptExecutor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class TestExo1 {

    @BeforeAll
    static void initialise(){
        DatabaseScriptExecutor.createDatabaseExo1();
    }

    @AfterAll
    static void dropTable() {
        DatabaseScriptExecutor.dropDatabase();
    }

    @Test
    void TestValidationJpa_bikeEntity() throws Exception {
        Class<?> classz = Class.forName("fr.uga.l3miage.tp1.exo1.models.BikeEntity");
        // vérification que tous les champs sont déclarés
        assertThat(classz.getDeclaredFields())
                .as("Tous les champs doivent être déclarer.")
                .hasSize(7);

        // vérification que tous les champs sont préfixés de @Column
        assertThat(Arrays.stream(classz.getDeclaredFields())
                .allMatch(field ->
                        Arrays.stream(field.getAnnotations())
                        .filter(annotation -> annotation.annotationType().equals(Column.class))
                        .count() == 1))
                .as("Tous les champs doivent avoir un @Column")
                .isTrue();

        // vérification des types énum
        assertThat(((Enumerated)Arrays.stream(Arrays.stream(classz.getDeclaredFields())
                        .filter(field -> field.getName().equals("shifterType"))
                        .findFirst()
                        .orElseThrow(() -> new Exception("L'attribue shifterType n'est pas déclarer dans la classe"))
                        .getAnnotations())
                .filter(annotation -> annotation.annotationType().equals(Enumerated.class))
                .findFirst()
                .orElseThrow(()->new Exception("L'attribue shifterType n'a pas l'annotation @Enumerated")))
                .value())
                .as("L'attribue shifterType devrait être en mode EnumType.ORDINAL")
                .isEqualTo(EnumType.ORDINAL);

        assertThat(((Enumerated)Arrays.stream(Arrays.stream(classz.getDeclaredFields())
                        .filter(field -> field.getName().equals("cylinderNumber"))
                        .findFirst()
                        .orElseThrow(() -> new Exception("L'attribue cylinderNumber n'est pas déclarer dans la classe"))
                        .getAnnotations())
                .filter(annotation -> annotation.annotationType().equals(Enumerated.class))
                .findFirst()
                .orElseThrow(()->new Exception("L'attribue cylinderNumber n'a pas l'annotation @Enumerated")))
                .value())
                .as("L'attribue cylinderNumber devrait être en mode EnumType.STRING")
                .isEqualTo(EnumType.STRING);


        // vérification des annotations sur la class mère
        assertThat(classz.getAnnotations())
                .as("Doit avoir 2 annotations")
                .hasSize(2);

        assertThat(Arrays.stream(classz.getAnnotations()).filter(annotation -> annotation.annotationType().equals(Table.class)).toArray())
                .as("La classe bike entity doit avoir l'annotation @Table")
                .hasSize(1);

        assertThat(Arrays.stream(classz.getAnnotations()).filter(annotation -> annotation.annotationType().equals(Entity.class)).toArray())
                .as("La classe bike entity doit avoir l'annotation @Entity")
                .hasSize(1);
    }


    @Test
    void TestValidationJpa_carEntity() throws Exception {
        Class<?> classz = Class.forName("fr.uga.l3miage.tp1.exo1.models.CarEntity");
        // vérification que tous les champs sont déclarés
        assertThat(classz.getDeclaredFields())
                .as("Tous les champs doivent être déclarer.")
                .hasSize(8);

        // vérification que tous les champs sont préfixés de @Column
        assertThat(Arrays.stream(classz.getDeclaredFields())
                .allMatch(field ->
                        Arrays.stream(field.getAnnotations())
                                .filter(annotation -> annotation.annotationType().equals(Column.class))
                                .count() == 1))
                .as("Tous les champs doivent avoir un @Column")
                .isTrue();

        // vérification des types énum
        assertThat(((Enumerated)Arrays.stream(Arrays.stream(classz.getDeclaredFields())
                        .filter(field -> field.getName().equals("powerType"))
                        .findFirst()
                        .orElseThrow(() -> new Exception("L'attribue powerType n'est pas déclarer dans la classe"))
                        .getAnnotations())
                .filter(annotation -> annotation.annotationType().equals(Enumerated.class))
                .findFirst()
                .orElseThrow(() -> new Exception("L'attribue powerType n'a pas l'annotation @Enumerated")))
                .value())
                .as("L'attribue powerType devrait être en mode EnumType.ORDINAL")
                .isEqualTo(EnumType.ORDINAL);

        assertThat(((Enumerated)Arrays.stream(Arrays.stream(classz.getDeclaredFields())
                        .filter(field -> field.getName().equals("weightUnity"))
                        .findFirst()
                        .orElseThrow(() -> new Exception("L'attribue weightUnity n'est pas déclarer dans la classe"))
                        .getAnnotations())
                .filter(annotation -> annotation.annotationType().equals(Enumerated.class))
                .findFirst()
                .orElseThrow(() -> new Exception("L'attribue weightUnity n'a pas l'annotation @Enumerated")))
                .value())
                .as("L'attribue weightUnity devrait être en mode EnumType.STRING")
                .isEqualTo(EnumType.STRING);


        // vérification des annotations sur la class mère
        assertThat(classz.getAnnotations())
                .as("La classe CarEntity doit avoir 2 annotations")
                .hasSize(2);

        assertThat(Arrays.stream(classz.getAnnotations()).filter(annotation -> annotation.annotationType().equals(Table.class)).toArray())
                .as("La classe  CarEntity doit avoir l'annotation @Table")
                .hasSize(1);

        assertThat(Arrays.stream(classz.getAnnotations()).filter(annotation -> annotation.annotationType().equals(Entity.class)).toArray())
                .as("La classe CarEntity doit avoir l'annotation @Entity")
                .hasSize(1);
    }
}
