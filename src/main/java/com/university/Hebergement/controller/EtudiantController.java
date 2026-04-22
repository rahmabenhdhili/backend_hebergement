package com.university.Hebergement.controller;

import com.university.Hebergement.IService.IEtudiantService;
import com.university.Hebergement.entities.Etudiant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("etudiant")
public class EtudiantController {
    @Autowired
    IEtudiantService etudiantService;

    @GetMapping("/")
    public List<Etudiant> getAll(){
        return etudiantService.getAll();
    }
    @GetMapping("/{id}")
    public Etudiant GetOne(@PathVariable("id") Long id){
        return etudiantService.getEtudiantByID(id);
    }

    @PutMapping("/update")
    public Etudiant updateEtudiant(@RequestBody Etudiant etudiant){
        return etudiantService.updateEtudiant(etudiant);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEtudiant(@RequestBody Etudiant etudiant) {

        if (etudiantService.existsById(etudiant.getCin())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("CIN déjà existant");
        }

        Etudiant saved = etudiantService.addEtudiant(etudiant);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{cin}")
    public void delete(@PathVariable("cin") Long cin){
        etudiantService.deleteEtudiant(cin);
    }
}
