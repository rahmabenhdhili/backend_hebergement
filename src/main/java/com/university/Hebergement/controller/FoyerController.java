package com.university.Hebergement.controller;

import org.springframework.web.bind.annotation.*;
import com.university.Hebergement.entities.Foyer;
import com.university.Hebergement.service.FoyerService;

import java.util.List;

@RestController
@RequestMapping("/foyer")
public class FoyerController {

    private final FoyerService foyerService;

    public FoyerController(FoyerService foyerService) {
        this.foyerService = foyerService;
    }

    // CREATE
    @PostMapping("/add")
    public Foyer addFoyer(@RequestBody Foyer foyer) {
        return foyerService.addFoyer(foyer);
    }

    // UPDATE
    @PutMapping("/update")
    public Foyer updateFoyer(@RequestBody Foyer foyer) {
        return foyerService.updateFoyer(foyer);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteFoyer(@PathVariable Long id) {
        return foyerService.deleteFoyer(id);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Foyer getById(@PathVariable Long id) {
        return foyerService.getFoyerById(id);
    }

    // GET ALL
    @GetMapping("/all")
    public List<Foyer> getAll() {
        return foyerService.getAllFoyers();
    }
}