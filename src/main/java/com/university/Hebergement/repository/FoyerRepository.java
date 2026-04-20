package com.university.Hebergement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.university.Hebergement.entities.Foyer;

public interface FoyerRepository extends JpaRepository<Foyer, Long> {
}