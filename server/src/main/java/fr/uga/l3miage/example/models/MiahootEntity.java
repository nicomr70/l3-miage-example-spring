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
public class MiahootEntity {
    @Id
    @GeneratedValue
    private Long idMiahoot;

    private String nom;

    private boolean estEnCours;

    @OneToMany
    private List<QuestionEntity> FaitPartieDe;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MiahootEntity that = (MiahootEntity) o;
        return getIdMiahoot() != null && Objects.equals(getIdMiahoot(), that.getIdMiahoot());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
