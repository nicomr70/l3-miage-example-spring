package fr.uga.l3miage.example.mapper;


import fr.uga.l3miage.example.models.QuestionEntity;
import fr.uga.l3miage.example.request.CreateQuestionRequest;
import fr.uga.l3miage.example.response.Question;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    /**
     * Cette fonction va faire le mapping d'une entité vers le <b color="yellow">DTO</b> de Question
     * @param questionEntity l'entité à mapper en <b color="yellow">DTO</b>
     * @return le <b color="yellow">DTO</b> d'une entité Question
     */
    Question toDto(QuestionEntity questionEntity);

    QuestionEntity toEntity(CreateQuestionRequest request);

    /**
     * La fonction merge va mélanger le target avec la source, et quand une valeur n'est pas la même de la source au target elle est modifiée<br>
     * <br>
     * Les annotations :
     * <ul>
     *     <li>{@link MappingTarget} permet de dire que c'est le paramètre qui va être modifié</li>
     *     <li>{@link NonNull} permet de vérifier que le target est non null </li>
     * </ul>
     * @param baseEntity l'entité de base à modifier
     * @param question la source des modifications
     */
    void mergeQuestionEntity(@MappingTarget @NonNull QuestionEntity baseEntity, Question question);
}