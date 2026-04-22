package com.university.Hebergement.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


    @Entity
    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class Reservation {

        @Id
        private String idReservation;

        private LocalDate dateReservation;
        private boolean estValide;

        @ManyToOne(fetch = FetchType.LAZY) // pour éviter les chargements inutites
        private Chambre chambre;

        @ManyToMany(fetch = FetchType.LAZY)
        private List<Etudiant> etudiants = new ArrayList<>();
    }

