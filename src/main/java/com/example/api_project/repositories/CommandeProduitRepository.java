package com.example.api_project.repositories;

import com.example.api_project.models.CommandeProduit;
import com.example.api_project.models.CommandeProduitId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeProduitRepository extends JpaRepository<CommandeProduit, CommandeProduitId> {
    List<CommandeProduit> findByCommandeId(Long commandeId);
}