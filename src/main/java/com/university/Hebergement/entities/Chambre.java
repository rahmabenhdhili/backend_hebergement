package com.university.Hebergement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Chambre {

    @Id
    private Long numeroChambre;

    @Enumerated(EnumType.STRING)
    private TypeChambre type;

    @ManyToOne
    private Bloc bloc;

    @OneToMany(mappedBy = "chambre")
    private List<Reservation> reservations = new ArrayList<>();
}
