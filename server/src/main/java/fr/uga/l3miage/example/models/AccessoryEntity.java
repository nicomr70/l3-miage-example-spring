package fr.uga.l3miage.example.models;


import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AccessoryEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String referenceId;

    private String name;

    private Float price;

    @ManyToOne(fetch = FetchType.LAZY)
    private CarEntity carEntity;
}
