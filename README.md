🔴🔵 PSG Archive — Gestion & Data
PSG Archive est une application Desktop (Client Lourd) développée en Java avec le framework Spring Boot. Elle permet de centraliser, d'administrer et de consulter l'historique des joueurs du Paris Saint-Germain dans une interface moderne et sécurisée.

🚀 Fonctionnalités
Authentification sécurisée : Système de login relié à une base de données MySQL.

Gestion des rôles (RBAC) : * ADMIN : Accès complet au CRUD (Ajout, Modification, Suppression de joueurs).

USER : Consultation et recherche uniquement.

Moteur de recherche : Filtrage dynamique par nom, nationalité et périodes d'activité au club.

Interface Moderne : Utilisation de la bibliothèque FlatLaf pour un rendu "Dark Mode" épuré.

🛠️ Stack Technique
Langage : Java 17+

Framework : Spring Boot 3 (Core, Data JPA)

Base de données : MySQL

Interface Graphique : Java Swing + FlatLaf

Gestionnaire de projet : Maven (Gestion des dépendances et build)

📂 Structure du Projet
Plaintext
src/main/java/com/yanis/psgarchive/
├── entity/       # Modèles de données (Joueur, Utilisateur, Nationalité)
├── repository/   # Interfaces Spring Data JPA pour les requêtes SQL
└── view/         # Fenêtres et composants Swing (UI)
⚙️ Installation et Lancement
Prérequis
Java JDK 17 ou supérieur installé.

Un serveur MySQL actif avec une base nommée psg_archive.

Build du projet
Pour générer le fichier exécutable (.jar), utilisez la commande Maven suivante à la racine du projet :

Bash
./mvnw clean package
Exécution
Une fois le build terminé, lancez l'application via le terminal ou en double-cliquant sur le fichier généré dans le dossier /target :

Bash
java -jar target/psg-archive-0.0.1-SNAPSHOT.jar
🎓 Objectifs Pédagogiques
Ce projet a été réalisé dans le cadre du BTS SIO (Option SLAM). Il démontre la capacité à :

Concevoir une architecture logicielle robuste et scalable.

Gérer le cycle de vie complet de la donnée (Saisie -> Stockage -> Affichage).

Implémenter des règles de gestion métier et de sécurité (Gestion des droits).

Développé par Yanis — Étudiant en BTS SIO SLAM, futur alternant en Licence IA & Data.

Captures d'écran

LOGIN

<img width="377" height="264" alt="image" src="https://github.com/user-attachments/assets/d3c87f75-6c94-4b10-8b97-b4d3fc9d02f8" />


ACCEUIL ADMIN

<img width="1181" height="787" alt="image" src="https://github.com/user-attachments/assets/3273d2aa-7bac-4cfe-9be3-1e7793ec79e7" />


ACCEUIL USER

<img width="1179" height="777" alt="image" src="https://github.com/user-attachments/assets/7cc911fd-0bb2-4808-8438-01b18b205753" />


MODIFIER JOUEUR

<img width="432" height="391" alt="image" src="https://github.com/user-attachments/assets/b7ea0049-74d5-4580-8ba2-0648006cd2ec" />


AJOUTER UN JOUEUR

<img width="432" height="389" alt="image" src="https://github.com/user-attachments/assets/df108db6-a5d2-4354-a469-a4e91225749a" />


RECHERCHE PAR FILTRE 

<img width="1167" height="135" alt="image" src="https://github.com/user-attachments/assets/5f278cea-ec41-46da-adc9-2f2ee9b1f883" />



