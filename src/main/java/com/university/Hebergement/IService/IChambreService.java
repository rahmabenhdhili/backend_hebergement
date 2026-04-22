package com.university.Hebergement.IService;

import com.university.Hebergement.entities.Chambre;

import java.util.List;

public interface IChambreService {
    List<Chambre> getChambresByBloc(String nomBloc);
}
