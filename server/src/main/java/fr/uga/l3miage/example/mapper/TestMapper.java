package fr.uga.l3miage.example.mapper;

import fr.uga.l3miage.example.models.TestEntity;
import fr.uga.l3miage.example.request.CreateTestRequest;
import fr.uga.l3miage.example.response.Test;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Correspond à un Mapper qui va permettre plus facilement de faire l'approche <b color="yellow">DTO</b><br>
 * <br>
 * Les 3 comportements possibles pour un mapper :<br>
 * <ul>
 *     <li>si les attributs ont le même nom, alors pas besoin d'explicité le mapping</li>
 *     <li>si les attributs n'ont pas le même nom, alors obligatoire d'expliciter le mapping avec un source, target</li>
 *     <li>si les attributs ont besoin d'un traitement spécial, utiliser un MapperUtils</li>
 * </ul>
 * <br>
 * Les annotations :
 * <ul>
 *     <li>{@link Mapper} permet de dire que l'interface, doit être implémenté par mapstruct, et que pour cela, le mapper utilise un mapper utile TestMapperUtils</li>
 * </ul>
 */
@Mapper(uses = TestMapperUtils.class)
public interface TestMapper {
    /**
     * Cette fonction va faire le mapping d'une entité vers le <b color="yellow">DTO</b> de Test
     * @param testEntity l'entité à mapper en <b color="yellow">DTO</b>
     * @return le <b color="yellow">DTO</b> d'une entité Test
     */
    Test toDto(TestEntity testEntity);

    /**
     * Cette fonction fait le mapping entre une requête de création d'une entité test et l'entité elle même.<br>
     * <br>
     * 2 implémentations de<b color="yellow">@Mapping</b>:
     * <ul>
     *     <li> pour le champ <b>testInt</b>, on a besoin d'additionner 2 chiffres donc on utilise un util, qui prend en paramètre toute la request(".")</li>
     *     <li>pour le champ <b>fieldMapping</b>, on doit le mapper avec la source <b>fieldNotMappingAutomatically</b></li>
     * </ul>
     * @param request de création d'une entité qui va être transformée en TestEntity
     * @return la TestEntity correspondante
     */
    @Mapping(target = "testInt", source = ".", qualifiedBy = TestMapperUtils.ToSumTestInt.class)
    @Mapping(target = "fieldMapping", source = "fieldNotMappingAutomatically")
    TestEntity toEntity(CreateTestRequest request);


    /**
     * La fonction merge va mélanger le target avec la source, et quand une valeur n'est pas la même de la source au target elle est modifiée<br>
     * <br>
     * Les annotations :
     * <ul>
     *     <li>{@link MappingTarget} permet de dire que c'est le paramètre qui va être modifié</li>
     *     <li>{@link NonNull} permet de vérifier que le target est non null </li>
     * </ul>
     * @param baseEntity l'entité de base à modifier
     * @param test la source des modifications
     */
    void mergeTestEntity(@MappingTarget @NonNull TestEntity baseEntity, Test test);

}
