package com.university.Hebergement.repository;

import com.university.Hebergement.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
    List<Reservation> findByDateReservationBetween(LocalDate debut, LocalDate fin);}
