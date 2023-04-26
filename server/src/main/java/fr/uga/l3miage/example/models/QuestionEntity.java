package fr.uga.l3miage.example.models;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class QuestionEntity {

    @Id
    @GeneratedValue
    private Long idQuestion;

    private String label;

    @OneToMany
    private List<ReponseEntity> EstReponseA;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        QuestionEntity that = (QuestionEntity) o;
        return getIdQuestion() != null && Objects.equals(getIdQuestion(), that.getIdQuestion());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
