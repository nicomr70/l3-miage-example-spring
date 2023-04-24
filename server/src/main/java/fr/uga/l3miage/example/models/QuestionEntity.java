package fr.uga.l3miage.example.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;


@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class QuestionEntity {

    private String label;

    @OneToMany
    private Set<Reponse> reponses;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        QuestionEntity that = (QuestionEntity) o;
        return getLabel() != null && Objects.equals(getLabel(), that.getLabel());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
