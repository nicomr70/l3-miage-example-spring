package fr.uga.l3miage.example.repository;

import fr.uga.l3miage.example.models.MiahootEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



@Repository
public interface MiahootRepository extends JpaRepository<MiahootEntity,Long> {

    Optional<List<MiahootEntity>>findByUserId(final long userId);

    Optional<MiahootEntity> findByUserIdAndNom(final long userId, final String nom);

    int deleteByUserIdAndNom(final long userId, final String nom);
}
