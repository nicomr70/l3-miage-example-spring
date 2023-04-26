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
    @Id
    @GeneratedValue
    private Long idReponse;

    private String label;

    private Boolean estValide;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReponseEntity that = (ReponseEntity) o;
        return getIdReponse() != null && Objects.equals(getIdReponse(), that.getIdReponse());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
