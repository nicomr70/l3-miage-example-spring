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

    private static final String ERROR_DETECTED = "Une erreur lors de la création de l'entité MiahootConfigWithProperties à été détecté.";
    private final MiahootComponent miahootComponent;
    private final MiahootMapper miahootMapper;


    public Miahoot getMiahoot(final long userId, final String nom) {
        try {
            return miahootMapper.toDto(miahootComponent.getMiahoot(userId, nom));
        } catch (MiahootEntityNotFoundException ex) {
            throw new MiahootEntityNotFoundRestException(String.format("Impossible de charger l'entité. Raison : [%s]",ex.getMessage()));
        }
    }


    public List<Miahoot> getMiahoot(final long userId) {
        List<Miahoot> l = new ArrayList<>();
        try {
            for (MiahootEntity m : miahootComponent.getMiahoot(userId)) {
                l.add(miahootMapper.toDto(m));
            }
            return l;
        } catch (MiahootEntityNotFoundException ex) {
            throw new MiahootEntityNotFoundRestException(String.format("Impossible de charger l'entité. Raison : [%s]",ex.getMessage()));
        }
    }


    public List<Miahoot> getMiahoot(final String nom) {
        List<Miahoot> l = new ArrayList<>();
        try {
            for (MiahootEntity m : miahootComponent.getMiahoot(nom)) {
                l.add(miahootMapper.toDto(m));
            }
            return l;
        } catch (MiahootEntityNotFoundException ex) {
            throw new MiahootEntityNotFoundRestException(String.format("Impossible de charger l'entité. Raison : [%s]",ex.getMessage()));
        }
    }

    public void createMiahoot(final CreateMiahootRequest createMiahootRequest){
        MiahootEntity newMiahootEntity = miahootMapper.toEntity(createMiahootRequest);
            try {
                miahootComponent.createMiahoot(newMiahootEntity);
            } catch (MiahootAlreadyExistException ex) {
               throw new MiahootAlreadyExistRestException(ERROR_DETECTED, ex);
            }
    }

    public void updateMiahoot(final long userId, final String nom, final Miahoot miahoot){
            try {
                miahootComponent.updateMiahoot(userId, nom, miahoot);
            } catch (MiahootEntityNotFoundException ex) {
                throw new MiahootEntityNotFoundRestException(String.format("Impossible de charger l'entité. Raison : [%s]",ex.getMessage()));
            } catch (MiahootAlreadyExistException ex) {
                throw new MiahootAlreadyExistRestException(ERROR_DETECTED);
            } catch (MiahootUserIdNotSameException ex) {
                throw new MiahootUserIdNotSameRestException("Une erreur lors de la mise à jour de l'entité.",ex);
            }
    }


    @Transactional
    public void deleteMiahoot(final long userId, final String nom) {
        try {
            miahootComponent.deleteMiahoot(userId, nom);
        } catch (MiahootEntityNotFoundException ex) {
            throw new MiahootEntityNotFoundRestException(ex.getMessage());
        }
    }

    @Transactional
    public void deleteMiahoot(final long userId) {
        try {
            miahootComponent.deleteMiahoot(userId);
        } catch (MiahootEntityNotFoundException ex) {
            throw new MiahootEntityNotFoundRestException(ex.getMessage());
        }
    }

    @Transactional
    public void deleteMiahoot(final String nom) {
        try {
            miahootComponent.deleteMiahoot(nom);
        } catch (MiahootEntityNotFoundException ex) {
            throw new MiahootEntityNotFoundRestException(ex.getMessage());
        }
    }


}