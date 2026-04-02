# 🔴🔵 PSG Archive — Gestion & Data

Application Desktop (Client Lourd) développée en Java avec Spring Boot pour centraliser, administrer et consulter l'historique des joueurs du Paris Saint-Germain dans une interface moderne et sécurisée.

**Contexte métier** : La gestion de données historiques nécessite une structure fiable et une séparation stricte des droits d'accès. Ce projet simule un outil d'administration interne pour un club sportif professionnel.

---

## ✅ Fonctionnalités

| Fonctionnalité | Description |
|----------------|-------------|
| Authentification | Login sécurisé relié à une base de données MySQL |
| RBAC — ADMIN | Accès complet au CRUD (ajout, modification, suppression de joueurs) |
| RBAC — USER | Consultation et recherche uniquement |
| Moteur de recherche | Filtrage dynamique par nom, nationalité et périodes d'activité |
| Interface Dark Mode | Rendu moderne via la bibliothèque FlatLaf |

---

## 🛠️ Stack Technique

| Couche | Technologie |
|--------|-------------|
| Langage | Java 17+ |
| Framework | Spring Boot 3 (Core, Data JPA) |
| Base de données | MySQL |
| Interface graphique | Java Swing + FlatLaf |
| Build & dépendances | Maven |

---

## 🗂️ Structure du projet

```
src/main/java/com/yanis/psgarchive/
├── entity/       # Modèles de données (Joueur, Utilisateur, Nationalité)
├── repository/   # Interfaces Spring Data JPA (requêtes SQL automatisées)
└── view/         # Fenêtres et composants Swing (Interface Utilisateur)
```

---

## ⚙️ Architecture

```
Interface Swing (View)
    ↓
Services Spring Boot
    ↓
Repository — Spring Data JPA (abstraction SQL)
    ↓
Entity — Mapping Hibernate (objets Java ↔ tables MySQL)
    ↓
Base de données MySQL
```

1. **Couche Entity** — Mapping des tables SQL en objets Java via Hibernate
2. **Couche Repository** — Abstraction de la base de données avec Spring Data JPA
3. **Couche View** — Interface graphique réactive pilotée par les services Spring

---

## 🚀 Installation & Lancement

**1. Prérequis**

- Java JDK 17+
- Serveur MySQL avec une base `psg_archive`

**2. Build du projet**
```bash
./mvnw clean package
```

**3. Exécution**
```bash
java -jar target/psg-archive-0.0.1-SNAPSHOT.jar
```

---

## 🎓 Compétences validées (BTS SIO SLAM)

- Conception d'une architecture logicielle robuste et scalable
- Gestion du cycle de vie complet de la donnée (Saisie → Stockage → Affichage)
- Implémentation de règles de gestion métier et de sécurité (RBAC)

---

## 🔧 Améliorations possibles

- [ ] Ajouter une couche Service entre Repository et View
- [ ] Exporter les données en CSV ou PDF
- [ ] Ajouter des statistiques visuelles (charts par nationalité, période, etc.)
- [ ] Migrer vers une interface web avec Spring MVC ou React

---

## 👤 Auteur

**Yanis** — Étudiant BTS SIO SLAM, en recherche d'alternance Data/IA (Licence/Bachelor)

