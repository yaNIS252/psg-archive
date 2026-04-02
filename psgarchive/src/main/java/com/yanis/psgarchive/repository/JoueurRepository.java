package com.yanis.psgarchive.repository;

import com.yanis.psgarchive.entity.Joueur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JoueurRepository extends JpaRepository<Joueur, Long> {

    // Recherche intelligente : regarde dans le nom OU le prénom
    List<Joueur> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String nom, String prenom);

    // Filtre par nationalité
    List<Joueur> findByNationalite_IdNationalite(Long idNationalite);

    // Filtres pour les années (optionnels si tu veux filtrer via SQL directement)
    List<Joueur> findByAnneeArrivee(int annee);
    List<Joueur> findByAnneeDepart(Integer annee);
    List<Joueur> findByAnneeDepartIsNull();
}