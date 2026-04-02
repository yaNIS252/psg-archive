package com.yanis.psgarchive.view;

import com.yanis.psgarchive.entity.Utilisateur;
import com.yanis.psgarchive.repository.JoueurRepository;
import com.yanis.psgarchive.repository.NationaliteRepository;
import com.yanis.psgarchive.repository.UtilisateurRepository;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class LoginFrame extends JFrame {

    private final UtilisateurRepository utilisateurRepository;
    private final JoueurRepository joueurRepository;
    private final NationaliteRepository nationaliteRepository;

    private JTextField txtPseudo;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginFrame(UtilisateurRepository uRepo, JoueurRepository jRepo, NationaliteRepository nRepo) {
        this.utilisateurRepository = uRepo;
        this.joueurRepository = jRepo;
        this.nationaliteRepository = nRepo;

        setTitle("Connexion - PSG Archive");
        setSize(400, 280); // Légèrement agrandi pour le confort
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtPseudo = new JTextField();
        txtPassword = new JPasswordField();
        btnLogin = new JButton("Se connecter");
        
        // Style du bouton
        btnLogin.setBackground(new Color(0, 65, 125));
        btnLogin.setForeground(Color.WHITE);

        mainPanel.add(new JLabel("Pseudo :"));
        mainPanel.add(txtPseudo);
        mainPanel.add(new JLabel("Mot de passe :"));
        mainPanel.add(txtPassword);
        mainPanel.add(new JPanel()); // Espace vide pour décaler le bouton
        mainPanel.add(btnLogin);

        add(mainPanel);

        // Action
        btnLogin.addActionListener(e -> tenterConnexion());
        
        // Permet de valider avec la touche "Entrée"
        getRootPane().setDefaultButton(btnLogin);
    }

    private void tenterConnexion() {
        String pseudo = txtPseudo.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (pseudo.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            return;
        }

        Optional<Utilisateur> userOpt = utilisateurRepository.findByPseudoAndMotDePasse(pseudo, password);
        
        if (userOpt.isPresent()) {
            Utilisateur user = userOpt.get();
            
            // On ferme la fenêtre de login
            this.dispose(); 
            
            // On lance la MainFrame avec les 4 arguments nécessaires
            SwingUtilities.invokeLater(() -> {
                MainFrame mainFrame = new MainFrame(
                    joueurRepository, 
                    nationaliteRepository, 
                    utilisateurRepository, // L'argument manquant était ici
                    user
                );
                mainFrame.setVisible(true);
            });
            
        } else {
            JOptionPane.showMessageDialog(this, "Identifiants incorrects.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}