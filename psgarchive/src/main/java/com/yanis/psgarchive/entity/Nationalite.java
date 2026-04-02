package com.yanis.psgarchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "nationalite")
@Getter @Setter
public class Nationalite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nationalite") // Le nom dans MySQL
    private Long idNationalite;      // Le nom que Spring va chercher

    @Column(name = "libelle_nationalite")
    private String libelleNationalite;
}