package com.yanis.psgarchive.repository;

import com.yanis.psgarchive.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByPseudoAndMotDePasse(String pseudo, String motDePasse);
}