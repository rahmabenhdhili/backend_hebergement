package com.university.Hebergement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.university.Hebergement.entities.Foyer;
import java.util.List;
import java.util.Optional;

public interface FoyerRepository extends JpaRepository<Foyer, Long> {
    Optional<Foyer> findByNomFoyer(String nomFoyer);
    List<Foyer> findByNomFoyerContainingIgnoreCase(String keyword);
}
