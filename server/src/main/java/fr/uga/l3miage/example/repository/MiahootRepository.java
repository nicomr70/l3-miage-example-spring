package fr.uga.l3miage.example.repository;

import fr.uga.l3miage.example.models.MiahootEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MiahootRepository extends JpaRepository<MiahootEntity,Long> {
    Optional<MiahootEntity> findByIdMiahoot(final Long idMiahoot);

    int deleteByIdMiahoot(final Long idMiahoot);
}
