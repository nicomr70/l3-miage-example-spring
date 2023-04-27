package fr.uga.l3miage.example.component;

import fr.uga.l3miage.example.config.HelloWordConfig;
import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.QuestionMapper;
import fr.uga.l3miage.example.models.QuestionEntity;
import fr.uga.l3miage.example.models.TestEntity;
import fr.uga.l3miage.example.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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
public class QuestionComponent {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
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
     * @param label de l'entité Test à récupérer
     * @return une {@link TestEntity} correspondant à description donnée
     * @throws TestEntityNotFoundException si aucune entité Test n'est trouvée
     */
    public QuestionEntity getQuestion(final String label) throws QuestionEntityNotFoundException {
        return questionRepository.findByLabel(label)
                .orElseThrow(() -> new QuestionEntityNotFoundException(String.format("Aucune entité n'a été trouvée pour le label [%s]", label), label));
    }

    /**
     * @param label à créer en base de données
     */
    public void createQuestion(String label) throws LabelAlreadyExistException{

        if (questionRepository.findByLabel(label).isPresent()) {
            throw new LabelAlreadyExistException(String.format("La label %s existe déjà en BD.", label), label);
        }
        QuestionEntity newQuestion = new QuestionEntity();
        newQuestion.setLabel(label);
        questionRepository.save(newQuestion);
    }


}
