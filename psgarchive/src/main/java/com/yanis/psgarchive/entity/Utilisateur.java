package com.yanis.psgarchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "utilisateur")
@Getter @Setter
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    private String pseudo;
    private String email;
    private String motDePasse;
    
    private String role; // "ADMIN" ou "USER"
}