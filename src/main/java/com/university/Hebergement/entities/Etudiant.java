package com.university.Hebergement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Etudiant {

    @Id
    private Long cin;

    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String ecole;

    @ManyToMany(mappedBy = "etudiants")
    private List<Reservation> reservations = new ArrayList<>();
}
