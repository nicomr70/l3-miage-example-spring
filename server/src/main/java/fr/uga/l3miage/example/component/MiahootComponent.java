package fr.uga.l3miage.example.component;

import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.MiahootMapper;
import fr.uga.l3miage.example.models.MiahootEntity;
import fr.uga.l3miage.example.repository.MiahootRepository;
import fr.uga.l3miage.example.response.Miahoot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class MiahootComponent {
    private final MiahootRepository miahootRepository;
    private final MiahootMapper miahootMapper;

    public MiahootEntity getMiahoot(final long userId, final String nom) throws MiahootEntityNotFoundException {
        return miahootRepository.findByUserIdAndNom(userId, nom)
                .orElseThrow(() -> new MiahootEntityNotFoundException(String.format("Aucune entité n'a été trouvée pour le userId [%d] et le nom [%s]", userId, nom)));
    }

    public List<MiahootEntity> getMiahoot(final long userId) throws MiahootEntityNotFoundException {
        List<MiahootEntity> l = miahootRepository.findAllByUserId(userId);
        if (l.size()>0)
            return l;
        else
            throw new MiahootEntityNotFoundException(String.format("Aucune entité n'a été trouvée pour le userId [%d]", userId));
    }

    public List<MiahootEntity> getMiahoot(final String nom) throws MiahootEntityNotFoundException {
        List<MiahootEntity> l = miahootRepository.findAllByNom(nom);
        if (l.size()>0)
            return l;
        else
            throw new MiahootEntityNotFoundException(String.format("Aucune entité n'a été trouvée pour le nom [%s]", nom));
    }


    public void createMiahoot(final MiahootEntity entity) throws MiahootAlreadyExistException {
//        userId is present and nom is present -> throw MiahootAlreadyExistException
//        userId is not present -> all good
//        userId present and nom isnt -> all good
        if (miahootRepository.findByUserIdAndNom(entity.getUserId(), entity.getNom()).isPresent()){
            throw new MiahootAlreadyExistException(String.format("L'entité Miahoot existe déjà pour le userId [%d] et le nom [%s]", entity.getUserId(), entity.getNom()));
        }
        miahootRepository.save(entity);
    }


    public void updateMiahoot(final long userId, final String nom, final Miahoot miahoot) throws MiahootEntityNotFoundException, MiahootAlreadyExistException, MiahootUserIdNotSameException {
        // diff userId -> throw MiahootUserIdNotSameException
        // same userId diff nom -> check if new miahoot exists

        if (userId == miahoot.getUserId()){
            throw new MiahootUserIdNotSameException(String.format("Le userId [%d] est différent du userId [%d] de l'entité Miahoot", userId, miahoot.getUserId()));
        }
        if (!nom.equals(miahoot.getNom()) && !miahootRepository.findByUserIdAndNom(miahoot.getUserId(), miahoot.getNom()).isPresent()) {
            throw new MiahootAlreadyExistException(String.format("Le miahoot %s existe déjà en BD."));
        }
        MiahootEntity actualEntity = miahootRepository.findByUserIdAndNom(userId, nom)
                .orElseThrow(() -> new MiahootEntityNotFoundException(String.format("Aucune entité n'a été trouvé pour le userId [%d] et le nom [%s]", userId, nom)));
        miahootMapper.mergeMiahootEntity(actualEntity, miahoot);
        miahootRepository.save(actualEntity);
    }

    public void deleteMiahoot(final long userId, final String nom) throws MiahootEntityNotFoundException {
        // if looking for miahoot that doesnt exist -> entity not found exception
        // if looking for miahoot that exists -> delete

        if (!miahootRepository.findByUserIdAndNom(userId, nom).isPresent()){
            throw new MiahootEntityNotFoundException(String.format("L'entité à supprimer n'a pas été trouvée"));
        }
        miahootRepository.deleteByUserIdAndNom(userId, nom);
    }

    public void deleteMiahoot(final long userId) throws MiahootEntityNotFoundException {
        List<MiahootEntity> l = miahootRepository.findAllByUserId(userId);
        if (l.isEmpty()){
            throw new MiahootEntityNotFoundException(String.format("Les entités à supprimer n'ont pas été trouvées"));
        }
        for (MiahootEntity m : l){
            miahootRepository.deleteByUserIdAndNom(userId, m.getNom());
        }
    }

    public void deleteMiahoot(final String nom) throws MiahootEntityNotFoundException {
        List<MiahootEntity> l = miahootRepository.findAllByNom(nom);
        if (l.isEmpty()){
            throw new MiahootEntityNotFoundException(String.format("Les entités à supprimer n'ont pas été trouvées"));
        }
        for (MiahootEntity m : l){
            miahootRepository.deleteByUserIdAndNom(m.getUserId(), nom);
        }
    }


}
