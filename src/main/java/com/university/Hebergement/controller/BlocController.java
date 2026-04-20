package com.university.Hebergement.controller;

import com.university.Hebergement.entities.Bloc;
import com.university.Hebergement.service.IBlocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blocs")
@CrossOrigin(origins = "*")
public class BlocController {

    private final IBlocService blocService;

    @Autowired
    public BlocController(IBlocService blocService) {
        this.blocService = blocService;
    }

    @PostMapping
    public ResponseEntity<Bloc> addBloc(@RequestBody Bloc bloc) {
        return new ResponseEntity<>(blocService.addBloc(bloc), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Bloc>> getAllBlocs() {
        return ResponseEntity.ok(blocService.getAllBlocs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bloc> getBlocById(@PathVariable Long id) {
        return ResponseEntity.ok(blocService.getBlocById(id));
    }

    @GetMapping("/foyer/{foyerId}")
    public ResponseEntity<List<Bloc>> getBlocsByFoyer(@PathVariable Long foyerId) {
        return ResponseEntity.ok(blocService.getBlocsByFoyer(foyerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bloc> updateBloc(@PathVariable Long id, @RequestBody Bloc bloc) {
        return ResponseEntity.ok(blocService.updateBloc(id, bloc));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBloc(@PathVariable Long id) {
        blocService.deleteBloc(id);
        return ResponseEntity.noContent().build();
    }
}