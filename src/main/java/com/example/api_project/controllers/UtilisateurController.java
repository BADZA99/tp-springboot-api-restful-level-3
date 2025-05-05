package com.example.api_project.controllers;

import com.example.api_project.models.Utilisateur;
import com.example.api_project.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping
    public List<EntityModel<Utilisateur>> getAllUtilisateurs() {
        return utilisateurRepository.findAll().stream().map(utilisateur -> {
            EntityModel<Utilisateur> model = EntityModel.of(utilisateur);
            model.add(WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(UtilisateurController.class).getUtilisateurById(utilisateur.getId()))
                    .withSelfRel());
            return model;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Utilisateur>> getUtilisateurById(@PathVariable Long id) {
        return utilisateurRepository.findById(id).map(utilisateur -> {
            EntityModel<Utilisateur> model = EntityModel.of(utilisateur);
            model.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(UtilisateurController.class).getAllUtilisateurs())
                    .withRel("all-utilisateurs"));
            return ResponseEntity.ok(model);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        try {
            utilisateurRepository.save(utilisateur);
            return ResponseEntity.ok("Utilisateur créé avec succès.");
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création de l'utilisateur", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUtilisateur(@PathVariable Long id,
            @RequestBody Utilisateur utilisateurDetails) {
        try {
            Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(id);
            if (utilisateurOptional.isPresent()) {
                Utilisateur utilisateur = utilisateurOptional.get();
                utilisateur.setLogin(utilisateurDetails.getLogin());
                utilisateur.setPassword(utilisateurDetails.getPassword());
                utilisateurRepository.save(utilisateur);
                return ResponseEntity.ok("Utilisateur mis à jour avec succès.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'utilisateur", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUtilisateur(@PathVariable Long id) {
        try {
            if (utilisateurRepository.existsById(id)) {
                utilisateurRepository.deleteById(id);
                return ResponseEntity.ok("Utilisateur supprimé avec succès.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression de l'utilisateur", e);
        }
    }
}