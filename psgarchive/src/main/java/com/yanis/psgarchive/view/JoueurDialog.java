package com.yanis.psgarchive.view;

import com.yanis.psgarchive.entity.Joueur;
import com.yanis.psgarchive.entity.Nationalite;
import com.yanis.psgarchive.repository.NationaliteRepository;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JoueurDialog extends JDialog {
    private JTextField txtNom = new JTextField(20);
    private JTextField txtPrenom = new JTextField(20);
    private JTextField txtAnneeArrivee = new JTextField(10);
    private JTextField txtAnneeDepart = new JTextField(10);
    private JComboBox<Nationalite> comboNat = new JComboBox<>();
    
    private boolean validated = false;
    private Joueur joueur;

    public JoueurDialog(Frame parent, NationaliteRepository natRepo) {
        super(parent, "Gestion Joueur", true);
        setSize(450, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Panneau de formulaire
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Chargement des nationalités
        try {
            List<Nationalite> nats = natRepo.findAll();
            for (Nationalite n : nats) comboNat.addItem(n);
        } catch (Exception e) { e.printStackTrace(); }

        comboNat.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Nationalite n) setText(n.getLibelleNationalite());
                return this;
            }
        });

        formPanel.add(new JLabel("Nom :")); formPanel.add(txtNom);
        formPanel.add(new JLabel("Prénom :")); formPanel.add(txtPrenom);
        formPanel.add(new JLabel("Année Arrivée :")); formPanel.add(txtAnneeArrivee);
        formPanel.add(new JLabel("Année Départ (laisser vide si actuel) :")); formPanel.add(txtAnneeDepart);
        formPanel.add(new JLabel("Nationalité :")); formPanel.add(comboNat);

        // Panneau boutons
        JPanel btnPanel = new JPanel();
        JButton btnSave = new JButton("Enregistrer");
        JButton btnCancel = new JButton("Annuler");
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);

        add(formPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // Actions
        btnSave.addActionListener(e -> {
            if(validerSaisie()) {
                if (joueur == null) joueur = new Joueur(); // Si c'est un ajout
                
                joueur.setNom(txtNom.getText().trim());
                joueur.setPrenom(txtPrenom.getText().trim());
                joueur.setAnneeArrivee(Integer.parseInt(txtAnneeArrivee.getText().trim()));
                
                String dep = txtAnneeDepart.getText().trim();
                joueur.setAnneeDepart((dep.isEmpty() || dep.equalsIgnoreCase("Actuel")) ? null : Integer.parseInt(dep));
                joueur.setNationalite((Nationalite) comboNat.getSelectedItem());
                
                validated = true;
                dispose();
            }
        });

        btnCancel.addActionListener(e -> dispose());
    }

    // Méthode CRITIQUE pour la modification
    public void chargerJoueur(Joueur j) {
        this.joueur = j; // On garde l'ID existant
        txtNom.setText(j.getNom());
        txtPrenom.setText(j.getPrenom());
        txtAnneeArrivee.setText(String.valueOf(j.getAnneeArrivee()));
        txtAnneeDepart.setText(j.getAnneeDepart() != null ? String.valueOf(j.getAnneeDepart()) : "");
        
        for (int i = 0; i < comboNat.getItemCount(); i++) {
            Nationalite n = comboNat.getItemAt(i);
            if (j.getNationalite() != null && n.getIdNationalite().equals(j.getNationalite().getIdNationalite())) {
                comboNat.setSelectedIndex(i);
                break;
            }
        }
    }

    private boolean validerSaisie() {
        if (txtNom.getText().isEmpty() || txtAnneeArrivee.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nom et Année d'arrivée requis.");
            return false;
        }
        return true;
    }

    public boolean isValidated() { return validated; }
    public Joueur getJoueur() { return joueur; }
}