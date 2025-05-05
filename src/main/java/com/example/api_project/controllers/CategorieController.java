package com.example.api_project.controllers;

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

@RestController
@RequestMapping("/api/categories")
public class CategorieController {

    @Autowired
    private CategorieRepository categorieRepository;

    @GetMapping
    public List<EntityModel<Categorie>> getAllCategories() {
        try {
            List<Categorie> categories = categorieRepository.findAll();
            return categories.stream().map(categorie -> {
                EntityModel<Categorie> model = EntityModel.of(categorie);
                model.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(CategorieController.class).getCategorieById(categorie.getId()))
                        .withSelfRel());
                return model;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des catégories", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Categorie>> getCategorieById(@PathVariable Long id) {
        try {
            Optional<Categorie> categorie = categorieRepository.findById(id);
            if (categorie.isPresent()) {
                EntityModel<Categorie> model = EntityModel.of(categorie.get());
                model.add(WebMvcLinkBuilder
                        .linkTo(WebMvcLinkBuilder.methodOn(CategorieController.class).getAllCategories())
                        .withRel("all-categories"));
                return ResponseEntity.ok(model);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de la catégorie", e);
        }
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