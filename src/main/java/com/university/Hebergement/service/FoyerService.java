package com.university.Hebergement.service;

import org.springframework.stereotype.Service;
import com.university.Hebergement.entities.Foyer;
import com.university.Hebergement.exception.ResourceNotFoundException;
import com.university.Hebergement.repository.FoyerRepository;

import java.util.List;

@Service
public class FoyerService {

    private final FoyerRepository foyerRepository;

    public FoyerService(FoyerRepository foyerRepository) {
        this.foyerRepository = foyerRepository;
    }

    // CREATE — rejects duplicate nomFoyer
    public Foyer addFoyer(Foyer foyer) {
        if (foyer.getCapaciteFoyer() == null || foyer.getCapaciteFoyer() <= 0) {
            throw new RuntimeException("La capacité doit être supérieure à 0.");
        }
        if (foyer.getCapaciteFoyer() > 1000) {
            throw new RuntimeException("La capacité ne peut pas dépasser 1000.");
        }
        foyerRepository.findByNomFoyer(foyer.getNomFoyer()).ifPresent(existing -> {
            throw new RuntimeException("Un foyer avec le nom '" + foyer.getNomFoyer() + "' existe déjà.");
        });
        return foyerRepository.save(foyer);
    }

    public Foyer updateFoyer(Foyer foyer) {
        if (!foyerRepository.existsById(foyer.getIdFoyer())) {
            throw new ResourceNotFoundException("Foyer introuvable avec l'id : " + foyer.getIdFoyer());
        }
        if (foyer.getCapaciteFoyer() == null || foyer.getCapaciteFoyer() <= 0) {
            throw new RuntimeException("La capacité doit être supérieure à 0.");
        }
        if (foyer.getCapaciteFoyer() > 1000) {
            throw new RuntimeException("La capacité ne peut pas dépasser 1000.");
        }
        foyerRepository.findByNomFoyer(foyer.getNomFoyer()).ifPresent(existing -> {
            if (!existing.getIdFoyer().equals(foyer.getIdFoyer())) {
                throw new RuntimeException("Un foyer avec le nom '" + foyer.getNomFoyer() + "' existe déjà.");
            }
        });
        return foyerRepository.save(foyer);
    }


    // DELETE — throws 404 if not found
    public void deleteFoyer(Long id) {
        if (!foyerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Foyer introuvable avec l'id : " + id);
        }
        foyerRepository.deleteById(id);
    }

    // GET BY ID — throws 404 instead of returning null
    public Foyer getFoyerById(Long id) {
        return foyerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Foyer introuvable avec l'id : " + id));
    }

    // GET ALL
    public List<Foyer> getAllFoyers() {
        return foyerRepository.findAll();
    }

    // SEARCH by name (case-insensitive, partial match)
    public List<Foyer> searchFoyers(String keyword) {
        return foyerRepository.findByNomFoyerContainingIgnoreCase(keyword);
    }
}
