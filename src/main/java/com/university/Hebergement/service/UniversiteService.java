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
    public Universite updateUniversite(Universite universite) {
        // 1. Vérifier que l'id est fourni
        if (universite.getIdUniversite() == null) {
            throw new RuntimeException("L'idUniversite est obligatoire pour la modification");
        }

        // 2. Vérifier que l'université existe en base
        if (!universiteRepository.existsById(universite.getIdUniversite())) {
            throw new RuntimeException("Université introuvable avec l'id : " + universite.getIdUniversite());
        }

        // 3. OK, on peut modifier
        return universiteRepository.save(universite);
    }

    @Override
    public void addUniversite(Universite universite) {
        universiteRepository.save(universite);
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