package fr.uga.l3miage.example.component;

import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.TestMapper;
import fr.uga.l3miage.example.models.TestEntity;
import fr.uga.l3miage.example.repository.TestRepository;
import fr.uga.l3miage.example.response.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import fr.uga.l3miage.example.config.HelloWordConfig;

/**
 * Pour respecter l'architecture hexagonale, ici nous ne traitons que les données
 * <br>
 * Les Annotations :
 * <ul>
 *     <li>{@link Component} permet de créer un composant spring, cette annotation est en quelque sorte un stéréotype d'un @{@link Bean}, car elle va permettre de créer un candidat à l'injection.</li>
 *     <li>{@link RequiredArgsConstructor} crée un constructeur avec les attributs finaux, ou les attributs annotés par {@link lombok.NonNull}.<br>Voir la doc <a href="https://projectlombok.org/features/constructor">projectlombok/feature/RequiredArgConstructor</a></li>
 * </ul>
 */
@Component
@RequiredArgsConstructor
public class ExampleComponent {
    private final TestRepository testRepository;
    private final TestMapper testMapper;
    /**
     * Ici le String helloWord est directement injecté par contructeur (via le constructeur construit par l'annotation {@link RequiredArgsConstructor}) car un bean de type String est défini dans {@link HelloWordConfig}
     */
    private final String helloWord;

    /**
     * @param isInError si nous voulons une erreur ou non
     * @return "Hello word"
     * @throws IsInErrorException si isInError est égale à <b>true</b>
     */
    public String getHelloWord(boolean isInError) throws IsInErrorException {
        if (!isInError) return helloWord;
        throw new IsInErrorException("Le client a demandé d'être en erreur");
    }

    /**
     * @param description de l'entité Test à récupérer
     * @return une {@link TestEntity} correspondant à description donnée
     * @throws TestEntityNotFoundException si aucune entité Test n'est trouvée
     */
    public TestEntity getTest(final String description) throws TestEntityNotFoundException {
        return testRepository.findByDescription(description)
                .orElseThrow(() -> new TestEntityNotFoundException(String.format("Aucune entité n'a été trouvée pour la description [%s]", description), description));
    }

    /**
     * @param entity à créer en base de données
     * @throws IsNotTestException si dans l'entité à créer le champ isTest est égal à <b>false</b>
     * @throws DescriptionAlreadyExistException Si la description dans l'entité à créer existe déjà en BD
     */
    public void createTest(final TestEntity entity) throws IsNotTestException, DescriptionAlreadyExistException {
        if (Boolean.TRUE.equals(entity.getIsTest())) {
            if (testRepository.findByDescription(entity.getDescription()).isPresent()) {
                throw new DescriptionAlreadyExistException(String.format("La description %s existe déjà en BD.", entity.getDescription()), entity.getDescription());
            }
            testRepository.save(entity);
        } else throw new IsNotTestException("Le champs isTest n'est pas à true, donc erreur technique levée", entity);
    }

    /**
     * @param lastDescription la description de l'entité en BD à mettre à jour
     * @param test le <b color="yellow"> DTO</b> de l'entité qui va permettre la modification
     * @throws TestEntityNotFoundException si l'entité avec une lastDescription n'est pas trouvée en BD
     * @throws IsNotTestException si le champ isTest n'est pas true
     * @throws DescriptionAlreadyExistException si la description qui est modifiée existe déjà en BD
     */
    public void updateTest(final String lastDescription, final Test test) throws TestEntityNotFoundException, IsNotTestException, DescriptionAlreadyExistException {
        if (Boolean.TRUE.equals(test.getIsTest())) {
            if (!lastDescription.equals(test.getDescription()) && testRepository.findByDescription(test.getDescription()).isPresent()) {
                throw new DescriptionAlreadyExistException(String.format("La description %s existe déjà en BD.", test.getDescription()), test.getDescription());
            }
            TestEntity actualEntity = testRepository.findByDescription(lastDescription)
                    .orElseThrow(() -> new TestEntityNotFoundException(String.format("Aucune entité n'a été trouvé pour la description [%s]", lastDescription), lastDescription));
            testMapper.mergeTestEntity(actualEntity, test);
            testRepository.save(actualEntity);
        } else throw new IsNotTestException("Le champs isTest n'est pas à true, donc erreur technique levée", null);
    }


    /**
     * @param description de l'entité à supprimer
     * @throws MultipleEntityHaveSameDescriptionException <b color="red">Impossible à lever si on passe toujours par les endpoints</b>, mais le sera s'il existe plusieurs entités Test qui sont supprimées. Un <b color="green">rollback()</b> est effectué si problème
     * @throws TestEntityNotFoundException si l'entité avec la description fournit n'est pas trouvée
     */
    public void deleteTest(final String description) throws MultipleEntityHaveSameDescriptionException, TestEntityNotFoundException {
        int deleted = testRepository.deleteByDescription(description);
        if (deleted > 1)
            throw new MultipleEntityHaveSameDescriptionException("Plusieurs entités ont la même description alors que c'est impossible niveau métier !!");
        else if (deleted == 0)
            throw new TestEntityNotFoundException("L'entité à supprimer n'a pas été trouvée", description);


    }
}
