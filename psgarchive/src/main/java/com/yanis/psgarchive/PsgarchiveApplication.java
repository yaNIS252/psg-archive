package com.yanis.psgarchive;

import com.formdev.flatlaf.FlatDarkLaf;
import com.yanis.psgarchive.repository.JoueurRepository;
import com.yanis.psgarchive.repository.NationaliteRepository;
import com.yanis.psgarchive.repository.UtilisateurRepository;
import com.yanis.psgarchive.view.LoginFrame;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder; // L'IMPORT MANQUANT
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class PsgarchiveApplication {

   public static void main(String[] args) {
    @SuppressWarnings("unused") // Dit à l'IDE d'ignorer l'avertissement
    ConfigurableApplicationContext context = new SpringApplicationBuilder(PsgarchiveApplication.class)
            .headless(false)
            .run(args);
}

    @Bean
    public CommandLineRunner run(JoueurRepository jRepo, NationaliteRepository nRepo, UtilisateurRepository uRepo) {
        return args -> {
            // 1. Initialisation du thème visuel moderne
            try {
                UIManager.setLookAndFeel(new FlatDarkLaf());
                
                // Personnalisation sobre PSG
                UIManager.put("Button.arc", 10);
                UIManager.put("Component.arc", 10);
                UIManager.put("ProgressBar.accentColor", new Color(0, 65, 125));
            } catch (Exception ex) {
                System.err.println("Erreur FlatLaf : " + ex.getMessage());
            }

            // 2. Lancement de l'interface sur le thread dédié à Swing
            SwingUtilities.invokeLater(() -> {
                LoginFrame login = new LoginFrame(uRepo, jRepo, nRepo);
                login.setVisible(true);
            });
        };
    }
}