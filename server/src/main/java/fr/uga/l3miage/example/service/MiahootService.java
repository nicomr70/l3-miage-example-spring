package fr.uga.l3miage.example.service;

import fr.uga.l3miage.example.component.MiahootComponent;
import fr.uga.l3miage.example.exception.rest.*;
import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.MiahootMapper;
import fr.uga.l3miage.example.models.MiahootEntity;
import fr.uga.l3miage.example.request.CreateMiahootRequest;
import fr.uga.l3miage.example.response.Miahoot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MiahootService{

    private static final String ERROR_DETECTED = "Une erreur lors de la création de l'entité TestConfigWithProperties à été détecté.";
    private final MiahootComponent miahootComponent;
    private final MiahootMapper miahootMapper;


    public Miahoot getMiahoot(final long userId, final String nom) {
        try {
            return miahootMapper.toDto(miahootComponent.getMiahoot(userId, nom));
        } catch (MiahootEntityNotFoundException ex) {
            throw new MiahootEntityNotFoundRestException(String.format("Impossible de charger l'entité. Raison : [%s]",ex.getMessage()));
        }
    }


    public List<Miahoot> getMiahoot(final Long userId) {
        try {
            List<Miahoot> listMiahoot = new ArrayList();
            for(MiahootEntity miahootEntity : miahootComponent.getMiahoot(userId)){
                listMiahoot.add(miahootMapper.toDto(miahootEntity));
            }
            return listMiahoot;
        } catch (MiahootEntityNotFoundException ex) {
            throw new MiahootEntityNotFoundRestException(String.format("Impossible de charger l'entité. Raison : [%s]",ex.getMessage()));
        }
    }


    public List<Miahoot> getMiahoot(final String nom) {
        try {
            List<Miahoot> listMiahoot = new ArrayList();
            for(MiahootEntity miahootEntity : miahootComponent.getMiahoot(nom)){
                listMiahoot.add(miahootMapper.toDto(miahootEntity));
            }
            return listMiahoot;
        } catch (MiahootEntityNotFoundException ex) {
            throw new MiahootEntityNotFoundRestException(String.format("Impossible de charger l'entité. Raison : [%s]",ex.getMessage()));
        }
    }

    public void createMiahoot(final CreateMiahootRequest createMiahootRequest) {
        MiahootEntity newMiahootEntity = miahootMapper.toEntity(createMiahootRequest);
            try {
                miahootComponent.createMiahoot(newMiahootEntity);
            } catch (MiahootAlreadyExistException ex) {
                throw new MiahootAlreadyExistRestException(ERROR_DETECTED,createMiahootRequest,ex);
            }
    }

    public void updateMiahoot(final Long userId, final String nom,final Miahoot miahoot) {

            try {
                miahootComponent.updateMiahoot(userId, nom, miahoot);
            } catch (MiahootAlreadyExistException ex) {
                throw new MiahootAlreadyExistRestException(String.format("Le miahoot %s existe déjà en BD."));
            } catch (MiahootUserIdNotSameException ex) {
                throw new MiahootUserIdNotSameRestException("Le userId [%l] est différent du userId [%l] de l'entité Miahoot. Raison : [%s]", userId, miahoot.getUserId(),ex.getMessage());
            } catch (MiahootEntityNotFoundException e) {
                throw new RuntimeException(e);
            }

    }


    @Transactional
    public void deleteMiahoot(final long userId, final String nom) {
        try {
            miahootComponent.deleteMiahoot(userId, nom);
        } catch (MultipleEntityHaveSameDescriptionException | MiahootEntityNotFoundException ex) {
            throw new TestEntityNotDeletedRestException(ex.getMessage());
        }
    }





}