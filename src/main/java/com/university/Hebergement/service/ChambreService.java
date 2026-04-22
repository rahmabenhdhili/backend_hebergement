package com.university.Hebergement.service;

import com.university.Hebergement.IService.IChambreService;
import com.university.Hebergement.entities.Chambre;
import com.university.Hebergement.repository.ChambreRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
@AllArgsConstructor
public class ChambreService implements IChambreService {
    @Autowired
    ChambreRepository chambreRepository;

    @Override
    public List<Chambre> getChambresByBloc(String nomBloc) {
        List<Chambre> chambres = chambreRepository.findByBlocNomBloc(nomBloc);

        if (chambres.isEmpty()) {
            throw new RuntimeException("Aucune chambre trouvée pour le bloc : " + nomBloc);
        }

        return chambres;
    }
}
