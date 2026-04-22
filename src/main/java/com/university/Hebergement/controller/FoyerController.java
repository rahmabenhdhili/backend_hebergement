package com.university.Hebergement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.university.Hebergement.entities.Foyer;
import com.university.Hebergement.service.FoyerService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/foyer")
public class FoyerController {

    private final FoyerService foyerService;

    public FoyerController(FoyerService foyerService) {
        this.foyerService = foyerService;
    }

    // CREATE — returns 201 + body, 409 if duplicate
    @PostMapping("/add")
    public ResponseEntity<?> addFoyer(@RequestBody Foyer foyer) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(foyerService.addFoyer(foyer));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // UPDATE — returns 409 if duplicate name
    @PutMapping("/update")
    public ResponseEntity<?> updateFoyer(@RequestBody Foyer foyer) {
        try {
            return ResponseEntity.ok(foyerService.updateFoyer(foyer));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // DELETE — returns 204
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoyer(@PathVariable Long id) {
        foyerService.deleteFoyer(id);
        return ResponseEntity.noContent().build();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Foyer> getById(@PathVariable Long id) {
        return ResponseEntity.ok(foyerService.getFoyerById(id));
    }

    // GET ALL
    @GetMapping("/all")
    public List<Foyer> getAll() {
        return foyerService.getAllFoyers();
    }

    // SEARCH — GET /foyer/search?keyword=xxx
    @GetMapping("/search")
    public List<Foyer> search(@RequestParam String keyword) {
        return foyerService.searchFoyers(keyword);
    }
}
