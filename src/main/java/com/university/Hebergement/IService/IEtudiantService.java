package com.university.Hebergement.IService;

import com.university.Hebergement.entities.Etudiant;

import java.util.List;

public interface IEtudiantService {
    List<Etudiant> getAll();
    Etudiant getEtudiantByID(Long id);
    Etudiant updateEtudiant(Etudiant etudiant);
    void addEtudiant(Etudiant etudiant);
    void deleteEtudiant(Long id);


}
