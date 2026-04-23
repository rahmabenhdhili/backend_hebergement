package com.university.Hebergement.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

        @ManyToOne
        @JsonIgnore
        private Chambre chambre;

        @ManyToMany
        private List<Etudiant> etudiants = new ArrayList<>();
    }

