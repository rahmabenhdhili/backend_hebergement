package com.university.Hebergement.service;

import com.university.Hebergement.entities.Chambre;
import com.university.Hebergement.entities.TypeChambre;
import com.university.Hebergement.exception.ResourceNotFoundException;
import com.university.Hebergement.entities.Foyer;
import com.university.Hebergement.repository.FoyerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoyerService {

    private final FoyerRepository foyerRepository;

    public FoyerService(FoyerRepository foyerRepository) {
        this.foyerRepository = foyerRepository;
    }

    public Foyer addFoyer(Foyer foyer) {
        if (foyer.getCapaciteFoyer() == null || foyer.getCapaciteFoyer() <= 0)
            throw new RuntimeException("La capacité doit être supérieure à 0.");
        if (foyer.getCapaciteFoyer() > 1000)
            throw new RuntimeException("La capacité ne peut pas dépasser 1000.");
        foyerRepository.findByNomFoyer(foyer.getNomFoyer()).ifPresent(e -> {
            throw new RuntimeException("Un foyer avec le nom '" + foyer.getNomFoyer() + "' existe déjà.");
        });
        return foyerRepository.save(foyer);
    }

    public Foyer updateFoyer(Foyer foyer) {
        if (!foyerRepository.existsById(foyer.getIdFoyer()))
            throw new ResourceNotFoundException("Foyer introuvable avec l'id : " + foyer.getIdFoyer());
        if (foyer.getCapaciteFoyer() == null || foyer.getCapaciteFoyer() <= 0)
            throw new RuntimeException("La capacité doit être supérieure à 0.");
        if (foyer.getCapaciteFoyer() > 1000)
            throw new RuntimeException("La capacité ne peut pas dépasser 1000.");
        foyerRepository.findByNomFoyer(foyer.getNomFoyer()).ifPresent(existing -> {
            if (!existing.getIdFoyer().equals(foyer.getIdFoyer()))
                throw new RuntimeException("Un foyer avec le nom '" + foyer.getNomFoyer() + "' existe déjà.");
        });
        return foyerRepository.save(foyer);
    }

    public void deleteFoyer(Long id) {
        if (!foyerRepository.existsById(id))
            throw new ResourceNotFoundException("Foyer introuvable avec l'id : " + id);
        foyerRepository.deleteById(id);
    }

    public Foyer getFoyerById(Long id) {
        return foyerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Foyer introuvable avec l'id : " + id));
    }

    public List<Foyer> getAllFoyers() {
        return foyerRepository.findAll();
    }

    public List<Foyer> searchFoyers(String keyword) {
        return foyerRepository.findByNomFoyerContainingIgnoreCase(keyword);
    }

    public List<Chambre> getChambresNonReserveParNomFoyerEtTypeChambre(String nomFoyer, TypeChambre type) {
        return foyerRepository.getChambresNonReserveParNomFoyerEtTypeChambre(nomFoyer, type);
    }
}
