package com.university.Hebergement.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChambreDTO {
    private Long numeroChambre;
    private String type;

    private List<ReservationDTO> reservations;
}
