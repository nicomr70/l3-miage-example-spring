package fr.uga.l3miage.example.mapper;

import fr.uga.l3miage.example.request.CreateTestRequest;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Correspond à l'utilitaire pour mapper des champs avec des traitements spéciaux
 * Les annotations :
 * <ul>
 *     <li>{@link Component} permet de créer un composant spring, cette annotation est en quelque sort stereotype d'un @{@link Bean}, car elle va donc permettre de créer un candidat à l'injection.</li>
 *     <li>{@link RequiredArgsConstructor} créer un constructeur avec les attributs finaux, ou les attributs annotés par {@link lombok.NonNull}.<br>Voir la doc <a href="https://projectlombok.org/features/constructor">projectlombok/feature/RequiredArgConstructor</a></li>
 * </ul>
 */
@Component
@RequiredArgsConstructor
public class TestMapperUtils {

    /**
     * Définition de la fonction util<br>
     * Les annotations :
     * <ul>
     *     <li>{@link ToSumTestInt} permet de dire à mapStruct que pour l'util toSumTestInt,il faut utiliser la méthode préfixée de cette annotation</li>
     * </ul>
     * @param request la requête utilisée pour faire la fonction util
     * @return la somme de testInt1 et testIntTest2 dans la requête de création
     */
    @ToSumTestInt
    public int toSumTestInt(final CreateTestRequest request) {
        return request.getTestIntMapperUtil1() + request.getTestIntMapperUtil2();
    }

    /**
     * Cette annotation permet à mapStruct de savoir où trouver la fonction util lors du mapping<br>
     * <br>
     * Les annotations :
     * <ul>
     *  <li>{@link Qualifier} permet de dire à mapStruct que cette @interface est utilisé par mapstruct</li>
     *  <li>{@link Target} Une annotation <b color="red">obligatoire</b> lors de la création d'une <b>@interface</b>.<br>
     *      Elle permet de renseigner où l'on peut placer notre annotation. Sont possibles :
     *          <ul>
     *              <li>{@link ElementType#METHOD} : sur une méthode</li>
     *              <li>{@link ElementType#TYPE} : sur une classe</li>
     *              <li>{@link ElementType#ANNOTATION_TYPE} : sur une annotation</li>
     *              <li>{@link ElementType#CONSTRUCTOR} : sur un constructeur</li>
     *              <li>{@link ElementType#FIELD} : sur un attribut</li>
     *              <li><b>etc...</b></li>
     *              <li>La <a href=""><b> doc</b></a> !</li>
     *          <ul/>
     *   </li>
     *   <li>{@link Retention} une annotation <b color="red">obligatoire</b> lors de la création d'une <b>@interface</b>.<br>
     *      Elle permet de renseigner quoi faire avec les annotations, 3 choix possibles :
     *      <ul>
     *          <li>{@link RetentionPolicy#SOURCE}: mettre les résultats des annotations dans le .class et les supprime par la suite</li>
     *          <li>{@link RetentionPolicy#CLASS}: mettre les résultats des annotations dans le .class, mais garde aussi les annotations dans le .class</li>
     *          <li>{@link RetentionPolicy#RUNTIME}: comme le {@link RetentionPolicy#CLASS}, sauf que les Annotations sont gardées dans la JVM lors de l'exécution d'un programme et peuvent être utilisées lors du run</li>
     *          <li>La <a href="">doc</a> !</li>
     *      </ul>
     *   </li>
     * </ul>
     */
    @Qualifier
    @Retention(RetentionPolicy.CLASS)
    @Target(ElementType.METHOD)
    public @interface ToSumTestInt {
    }
}
