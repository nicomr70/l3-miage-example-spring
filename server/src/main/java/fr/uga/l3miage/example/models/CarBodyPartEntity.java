package fr.uga.l3miage.example.models;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CarBodyPartEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String noSeries;

    private String Color;
}
