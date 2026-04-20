package com.university.Hebergement.IService;

import com.university.Hebergement.entities.Universite;
import java.util.List;

public interface IUniversiteService {
    List<Universite> getAll();
    Universite getUniversiteByID(Long id);
    Universite updateUniversite(Universite universite);
    void addUniversite(Universite universite);
    void deleteUniversite(Long id);
}