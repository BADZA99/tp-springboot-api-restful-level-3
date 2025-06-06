package com.example.api_project.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "commande")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id")
    private com.example.api_project.models.Utilisateur utilisateur;

    @Column(name = "date_commande", nullable = false)
    private LocalDate dateCommande;

    @OneToMany(mappedBy = "commande", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CommandeProduit> commandeProduits;

    public Commande() {
        // Constructeur par défaut
    }

    public Commande(Long id, LocalDate dateCommande, com.example.api_project.models.Utilisateur utilisateur) {
        this.id = id;
        this.dateCommande = dateCommande;
        this.utilisateur = utilisateur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public com.example.api_project.models.Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(com.example.api_project.models.Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public List<CommandeProduit> getCommandeProduits() {
        return commandeProduits;
    }

    public void setCommandeProduits(List<CommandeProduit> commandeProduits) {
        this.commandeProduits = commandeProduits;
    }
}