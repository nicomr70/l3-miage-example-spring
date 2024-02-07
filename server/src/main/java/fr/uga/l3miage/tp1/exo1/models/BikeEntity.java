package fr.uga.l3miage.tp1.exo1.models;

import fr.uga.l3miage.tp1.exo1.enums.CylinderNumber;
import fr.uga.l3miage.tp1.exo1.enums.ShifterType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bike_triumph")
public class BikeEntity {
    @Id
    @Column(name = "immat")
    private String immatriculation;

    @Column(name="capcity_cc")
    private Integer capacity;

    @Column(name = "cylinder_number")
    @Enumerated(EnumType.STRING)
    private CylinderNumber cylinderNumber;

    @Column(name = "is_automatic")
    private Boolean automatic;

    @Column(name = "have_shifter")
    private Boolean shifter;

    @Column(name="shifter_type")
    @Enumerated(EnumType.ORDINAL)
    private ShifterType shifterType;

    @Column(name="ciruculation_date")
    private LocalDate circulationDate;
}
