package fr.uga.l3miage.example.models;

import fr.uga.l3miage.example.enums.Places;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CarEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String immat;
    private Places place;

    @OneToOne
    private TransmissionEntity transmission;

    @OneToOne
    private MotorEntity motor;

    @OneToMany(mappedBy = "car",cascade = CascadeType.ALL)
    private Set<AccessoryEntity> accessories;

    @OneToMany(mappedBy = "car",cascade = CascadeType.ALL)
    private Set<CarBodyPartEntity> carBodyParts;
}
