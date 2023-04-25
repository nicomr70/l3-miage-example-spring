package fr.uga.l3miage.example.component;

import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.MiahootMapper;
import fr.uga.l3miage.example.models.MiahootEntity;
import fr.uga.l3miage.example.models.TestEntity;
import fr.uga.l3miage.example.repository.MiahootRepository;
import fr.uga.l3miage.example.response.Miahoot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
//import org.springframework.context.annotation.Bean;
//import fr.uga.l3miage.example.config.HelloWordConfig;


@Component
@RequiredArgsConstructor
public class MiahootComponent {
    private final MiahootRepository miahootRepository;
    private final MiahootMapper miahootMapper;

    public MiahootEntity getMiahoot(final long userId, final String nom) throws MiahootEntityNotFoundException {
        return miahootRepository.findByUserIdAndNom(userId, nom)
                .orElseThrow(() -> new MiahootEntityNotFoundException(String.format("Aucune entité n'a été trouvée pour le userId [%l] et le nom [%s]", userId, nom)));
    }

    public List<MiahootEntity> getMiahoot(final long userId) throws MiahootEntityNotFoundException {
        List<MiahootEntity> l = miahootRepository.findAllByUserId(userId);
        if (l.size()>0)
            return l;
        else
            throw new MiahootEntityNotFoundException(String.format("Aucune entité n'a été trouvée pour le userId [%l]", userId));
    }

    public List<MiahootEntity> getMiahoot(final String nom) throws MiahootEntityNotFoundException {
        List<MiahootEntity> l = miahootRepository.findAllByNom(nom);
        if (l.size()>0)
            return l;
        else
            throw new MiahootEntityNotFoundException(String.format("Aucune entité n'a été trouvée pour le nom [%s]", nom));
    }


//    i am hereeeeeeeeeeeeeeee
//    public void createTest(final MiahootEntity entity) throws NomAlreadyExistException , MiahootAlreadyExistException {
//        if (Boolean.TRUE.equals(entity.getIsTest())) {
//            if (testRepository.findByDescription(entity.getDescription()).isPresent()) {
//                throw new DescriptionAlreadyExistException(String.format("La description %s existe déjà en BD.", entity.getDescription()), entity.getDescription());
//            }
//            testRepository.save(entity);
//        } else throw new IsNotTestException("Le champs isTest n'est pas à true, donc erreur technique levée", entity);
//    }

}
