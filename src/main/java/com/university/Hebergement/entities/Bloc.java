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
public class Bloc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBloc;

    private String nomBloc;

    @ManyToOne
    @JsonIgnoreProperties("blocs")
    private Foyer foyer;

    @OneToMany(mappedBy = "bloc",
            cascade = CascadeType.ALL,
            orphanRemoval = true)   // ← AJOUTE ÇA
    @JsonIgnoreProperties("bloc")
    private List<Chambre> chambres = new ArrayList<>();

    // Méthodes utilitaires recommandées
    public void addChambre(Chambre chambre) {
        chambres.add(chambre);
        chambre.setBloc(this);
    }

    public void removeChambre(Chambre chambre) {
        chambres.remove(chambre);
        chambre.setBloc(null);
    }
}