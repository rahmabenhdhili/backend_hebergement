package com.university.Hebergement.service;

import com.university.Hebergement.IService.IEtudiantService;
import com.university.Hebergement.entities.Etudiant;
import com.university.Hebergement.entities.Universite;
import com.university.Hebergement.repository.EtudiantRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EtudiantService implements IEtudiantService {
    @Autowired
    EtudiantRepository etudiantRepository;
    @Autowired
    private UniversiteService universiteService;

    @Override
    public List<Etudiant> getAll() {
        return (List<Etudiant>) etudiantRepository.findAll();
    }

    @Override
    public Etudiant getEtudiantByID(Long id) {
        return etudiantRepository.findById(id).orElseThrow(() -> new RuntimeException("Étudiant introuvable: " + id));
    }

    @Override
    public Etudiant updateEtudiant(Etudiant etudiant) {
        Etudiant existing = etudiantRepository.findById((etudiant.getCin())).orElseThrow(() -> new RuntimeException("Etudiant not found"));

        existing.setNom(etudiant.getNom());
        existing.setPrenom(etudiant.getPrenom());
        existing.setDateNaissance(etudiant.getDateNaissance());

        existing.setUniversite(etudiant.getUniversite());
        return etudiantRepository.save(etudiant);
    }

    @Override
    public Etudiant addEtudiant(Etudiant etudiant) {

        if (etudiant.getCin() == null) {
            throw new RuntimeException("CIN obligatoire");
        }

        if (etudiantRepository.existsById(etudiant.getCin())) {
            throw new RuntimeException("CIN déjà existant");
        }

        // IMPORTANT: attach managed Universite entity
        if (etudiant.getUniversite() != null &&
                etudiant.getUniversite().getIdUniversite() != null) {

            Universite u = universiteService.getUniversiteByID(
                    etudiant.getUniversite().getIdUniversite());

            etudiant.setUniversite(u);
        }

        return etudiantRepository.save(etudiant);
    }

    @Override
    public boolean existsById(Long id) {
        return etudiantRepository.existsById(id);
    }

    @Override
    public void deleteEtudiant(Long cin) {
        etudiantRepository.deleteById(cin);
    }
}
