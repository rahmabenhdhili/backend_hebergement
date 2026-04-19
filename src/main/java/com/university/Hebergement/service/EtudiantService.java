package com.university.Hebergement.service;

import com.university.Hebergement.IService.IEtudiantService;
import com.university.Hebergement.entities.Etudiant;
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

    @Override
    public List<Etudiant> getAll() {
        return (List<Etudiant>) etudiantRepository.findAll();
    }

    @Override
    public Etudiant getEtudiantByID(Long id) {
        return etudiantRepository.findById(id).get();
    }

    @Override
    public Etudiant updateEtudiant(Etudiant etudiant) {
        Etudiant existing = etudiantRepository.findById((etudiant.getCin())).orElseThrow(() -> new RuntimeException("Etudiant not found"));

        existing.setNom(etudiant.getNom());
        existing.setPrenom(etudiant.getPrenom());
        existing.setDateNaissance(etudiant.getDateNaissance());

        existing.setEcole(etudiant.getEcole());
        return etudiantRepository.save(etudiant);
    }

    @Override
    public Etudiant addEtudiant(Etudiant etudiant){
        if (etudiant.getCin() == null) {
            throw new RuntimeException("CIN obligatoire");
        }

        boolean exists = etudiantRepository.existsById(etudiant.getCin());

        if (exists) {
            throw new RuntimeException("CIN déjà existant");
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
