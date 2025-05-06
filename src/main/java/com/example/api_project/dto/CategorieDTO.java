package com.example.api_project.dto;

import java.util.List;

public class CategorieDTO {
    private Long id;
    private String nom;
    private List<String> produits; // Liste des noms des produits

    public CategorieDTO() {
    }

    public CategorieDTO(Long id, String nom, List<String> produits) {
        this.id = id;
        this.nom = nom;
        this.produits = produits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<String> getProduits() {
        return produits;
    }

    public void setProduits(List<String> produits) {
        this.produits = produits;
    }
}