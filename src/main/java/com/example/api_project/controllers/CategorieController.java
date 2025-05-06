package com.example.api_project.controllers;

import com.example.api_project.dto.CategorieDTO;
import com.example.api_project.models.Categorie;
import com.example.api_project.repositories.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.hibernate.Hibernate;

@RestController
@RequestMapping("/api/categories")
public class CategorieController {

    @Autowired
    private CategorieRepository categorieRepository;

    @GetMapping
    public List<CategorieDTO> getAllCategories() {
        return categorieRepository.findAll().stream().map(categorie -> {
            List<String> produits = categorie.getProduits().stream()
                    .map(produit -> produit.getDesignation())
                    .collect(Collectors.toList());
            return new CategorieDTO(categorie.getId(), categorie.getNom(), produits);
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategorieDTO> getCategorieById(@PathVariable Long id) {
        return categorieRepository.findById(id).map(categorie -> {
            List<String> produits = categorie.getProduits().stream()
                    .map(produit -> produit.getDesignation())
                    .collect(Collectors.toList());
            CategorieDTO categorieDTO = new CategorieDTO(categorie.getId(), categorie.getNom(), produits);
            return ResponseEntity.ok(categorieDTO);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createCategorie(@RequestBody Categorie categorie) {
        try {
            categorieRepository.save(categorie);
            return ResponseEntity.ok("Catégorie créée avec succès.");
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création de la catégorie", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategorie(@PathVariable Long id, @RequestBody Categorie categorieDetails) {
        try {
            Optional<Categorie> categorieOptional = categorieRepository.findById(id);
            if (categorieOptional.isPresent()) {
                Categorie categorie = categorieOptional.get();
                categorie.setNom(categorieDetails.getNom());
                categorieRepository.save(categorie);
                return ResponseEntity.ok("Catégorie mise à jour avec succès.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la catégorie", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategorie(@PathVariable Long id) {
        try {
            if (categorieRepository.existsById(id)) {
                categorieRepository.deleteById(id);
                return ResponseEntity.ok("Catégorie supprimée avec succès.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression de la catégorie", e);
        }
    }
}