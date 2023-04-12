package fr.uga.l3miage.example.service;

import fr.uga.l3miage.example.component.ExampleComponent;
import fr.uga.l3miage.example.exception.rest.*;
import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.TestMapper;
import fr.uga.l3miage.example.models.TestEntity;
import fr.uga.l3miage.example.request.CreateTestRequest;
import fr.uga.l3miage.example.response.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


/**
 * Dans un service, on va catcher toutes les exceptions techniques, vérifier les règles métiers et renvoyer des exceptions d'API<br>
 * Cette classe correspond à la logique métier de votre application<br>
 * Les Annotations :
 * <ul>
 *     <li>{@link Service} permet de créer un service spring, cette annotation est en quelque sorte un stéréotype d'un @{@link Bean}, car elle va permettre de créer un candidat à l'injection.</li>
 *     <li>{@link RequiredArgsConstructor} crée un constructeur avec les attributs finaux, ou les attributs annotés par {@link lombok.NonNull}.<br>Voir la doc <a href="https://projectlombok.org/features/constructor">projectlombok/feature/RequiredArgConstructor</a></li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class ExampleService {
    private static final String ERROR_DETECTED = "Une erreur lors de la création de l'entité TestConfigWithProperties à été détecté.";
    private final ExampleComponent exampleComponent;
    private final TestMapper testMapper;


    /**
     * @param isInError boolean qui dit si oui ou non, on veut une erreur
     * @return "Hello Word"
     */
    public String helloWord(final boolean isInError) {
        try {
            return exampleComponent.getHelloWord(isInError);
        } catch (IsInErrorException ex) {
            throw new IsInErrorRestException("Une erreur à été demandée par le client, ici elle est catch par le service qui renvoie une rest exception et qui a comme cause l'exception technique", ex);
        }
    }

    /**
     * @param description de l'entité test recherchée
     * @return le dto test correspondant à la description
     */
    public Test getTest(final String description) {
        try {
            return testMapper.toDto(exampleComponent.getTest(description));
        } catch (TestEntityNotFoundException ex) {
            throw new TestEntityNotFoundRestException(String.format("Impossible de charger l'entité. Raison : [%s]",ex.getMessage()),description,ex);
        }
    }

    /**
     * @param createTestRequest la requête qui permet de créer une entité test
     */
    public void createTest(final CreateTestRequest createTestRequest) {
        TestEntity newTestEntity = testMapper.toEntity(createTestRequest);
        if(newTestEntity.getTestInt()!=0){
            try {
                exampleComponent.createTest(newTestEntity);
            } catch (IsNotTestException ex) {
                throw new IsNotTestRestException(ERROR_DETECTED,createTestRequest,ex);
            } catch (DescriptionAlreadyExistException ex) {
                throw new DescriptionAlreadyUseRestException(ERROR_DETECTED,newTestEntity.getDescription(),ex);
            }
        }else{
            throw new TestIntIsZeroRestException("La somme des testInt ne doit pas être égale à zéro");
        }
    }

    /**
     * @param lastDescription la description actuelle de l'entité test
     * @param test le DTO qui va permettre de merge les informations
     */
    public void updateTest(final String lastDescription,final Test test) {
        if (test.getTestInt() != 0) {
            try {
                exampleComponent.updateTest(lastDescription, test);
            } catch (TestEntityNotFoundException ex) {
                throw new TestEntityNotFoundRestException(String.format("Impossible de charger l'entité. Raison : [%s]",ex.getMessage()),lastDescription,ex);
            } catch (IsNotTestException ex) {
                throw new IsNotTestRestException("Une erreur lors de la mise à jour de l'entité TestConfigWithProperties a été détectée.",null,ex);
            } catch (DescriptionAlreadyExistException ex) {
                throw new DescriptionAlreadyUseRestException(ERROR_DETECTED,test.getDescription(),ex);
            }
        }else throw new TestIntIsZeroRestException("L'attribut testInt ne peut pas être égal à zéro");
    }

    /**
     * Les annotations :
     * <ul>
     *     <li>{@link Transactional} cette fonction est isolée dans une transaction, càd que si la fonction échoue alors un rollback() est exécuté</li>
     * </ul>
     * @param description de l'entité Test à supprimer
     */
    @Transactional
    public void deleteTest(String description) {
        try {
            exampleComponent.deleteTest(description);
        } catch (MultipleEntityHaveSameDescriptionException | TestEntityNotFoundException ex) {
            throw new TestEntityNotDeletedRestException(ex.getMessage());
        }
    }


}
