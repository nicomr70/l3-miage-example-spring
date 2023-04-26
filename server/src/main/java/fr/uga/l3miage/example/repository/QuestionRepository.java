package fr.uga.l3miage.example.repository;

import fr.uga.l3miage.example.models.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity,Long>  {
    Optional<QuestionEntity> findByIdQuestion(final Long idQuestion);

    int deleteByIdQuestion(final Long idQuestion);
}
