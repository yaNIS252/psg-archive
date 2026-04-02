package com.yanis.psgarchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "joueur")
@Getter @Setter
public class Joueur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_joueur")
    private Long idJoueur; // camelCase uniquement

    private String nom;
    private String prenom;

    @Column(name = "annee_arrivee")
    private int anneeArrivee; // minuscule au début, pas d'underscore

    @Column(name = "annee_depart")
    private Integer anneeDepart; 

    @ManyToOne
    @JoinColumn(name = "id_nationalite")
    private Nationalite nationalite;

    @ManyToOne
    @JoinColumn(name = "id_club_destination")
    private Club clubDestination;

    @ManyToOne
    @JoinColumn(name = "id_createur")
    private Utilisateur createur;
}