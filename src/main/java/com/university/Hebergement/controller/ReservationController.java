package com.university.Hebergement.controller;

import com.university.Hebergement.DTO.ReservationDTO;
import com.university.Hebergement.entities.Reservation;
import com.university.Hebergement.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public Reservation AddReservation(@RequestBody ReservationDTO request){
        return reservationService.AddReservation(request);
    }
}
