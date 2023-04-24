package fr.uga.l3miage.example.models;

import fr.uga.l3miage.example.enums.Carburant;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MotorEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String noSeries;

    private Float cylindre;

    private Float puissance;

    @Enumerated(EnumType.STRING)
    private Carburant carburant;

}
