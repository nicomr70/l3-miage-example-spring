package fr.uga.l3miage.example.models;

import fr.uga.l3miage.example.enums.Carburant;
import fr.uga.l3miage.example.enums.Model;
import fr.uga.l3miage.example.enums.Places;
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
public class BonDeCommande {
    @Id
    @GeneratedValue
    private UUID id;

    private String commandId;

    private String color;

    @Enumerated(EnumType.STRING)
    private Places place;

    @Enumerated(EnumType.STRING)
    private Carburant carburant;

    @Enumerated(EnumType.STRING)
    private Model model;

    @Enumerated(EnumType.STRING)
    private TransmissionType transmissionType;
}
