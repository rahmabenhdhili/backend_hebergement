package com.university.Hebergement.service;

import com.university.Hebergement.DTO.ReservationDTO;
import com.university.Hebergement.IService.IReservationService;
import com.university.Hebergement.entities.Chambre;
import com.university.Hebergement.entities.Etudiant;
import com.university.Hebergement.entities.Reservation;
import com.university.Hebergement.entities.TypeChambre;
import com.university.Hebergement.repository.ChambreRepository;
import com.university.Hebergement.repository.EtudiantRepository;
import com.university.Hebergement.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService implements IReservationService {
    @Autowired
    ChambreRepository chambreRepository;
    @Autowired
    EtudiantRepository etudiantRepository;

    @Autowired
    ReservationRepository reservationRepository;
    @Override
    public Reservation AddReservation(ReservationDTO request) {
        //first , we récupére la chambre
        Long num = request.getNumeroChambre();
        Chambre chambre = chambreRepository.findByNumeroChambre(request.getNumeroChambre()).orElseThrow(() -> new RuntimeException("chambre non trouvée"));

        //second , we récupére les étudiants
        List<Etudiant> etudiants = etudiantRepository.findByCinIn(request.getEtudiantsCINs());

        //capacité check
        int capaciteMax = this.getCapacite(chambre.getType());

        //voir si la chambre est pleine

        //stream -> transformer une liste en flux càd
        // passant de [Reservation1, Reservation2, ...]
        // à un pipepline Reservation1->Reservation2->Reservation3

        //mapToInt() -> transformer chaque élément en int

        int dejaReserve = chambre.getReservations()
                .stream()
                .mapToInt(r -> r.getEtudiants().size())
                .sum();

        if(dejaReserve + etudiants.size() > capaciteMax){
            throw new RuntimeException("Capacité de la chambre dépassée");
        }

        //générer l'id reservation
        Reservation reservation = new Reservation();
        reservation.setIdReservation(
                request.getAnneeUniversitaire() + "-" +
                        request.getNomBloc() + "-" +
                        request.getNumeroChambre() + "-" +
                        request.getEtudiantsCINs().get(0)
        );
        reservation.setDateReservation(LocalDate.now());
        reservation.setEstValide(true);
        reservation.setChambre(chambre);
        for (Etudiant e : etudiants) {
            reservation.getEtudiants().add(e);
        }

        return reservationRepository.save(reservation);
    }
    private int getCapacite(TypeChambre type){
        return switch(type){
            case SIMPLE -> 1;
            case DOUBLE -> 2;
            case TRIPLE -> 3;
        };
    }
}
