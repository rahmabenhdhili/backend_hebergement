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
public class ReservationDTO {
    private String anneeUniversitaire;
    private String nomBloc;
    @Getter
    private Long numeroChambre;
    private List<String> etudiantsCINs;

}
