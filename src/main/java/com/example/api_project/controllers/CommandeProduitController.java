package com.example.api_project.controllers;

import com.example.api_project.models.CommandeProduit;
import com.example.api_project.models.CommandeProduitId;
import com.example.api_project.repositories.CommandeProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/commande-produits")
public class CommandeProduitController {

    @Autowired
    private CommandeProduitRepository commandeProduitRepository;

    @GetMapping
    public List<EntityModel<CommandeProduit>> getAllCommandeProduits() {
        return commandeProduitRepository.findAll().stream().map(commandeProduit -> {
            EntityModel<CommandeProduit> model = EntityModel.of(commandeProduit);
            model.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(CommandeProduitController.class).getCommandeProduitById(
                            commandeProduit.getId().getCommandeId(), commandeProduit.getId().getProduitId()))
                    .withSelfRel());
            return model;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{commandeId}/{produitId}")
    public ResponseEntity<EntityModel<CommandeProduit>> getCommandeProduitById(@PathVariable Long commandeId,
            @PathVariable Long produitId) {
        CommandeProduitId id = new CommandeProduitId(commandeId, produitId);
        return commandeProduitRepository.findById(id).map(commandeProduit -> {
            EntityModel<CommandeProduit> model = EntityModel.of(commandeProduit);
            model.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(CommandeProduitController.class).getAllCommandeProduits())
                    .withRel("all-commande-produits"));
            return ResponseEntity.ok(model);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createCommandeProduit(@RequestBody CommandeProduit commandeProduit) {
        try {
            // Vérification et initialisation des relations
            if (commandeProduit.getCommande() == null || commandeProduit.getProduit() == null) {
                return ResponseEntity.badRequest().body("Commande et Produit doivent être spécifiés.");
            }

            // Génération de l'ID composite
            CommandeProduitId id = new CommandeProduitId(
                    commandeProduit.getCommande().getId(),
                    commandeProduit.getProduit().getId());
            commandeProduit.setId(id);

            commandeProduitRepository.save(commandeProduit);
            return ResponseEntity.ok("Produit de commande créé avec succès.");
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création du produit de commande", e);
        }
    }

    @PutMapping("/{commandeId}/{produitId}")
    public ResponseEntity<String> updateCommandeProduit(@PathVariable Long commandeId, @PathVariable Long produitId,
            @RequestBody CommandeProduit commandeProduitDetails) {
        try {
            CommandeProduitId id = new CommandeProduitId(commandeId, produitId);
            Optional<CommandeProduit> commandeProduitOptional = commandeProduitRepository.findById(id);
            if (commandeProduitOptional.isPresent()) {
                CommandeProduit commandeProduit = commandeProduitOptional.get();
                commandeProduit.setQuantite(commandeProduitDetails.getQuantite());
                commandeProduitRepository.save(commandeProduit);
                return ResponseEntity.ok("Produit de commande mis à jour avec succès.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour du produit de commande", e);
        }
    }

    @DeleteMapping("/{commandeId}/{produitId}")
    public ResponseEntity<String> deleteCommandeProduit(@PathVariable Long commandeId, @PathVariable Long produitId) {
        try {
            CommandeProduitId id = new CommandeProduitId(commandeId, produitId);
            if (commandeProduitRepository.existsById(id)) {
                commandeProduitRepository.deleteById(id);
                return ResponseEntity.ok("Produit de commande supprimé avec succès.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression du produit de commande", e);
        }
    }
}