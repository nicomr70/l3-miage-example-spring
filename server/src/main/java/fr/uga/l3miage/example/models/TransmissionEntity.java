package fr.uga.l3miage.example.models;

import fr.uga.l3miage.example.enums.TransmissionType;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TransmissionEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    private TransmissionType type;
    private Integer nbRapport;
}
