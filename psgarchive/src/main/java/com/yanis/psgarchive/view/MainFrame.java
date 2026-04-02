package com.yanis.psgarchive.view;

import com.formdev.flatlaf.FlatClientProperties;
import com.yanis.psgarchive.entity.Joueur;
import com.yanis.psgarchive.entity.Nationalite;
import com.yanis.psgarchive.entity.Utilisateur;
import com.yanis.psgarchive.repository.JoueurRepository;
import com.yanis.psgarchive.repository.NationaliteRepository;
import com.yanis.psgarchive.repository.UtilisateurRepository; // Ajouté

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    private final JoueurRepository joueurRepository;
    private final NationaliteRepository nationaliteRepository;
    private final UtilisateurRepository utilisateurRepository; // Ajouté
    private final Utilisateur currentUser;

    private JTextField searchField, arrivalYearField, departureYearField;
    private JComboBox<Nationalite> comboNationalite;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private JPanel filterPanel;
    private JButton btnFilterArrow;

    public MainFrame(JoueurRepository joueurRepo, NationaliteRepository natRepo, UtilisateurRepository userRepo, Utilisateur user) {
        this.joueurRepository = joueurRepo;
        this.nationaliteRepository = natRepo;
        this.utilisateurRepository = userRepo; // Ajouté
        this.currentUser = user;

        setTitle("PSG Archive - " + currentUser.getPseudo());
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(15, 15));
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(root);

        // --- HAUT : RECHERCHE & SESSION ---
        JPanel topContainer = new JPanel(new BorderLayout());

        // 1. Barre de Session (Déconnexion)
        JPanel sessionBar = new JPanel(new BorderLayout());
        sessionBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel lblUser = new JLabel("Connecté en tant que : " + currentUser.getPseudo() + " (" + currentUser.getRole() + ")");
        lblUser.setFont(new Font("SansSerif", Font.ITALIC, 12));

        JButton btnLogout = new JButton("Déconnexion");
        btnLogout.putClientProperty("JButton.buttonType", "roundRect"); 
        btnLogout.setForeground(new Color(227, 6, 19)); // Rouge PSG

        sessionBar.add(lblUser, BorderLayout.WEST);
        sessionBar.add(btnLogout, BorderLayout.EAST);

        // 2. Barre de Recherche
        JPanel searchBar = new JPanel(new BorderLayout(10, 0));
        searchField = new JTextField();
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Rechercher un joueur (Nom ou Prénom)...");
        searchField.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
        
        JButton btnSearch = new JButton("Rechercher");
        btnSearch.setBackground(new Color(0, 65, 125)); // Bleu PSG
        btnSearch.setForeground(Color.WHITE);
        
        btnFilterArrow = new JButton("▼ Filtres");

        searchBar.add(searchField, BorderLayout.CENTER);
        JPanel searchButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        searchButtons.add(btnSearch);
        searchButtons.add(btnFilterArrow);
        searchBar.add(searchButtons, BorderLayout.EAST);
        
        topContainer.add(sessionBar, BorderLayout.NORTH);
        topContainer.add(searchBar, BorderLayout.CENTER);

        // --- FILTRES ---
        filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Critères avancés"));
        comboNationalite = new JComboBox<>();
        initialiserNationalites();
        
        arrivalYearField = new JTextField(6);
        arrivalYearField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Arrivée");
        departureYearField = new JTextField(6);
        departureYearField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Départ");
        
        filterPanel.add(new JLabel("Nationalité :"));
        filterPanel.add(comboNationalite);
        filterPanel.add(new JLabel("Années :"));
        filterPanel.add(arrivalYearField);
        filterPanel.add(departureYearField);
        filterPanel.setVisible(false);
        
        topContainer.add(filterPanel, BorderLayout.SOUTH);
        root.add(topContainer, BorderLayout.NORTH);

        // --- TABLEAU ---
        tableModel = new DefaultTableModel(new String[]{"ID", "Nom", "Prénom", "Arrivée", "Départ", "Nationalité"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        resultTable = new JTable(tableModel);
        resultTable.removeColumn(resultTable.getColumnModel().getColumn(0));
        resultTable.setRowHeight(35);
        
        JScrollPane scrollPane = new JScrollPane(resultTable);
        root.add(scrollPane, BorderLayout.CENTER);

        // --- ZONE ADMIN ---
        JPanel adminPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton btnAdd = new JButton("Nouveau Joueur");
        JButton btnEdit = new JButton("Modifier");
        JButton btnDelete = new JButton("Supprimer");
        btnDelete.setForeground(new Color(227, 6, 19)); 

        if ("ADMIN".equals(currentUser.getRole())) {
            adminPanel.add(btnAdd); 
            adminPanel.add(btnEdit); 
            adminPanel.add(btnDelete);
        }
        root.add(adminPanel, BorderLayout.SOUTH);

        // --- ACTIONS ---
        btnLogout.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, "Voulez-vous vous déconnecter ?", "Déconnexion", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                this.dispose();
                SwingUtilities.invokeLater(() -> {
                    new LoginFrame(utilisateurRepository, joueurRepository, nationaliteRepository).setVisible(true);
                });
            }
        });

        btnFilterArrow.addActionListener(e -> {
            filterPanel.setVisible(!filterPanel.isVisible());
            btnFilterArrow.setText(filterPanel.isVisible() ? "▲ Filtres" : "▼ Filtres");
            revalidate();
            repaint();
        });

        btnSearch.addActionListener(e -> chargerDonnees());

        btnAdd.addActionListener(e -> {
            JoueurDialog dialog = new JoueurDialog(this, nationaliteRepository);
            dialog.setVisible(true);
            if (dialog.isValidated()) {
                joueurRepository.save(dialog.getJoueur());
                chargerDonnees();
            }
        });

        btnEdit.addActionListener(e -> {
            int row = resultTable.getSelectedRow();
            if (row != -1) {
                Long id = (Long) tableModel.getValueAt(row, 0);
                joueurRepository.findById(id).ifPresent(j -> {
                    JoueurDialog dialog = new JoueurDialog(this, nationaliteRepository);
                    dialog.chargerJoueur(j);
                    dialog.setVisible(true);
                    if (dialog.isValidated()) {
                        joueurRepository.save(dialog.getJoueur());
                        chargerDonnees();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(this, "Sélectionnez un joueur.");
            }
        });

        btnDelete.addActionListener(e -> {
            int row = resultTable.getSelectedRow();
            if (row != -1) {
                Long id = (Long) tableModel.getValueAt(row, 0);
                if (JOptionPane.showConfirmDialog(this, "Supprimer ?", "Confirmation", JOptionPane.YES_OPTION) == JOptionPane.YES_OPTION) {
                    joueurRepository.deleteById(id);
                    chargerDonnees();
                }
            }
        });

        chargerDonnees();
    }

    private void initialiserNationalites() {
        try {
            List<Nationalite> nats = nationaliteRepository.findAll();
            Nationalite all = new Nationalite(); all.setLibelleNationalite("Toutes");
            comboNationalite.addItem(all);
            for (Nationalite n : nats) comboNationalite.addItem(n);
            comboNationalite.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Nationalite n) setText(n.getLibelleNationalite());
                    return this;
                }
            });
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void chargerDonnees() {
        tableModel.setRowCount(0);
        String saisie = searchField.getText().trim();
        String arrF = arrivalYearField.getText().trim();
        String depF = departureYearField.getText().trim();
        Nationalite nat = (Nationalite) comboNationalite.getSelectedItem();

        try {
            List<Joueur> joueurs;
            if (!saisie.isEmpty()) {
                joueurs = joueurRepository.findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(saisie, saisie);
            } else if (nat != null && !"Toutes".equals(nat.getLibelleNationalite())) {
                joueurs = joueurRepository.findByNationalite_IdNationalite(nat.getIdNationalite());
            } else {
                joueurs = joueurRepository.findAll();
            }

            for (Joueur j : joueurs) {
                String depStr = (j.getAnneeDepart() == null || j.getAnneeDepart() == 0) ? "Actuel" : String.valueOf(j.getAnneeDepart());
                if (!arrF.isEmpty() && !String.valueOf(j.getAnneeArrivee()).equals(arrF)) continue;
                if (!depF.isEmpty() && !depStr.equalsIgnoreCase(depF)) continue;

                tableModel.addRow(new Object[]{
                    j.getIdJoueur(), j.getNom(), j.getPrenom(), j.getAnneeArrivee(), depStr,
                    (j.getNationalite() != null ? j.getNationalite().getLibelleNationalite() : "N/C")
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}