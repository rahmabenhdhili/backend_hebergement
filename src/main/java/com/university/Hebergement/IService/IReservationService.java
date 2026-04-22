package com.university.Hebergement.IService;

import com.university.Hebergement.DTO.ReservationDTO;
import com.university.Hebergement.entities.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface IReservationService {
    Reservation AddReservation(ReservationDTO request);
    List<Reservation> getReservationsBetweenDates(LocalDate debut, LocalDate fin);
    Reservation annulerReservation(long cinEtudiant);
}
