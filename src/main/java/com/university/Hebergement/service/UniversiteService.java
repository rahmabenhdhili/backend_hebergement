package com.university.Hebergement.service;

import com.university.Hebergement.IService.IUniversiteService;
import com.university.Hebergement.entities.Foyer;
import com.university.Hebergement.entities.Universite;
import com.university.Hebergement.repository.FoyerRepository;
import com.university.Hebergement.repository.UniversiteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UniversiteService implements IUniversiteService {

    private final UniversiteRepository universiteRepository;
    private final FoyerRepository foyerRepository;  // ← NOUVEAU

    @Override
    public List<Universite> getAll() {
        return universiteRepository.findAll();
    }

    @Override
    public Universite getUniversiteByID(Long id) {
        return universiteRepository.findById(id).orElse(null);
    }

    @Override
    public void addUniversite(Universite universite) {
        // Vérifier qu'aucune université n'existe déjà avec ce nom
        if (universiteRepository.findByNomUniversite(universite.getNomUniversite()).isPresent()) {
            throw new RuntimeException("Une université avec le nom '"
                    + universite.getNomUniversite() + "' existe déjà.");
        }
        universiteRepository.save(universite);
    }

    @Override
    public Universite updateUniversite(Universite universite) {
        if (universite.getIdUniversite() == null) {
            throw new RuntimeException("L'idUniversite est obligatoire pour la modification");
        }

        if (!universiteRepository.existsById(universite.getIdUniversite())) {
            throw new RuntimeException("Université introuvable avec l'id : " + universite.getIdUniversite());
        }

        // Vérifier qu'un AUTRE enregistrement n'a pas déjà ce nom
        universiteRepository.findByNomUniversite(universite.getNomUniversite())
                .ifPresent(existing -> {
                    if (!existing.getIdUniversite().equals(universite.getIdUniversite())) {
                        throw new RuntimeException("Une université avec le nom '"
                                + universite.getNomUniversite() + "' existe déjà.");
                    }
                });

        return universiteRepository.save(universite);
    }

    @Override
    public void deleteUniversite(Long id) {
        universiteRepository.deleteById(id);
    }

    // NOUVEAU : affecte un foyer existant à une université (signature du PDF)
    @Override
    public Universite affecterFoyerAUniversite(Long idFoyer, String nomUniversite) {
        // 1. Trouver l'université par son nom
        Universite universite = universiteRepository.findAll().stream()
                .filter(u -> u.getNomUniversite().equals(nomUniversite))
                .findFirst()
                .orElse(null);

        if (universite == null) return null;

        // 2. Trouver le foyer par son id
        Foyer foyer = foyerRepository.findById(idFoyer).orElse(null);
        if (foyer == null) return null;

        // 3. Affecter et sauvegarder
        universite.setFoyer(foyer);
        return universiteRepository.save(universite);
    }
}