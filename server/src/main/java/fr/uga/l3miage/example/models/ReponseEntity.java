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
public class ReponseEntity {

    private String label;

    private Boolean estValide;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReponseEntity that = (ReponseEntity) o;
        return getLabel() != null && Objects.equals(getLabel(), that.getLabel());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}