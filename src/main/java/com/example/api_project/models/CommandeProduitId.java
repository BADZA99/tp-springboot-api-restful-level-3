package com.example.api_project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class CommandeProduitId implements java.io.Serializable {
    private static final long serialVersionUID = -6488782456460407609L;
    @Column(name = "commande_id", nullable = false)
    private Long commandeId;

    @Column(name = "produit_id", nullable = false)
    private Long produitId;

    // Constructeur par défaut requis par Hibernate
    public CommandeProduitId() {
    }

    public CommandeProduitId(Long commandeId, Long produitId) {
        this.commandeId = commandeId;
        this.produitId = produitId;
    }

    public Long getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Long commandeId) {
        this.commandeId = commandeId;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        CommandeProduitId entity = (CommandeProduitId) o;
        return Objects.equals(this.produitId, entity.produitId) &&
                Objects.equals(this.commandeId, entity.commandeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produitId, commandeId);
    }

}