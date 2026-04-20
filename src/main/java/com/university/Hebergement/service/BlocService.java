package com.university.Hebergement.service;

import com.university.Hebergement.entities.Bloc;
import com.university.Hebergement.entities.Chambre;
import com.university.Hebergement.entities.Foyer;   // ← Ajoute cet import
import com.university.Hebergement.exception.ResourceNotFoundException;
import com.university.Hebergement.repository.BlocRepository;
import com.university.Hebergement.repository.FoyerRepository;   // ← Ajoute cet import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BlocService implements IBlocService {

    private final BlocRepository blocRepository;
    private final FoyerRepository foyerRepository;   // ← AJOUTÉ

    @Autowired
    public BlocService(BlocRepository blocRepository, FoyerRepository foyerRepository) {
        this.blocRepository = blocRepository;
        this.foyerRepository = foyerRepository;
    }

    @Override
    public Bloc addBloc(Bloc bloc) {
        if (bloc.getChambres() != null && !bloc.getChambres().isEmpty()) {
            for (Chambre chambre : bloc.getChambres()) {
                chambre.setBloc(bloc);
            }
        }
        return blocRepository.save(bloc);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bloc> getAllBlocs() {
        return blocRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Bloc getBlocById(Long id) {
        return blocRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bloc non trouvé avec l'ID : " + id));
    }

    @Override
    public Bloc updateBloc(Long id, Bloc blocDetails) {
        Bloc existingBloc = blocRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bloc non trouvé avec id: " + id));

        // Mise à jour des champs simples
        existingBloc.setNomBloc(blocDetails.getNomBloc());

        // Mise à jour du Foyer
        if (blocDetails.getFoyer() != null && blocDetails.getFoyer().getIdFoyer() != null) {
            Foyer newFoyer = foyerRepository.findById(blocDetails.getFoyer().getIdFoyer())
                    .orElseThrow(() -> new RuntimeException("Foyer non trouvé avec id: "
                            + blocDetails.getFoyer().getIdFoyer()));
            existingBloc.setFoyer(newFoyer);
        }

        // === GESTION DES CHAMBRES ===
        if (blocDetails.getChambres() != null) {
            existingBloc.getChambres().clear();   // orphanRemoval doit être activé

            for (Chambre ch : blocDetails.getChambres()) {
                Chambre newChambre = new Chambre();
                newChambre.setNumeroChambre(ch.getNumeroChambre());
                newChambre.setType(ch.getType());
                newChambre.setBloc(existingBloc);

                existingBloc.getChambres().add(newChambre);
            }
        }

        return blocRepository.save(existingBloc);
    }

    @Override
    public void deleteBloc(Long id) {
        Bloc bloc = getBlocById(id);
        blocRepository.delete(bloc);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bloc> getBlocsByFoyer(Long foyerId) {
        return blocRepository.findByFoyerIdFoyer(foyerId);
    }
}