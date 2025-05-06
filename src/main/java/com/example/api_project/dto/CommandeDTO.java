package com.example.api_project.dto;

import java.time.LocalDate;

public class CommandeDTO {
    private Long id;
    private LocalDate dateCommande;
    private String utilisateurLogin; // Nom d'utilisateur associé à la commande

    public CommandeDTO() {
    }

    public CommandeDTO(Long id, LocalDate dateCommande, String utilisateurLogin) {
        this.id = id;
        this.dateCommande = dateCommande;
        this.utilisateurLogin = utilisateurLogin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getUtilisateurLogin() {
        return utilisateurLogin;
    }

    public void setUtilisateurLogin(String utilisateurLogin) {
        this.utilisateurLogin = utilisateurLogin;
    }
}