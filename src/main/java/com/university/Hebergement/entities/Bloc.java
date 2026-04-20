package com.university.Hebergement.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Bloc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBloc;

    private String nomBloc;

    @ManyToOne
    @JsonIgnoreProperties("blocs")  // ✅ affiche le foyer mais ignore sa liste de blocs
    private Foyer foyer;

    @OneToMany(mappedBy = "bloc", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("bloc")   // ✅ affiche les chambres mais ignore leur référence au bloc
    private List<Chambre> chambres = new ArrayList<>();
}