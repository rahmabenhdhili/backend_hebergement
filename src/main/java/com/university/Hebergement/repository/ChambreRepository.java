package com.university.Hebergement.repository;

import com.university.Hebergement.DTO.ChambreDTO;
import com.university.Hebergement.entities.Bloc;
import com.university.Hebergement.entities.Chambre;
import com.university.Hebergement.entities.TypeChambre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChambreRepository extends JpaRepository<Chambre, Long> {
    Optional<Chambre> findByNumeroChambre(Long numeroChambre);
    List<Chambre> findByBlocNomBloc(String nomBloc);
}
