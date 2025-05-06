package com.example.api_project.models;

import jakarta.persistence.*;

@Entity
@Table(name = "commande_produit")
public class CommandeProduit {
    @EmbeddedId
    private CommandeProduitId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("commandeId")
    @JoinColumn(name = "commande_id")
    private Commande commande;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("produitId")
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @Column(name = "quantite", nullable = false)
    private Integer quantite;

    public CommandeProduit() {
        // Constructeur par défaut
    }

    public CommandeProduit(CommandeProduitId id, Commande commande, Produit produit) {
        this.id = id;
        this.commande = commande;
        this.produit = produit;
    }

    public CommandeProduitId getId() {
        return id;
    }

    public void setId(CommandeProduitId id) {
        this.id = id;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

}