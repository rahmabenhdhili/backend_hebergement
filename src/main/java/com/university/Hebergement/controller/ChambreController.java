package com.university.Hebergement.controller;

import com.university.Hebergement.DTO.ChambreDTO;
import com.university.Hebergement.entities.Chambre;
import com.university.Hebergement.service.ChambreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chambres")
@RequiredArgsConstructor
public class ChambreController {

    private final ChambreService chambreService;

    @GetMapping("/bloc/{nomBloc}")
    public List<ChambreDTO> getByBloc(@PathVariable String nomBloc) {
        return chambreService.getChambresByBloc(nomBloc);
    }
}
