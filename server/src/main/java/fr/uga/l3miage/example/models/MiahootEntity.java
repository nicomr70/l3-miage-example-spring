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
public class MiahootEntity {

    private String nom;

    @OneToMany
    private List<Question> questions;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MiahootEntity that = (MiahootEntity) o;
        return getNom() != null && Objects.equals(getNom(), that.getNom());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
