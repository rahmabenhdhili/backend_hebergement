package com.university.Hebergement.IService;

import com.university.Hebergement.DTO.ReservationDTO;
import com.university.Hebergement.entities.Reservation;

public interface IReservationService {
    Reservation AddReservation(ReservationDTO request);
}
