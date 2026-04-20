package com.university.Hebergement.service;

import org.springframework.stereotype.Service;
import com.university.Hebergement.entities.Foyer;
import com.university.Hebergement.repository.FoyerRepository;

import java.util.List;

@Service
public class FoyerService {

    private final FoyerRepository foyerRepository;

    public FoyerService(FoyerRepository foyerRepository) {
        this.foyerRepository = foyerRepository;
    }

    // CREATE
    public Foyer addFoyer(Foyer foyer) {
        return foyerRepository.save(foyer);
    }

    // UPDATE
    public Foyer updateFoyer(Foyer foyer) {
        return foyerRepository.save(foyer);
    }

    // DELETE
    public String deleteFoyer(Long id) {
        foyerRepository.deleteById(id);
        return "Foyer deleted successfully";
    }

    // GET BY ID
    public Foyer getFoyerById(Long id) {
        return foyerRepository.findById(id).orElse(null);
    }

    // GET ALL
    public List<Foyer> getAllFoyers() {
        return foyerRepository.findAll();
    }
}