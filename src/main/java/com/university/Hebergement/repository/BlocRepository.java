package com.university.Hebergement.repository;

import com.university.Hebergement.entities.Bloc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlocRepository extends JpaRepository<Bloc, Long> {
    List<Bloc> findByFoyerIdFoyer(Long idFoyer);
    Bloc findByNomBloc(String nomBloc);
}