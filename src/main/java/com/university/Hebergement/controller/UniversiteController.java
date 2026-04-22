package com.university.Hebergement.controller;

import com.university.Hebergement.IService.IUniversiteService;
import com.university.Hebergement.entities.Universite;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/universite")
@AllArgsConstructor
public class UniversiteController {

    private final IUniversiteService universiteService;

    @GetMapping("/getAll")
    public List<Universite> getAll() {
        return universiteService.getAll();
    }

    @GetMapping("/get/{id}")
    public Universite getUniversiteByID(@PathVariable Long id) {
        return universiteService.getUniversiteByID(id);
    }

    @PostMapping("/add")
    public void addUniversite(@RequestBody Universite universite) {
        universiteService.addUniversite(universite);
    }

    @PutMapping("/update")
    public Universite updateUniversite(@RequestBody Universite universite) {
        return universiteService.updateUniversite(universite);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUniversite(@PathVariable Long id) {
        universiteService.deleteUniversite(id);
    }

    // NOUVEAU : affecter un foyer à une université (Service 01 du PDF)
    @PutMapping("/affecterFoyerAUniversite")
    public Universite affecterFoyerAUniversite(
            @RequestParam Long idFoyer,
            @RequestParam String nomUniversite) {
        return universiteService.affecterFoyerAUniversite(idFoyer, nomUniversite);
    }

    @PutMapping("/desaffecterFoyerAUniversite/{idUniversite}")
    public Universite desaffecterFoyerAUniversite(@PathVariable long idUniversite) {
        return universiteService.desaffecterFoyerAUniversite(idUniversite);
    }
}