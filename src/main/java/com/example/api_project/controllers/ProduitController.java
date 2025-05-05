package com.example.api_project.controllers;

import com.example.api_project.models.Produit;
import com.example.api_project.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping
    public List<EntityModel<Produit>> getAllProduits() {
        return produitRepository.findAll().stream().map(produit -> {
            EntityModel<Produit> model = EntityModel.of(produit);
            model.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(ProduitController.class).getProduitById(produit.getId()))
                    .withSelfRel());
            return model;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Produit>> getProduitById(@PathVariable Long id) {
        return produitRepository.findById(id).map(produit -> {
            EntityModel<Produit> model = EntityModel.of(produit);
            model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProduitController.class).getAllProduits())
                    .withRel("all-produits"));
            return ResponseEntity.ok(model);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createProduit(@RequestBody Produit produit) {
        try {
            produitRepository.save(produit);
            return ResponseEntity.ok("Produit créé avec succès.");
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création du produit", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduit(@PathVariable Long id, @RequestBody Produit produitDetails) {
        try {
            Optional<Produit> produitOptional = produitRepository.findById(id);
            if (produitOptional.isPresent()) {
                Produit produit = produitOptional.get();
                produit.setDesignation(produitDetails.getDesignation());
                produit.setPrix(produitDetails.getPrix());
                produit.setCategorie(produitDetails.getCategorie());
                produitRepository.save(produit);
                return ResponseEntity.ok("Produit mis à jour avec succès.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour du produit", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduit(@PathVariable Long id) {
        try {
            if (produitRepository.existsById(id)) {
                produitRepository.deleteById(id);
                return ResponseEntity.ok("Produit supprimé avec succès.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression du produit", e);
        }
    }
}