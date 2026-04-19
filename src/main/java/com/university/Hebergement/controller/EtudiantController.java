package com.university.Hebergement.controller;

import com.university.Hebergement.IService.IEtudiantService;
import com.university.Hebergement.entities.Etudiant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void createEtudiant(@RequestBody Etudiant etudiant){

         etudiantService.addEtudiant(etudiant);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        etudiantService.deleteEtudiant(id);
    }
}
