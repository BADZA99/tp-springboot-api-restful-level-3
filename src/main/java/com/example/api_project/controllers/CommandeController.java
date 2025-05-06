package com.example.api_project.controllers;

import com.example.api_project.dto.CommandeDTO;
import com.example.api_project.models.Commande;
import com.example.api_project.repositories.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    @Autowired
    private CommandeRepository commandeRepository;

    @GetMapping
    public List<CommandeDTO> getAllCommandes() {
        return commandeRepository.findAll().stream().map(commande -> {
            String utilisateurLogin = commande.getUtilisateur() != null ? commande.getUtilisateur().getLogin() : null;
            return new CommandeDTO(commande.getId(), commande.getDateCommande(), utilisateurLogin);
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandeDTO> getCommandeById(@PathVariable Long id) {
        return commandeRepository.findById(id).map(commande -> {
            String utilisateurLogin = commande.getUtilisateur() != null ? commande.getUtilisateur().getLogin() : null;
            CommandeDTO commandeDTO = new CommandeDTO(commande.getId(), commande.getDateCommande(), utilisateurLogin);
            return ResponseEntity.ok(commandeDTO);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createCommande(@RequestBody Commande commande) {
        try {
            commandeRepository.save(commande);
            return ResponseEntity.ok("Commande créée avec succès.");
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création de la commande", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCommande(@PathVariable Long id, @RequestBody Commande commandeDetails) {
        try {
            Optional<Commande> commandeOptional = commandeRepository.findById(id);
            if (commandeOptional.isPresent()) {
                Commande commande = commandeOptional.get();
                commande.setDateCommande(commandeDetails.getDateCommande());
                commande.setUtilisateur(commandeDetails.getUtilisateur());
                commandeRepository.save(commande);
                return ResponseEntity.ok("Commande mise à jour avec succès.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la commande", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCommande(@PathVariable Long id) {
        try {
            if (commandeRepository.existsById(id)) {
                commandeRepository.deleteById(id);
                return ResponseEntity.ok("Commande supprimée avec succès.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression de la commande", e);
        }
    }
}