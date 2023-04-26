package fr.uga.l3miage.example.mapper;

import fr.uga.l3miage.example.models.MiahootEntity;
import fr.uga.l3miage.example.request.CreateMiahootRequest;
import fr.uga.l3miage.example.request.CreateTestRequest;
import fr.uga.l3miage.example.response.Miahoot;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

//kTest
@Mapper
public interface MiahootMapper {

    Miahoot toDto(MiahootEntity miahootEntity);

    MiahootEntity toEntity(CreateMiahootRequest request);

    // pas sure qu'on en aie besoin
    void mergeMiahootEntity(@MappingTarget @NonNull MiahootEntity baseEntity, Miahoot miahoot);


}