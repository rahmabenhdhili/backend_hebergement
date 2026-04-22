package com.university.Hebergement.repository;

import com.university.Hebergement.entities.Etudiant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtudiantRepository extends CrudRepository<Etudiant, Long> {
    List<Etudiant> findByCinIn(List<String> cins);
}
