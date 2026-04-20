package com.university.Hebergement.IService;

import com.university.Hebergement.entities.Etudiant;

import java.util.List;

public interface IEtudiantService {
    List<Etudiant> getAll();
    Etudiant getEtudiantByID(Long id);
    Etudiant updateEtudiant(Etudiant etudiant);
    Etudiant addEtudiant(Etudiant etudiant);
    boolean existsById(Long id);
    void deleteEtudiant(Long id);


}
