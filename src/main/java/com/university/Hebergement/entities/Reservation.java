package com.university.Hebergement.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


    @Entity
    @Getter @Setter
    public class Reservation {

        @Id
        private String idReservation;

        private LocalDate dateReservation;
        private boolean estValide;

        @ManyToOne
        private Chambre chambre;

        @ManyToMany
        private List<Etudiant> etudiants = new ArrayList<>();
    }

