package com.university.Hebergement.repository;

import com.university.Hebergement.entities.Chambre;
import com.university.Hebergement.entities.Foyer;
import com.university.Hebergement.entities.TypeChambre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FoyerRepository extends JpaRepository<Foyer, Long> {

    Optional<Foyer> findByNomFoyer(String nomFoyer);

    List<Foyer> findByNomFoyerContainingIgnoreCase(String keyword);

    @Query("SELECT c FROM Chambre c " +
            "JOIN c.bloc b " +
            "JOIN b.foyer f " +
            "WHERE f.nomFoyer = :nomFoyer " +
            "AND c.type = :type " +
            "AND NOT EXISTS (" +
            "SELECT r FROM Reservation r " +
            "WHERE r.chambre = c AND r.estValide = true" +
            ")")
    List<Chambre> getChambresNonReserveParNomFoyerEtTypeChambre(
            @Param("nomFoyer") String nomFoyer,
            @Param("type") TypeChambre type
    );
}
