package com.university.Hebergement.service;

import com.university.Hebergement.entities.Bloc;
import java.util.List;

public interface IBlocService {
    Bloc addBloc(Bloc bloc);
    List<Bloc> getAllBlocs();
    Bloc getBlocById(Long id);
    Bloc updateBloc(Long id, Bloc bloc);
    void deleteBloc(Long id);
    List<Bloc> getBlocsByFoyer(Long foyerId);
}