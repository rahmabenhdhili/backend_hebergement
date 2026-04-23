package com.university.Hebergement.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Foyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFoyer;

    private String nomFoyer;
    private Long capaciteFoyer;

    @OneToMany(mappedBy = "foyer")
    @JsonIgnoreProperties("foyer")  // ✅ affiche les blocs mais ignore leur référence au foyer
    private List<Bloc> blocs = new ArrayList<>();
}