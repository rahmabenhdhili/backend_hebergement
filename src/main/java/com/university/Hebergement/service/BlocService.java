package com.university.Hebergement.service;

import com.university.Hebergement.entities.Bloc;
import com.university.Hebergement.entities.Chambre;
import com.university.Hebergement.exception.ResourceNotFoundException;
import com.university.Hebergement.repository.BlocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BlocService implements IBlocService {

    private final BlocRepository blocRepository;

    @Autowired
    public BlocService(BlocRepository blocRepository) {
        this.blocRepository = blocRepository;
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
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Bloc non trouvé avec l'ID : " + id
                ));
    }

    @Override
    public Bloc updateBloc(Long id, Bloc blocDetails) {
        Bloc existingBloc = getBlocById(id);

        // ✅ Uniquement les champs qui existent dans ton entité Bloc
        existingBloc.setNomBloc(blocDetails.getNomBloc());
        existingBloc.setFoyer(blocDetails.getFoyer());

        // Mise à jour des chambres si fournies
        if (blocDetails.getChambres() != null) {
            existingBloc.getChambres().clear();
            for (Chambre chambre : blocDetails.getChambres()) {
                chambre.setBloc(existingBloc);
                existingBloc.getChambres().add(chambre);
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