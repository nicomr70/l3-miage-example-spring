package fr.uga.l3miage.example.repository;

import fr.uga.l3miage.example.models.ReponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReponseRepository extends JpaRepository<ReponseEntity,Long> {

    Optional<ReponseEntity> findByIdReponse(final Long idReponse);

    int deleteByIdReponse(final Long idReponse);

}
