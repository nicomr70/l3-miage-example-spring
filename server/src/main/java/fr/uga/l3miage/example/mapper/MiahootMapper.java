package fr.uga.l3miage.example.mapper;

import fr.uga.l3miage.example.models.MiahootEntity;
import fr.uga.l3miage.example.request.CreateTestRequest;
import fr.uga.l3miage.example.response.Miahoot;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

//kTest
@Mapper(uses = MiahootMapperUtils.class)
public interface MiahootMapper {

    Miahoot toDto(MiahootEntity miahootEntity);

    @Mapping(target = "miahootInt", source = ".", qualifiedBy = MiahootMapperUtils.ToSumMiahootInt.class)
    @Mapping(target = "miahootMapping", source = "fieldNotMappingAutomatically")
    MiahootEntity toEntity(CreateTestRequest request);


    void mergeMiahootEntity(@MappingTarget @NonNull MiahootEntity baseEntity, Test test);


}