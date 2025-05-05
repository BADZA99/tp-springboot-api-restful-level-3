package com.example.api_project.models;

import jakarta.persistence.*;

@Entity
@Table(name = "commande_produit")
public class CommandeProduit {
    @EmbeddedId
    private CommandeProduitId id;

    @MapsId("commandeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;

    @MapsId("produitId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produit_id", nullable = false)
    private com.example.api_project.models.Produit produit;

    @Column(name = "quantite", nullable = false)
    private int quantite;

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

    public com.example.api_project.models.Produit getProduit() {
        return produit;
    }

    public void setProduit(com.example.api_project.models.Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

}