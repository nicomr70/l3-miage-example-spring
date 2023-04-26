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
            return miahootMapper.toDto(miahootComponent.getMiahoot(userId));
        } catch (MiahootEntityNotFoundException ex) {
            throw new MiahootEntityNotFoundRestException(String.format("Impossible de charger l'entité. Raison : [%s]",ex.getMessage()));
        }
    }


    public List<Miahoot> getMiahoot(final String nom) {
        try {
            return miahootMapper.toDto(miahootComponent.getMiahoot(nom));
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
        if (miahoot.getmiahootInt() != 0) {
            try {
                miahootComponent.updateMiahoot(userId, nom, miahoot);
            } catch (MiahootEntityNotFoundException ex) {
                throw new MiahootEntityNotFoundRestException(String.format("Impossible de charger l'entité. Raison : [%s]",ex.getMessage()),lastDescription,ex);
            } catch (IsNotTestException ex) {
                throw new IsNotTestRestException("Une erreur lors de la mise à jour de l'entité TestConfigWithProperties a été détectée.",null,ex);
            } catch (DescriptionAlreadyExistException ex) {
                throw new DescriptionAlreadyUseRestException(ERROR_DETECTED,test.getDescription(),ex);
            }
        }else throw new TestIntIsZeroRestException("L'attribut testInt ne peut pas être égal à zéro");
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