package com.university.Hebergement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Chambre {

    @Id
    private Long numeroChambre;

    @Enumerated(EnumType.STRING)
    private TypeChambre type;

    @ManyToOne
    @JsonIgnoreProperties("chambres")
    @JsonIgnore
    private Bloc bloc;

    @JsonIgnore
    @OneToMany(mappedBy = "chambre")
    private List<Reservation> reservations = new ArrayList<>();
}
