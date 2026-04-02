# 🏦 Bank Customer Churn Prediction

Modèle de machine learning pour prédire si un client bancaire va quitter la banque (churn), basé sur ses caractéristiques démographiques et comportementales.

**Contexte métier** : Le churn client coûte en moyenne 5× plus cher qu'une rétention. Ce modèle permet d'identifier en amont les clients à risque pour cibler les campagnes de fidélisation.

---

## 📊 Résultats

| Modèle | Accuracy | ROC-AUC |
|--------|----------|---------|
| Régression Logistique | ~81% | ~0.77 |
| **Random Forest** | **~86%** | **~0.86** |

Le Random Forest surpasse significativement la baseline logistique, notamment sur le Recall de la classe churn (+15%).

---

## 🔍 Dataset

- **Source** : [Churn Modelling – Kaggle](https://www.kaggle.com/datasets/shrutimechlearn/churn-modelling)
- **Taille** : 10 000 clients, 14 variables
- **Cible** : `Exited` — 1 si le client a quitté la banque (20.4% de la population)
- **Variables clés** : Age, Balance, NumOfProducts, IsActiveMember, Geography, CreditScore

---

## 🗂️ Structure du projet

```
churn-prediction/
├── churn_prediction.py     # Pipeline ML complet
├── requirements.txt        # Dépendances Python
└── README.md
```

---

## ⚙️ Pipeline ML

```
Données brutes (CSV)
    ↓
Analyse exploratoire (EDA) — distributions, corrélations, déséquilibre de classes
    ↓
Preprocessing — suppression colonnes inutiles, LabelEncoder, One-Hot Encoding
    ↓
Train/Test Split — 80/20, stratifié sur la classe cible
    ↓
Normalisation (StandardScaler) — fit uniquement sur le train set (pas de data leakage)
    ↓
Entraînement — Régression Logistique + Random Forest
    ↓
Évaluation — Classification Report, Matrice de Confusion, Courbe ROC, Feature Importance
```

---

## 🚀 Installation & Lancement

**1. Cloner le repo**
```bash
git clone https://github.com/<ton-username>/churn-prediction.git
cd churn-prediction
```

**2. Installer les dépendances**
```bash
pip install -r requirements.txt
```

**3. Télécharger le dataset**

Télécharger `Churn_Modelling.csv` depuis [Kaggle](https://www.kaggle.com/datasets/shrutimechlearn/churn-modelling) et le placer à la racine du projet.

**4. Lancer**
```bash
python churn_prediction.py
```

4 graphiques PNG sont générés : distribution du churn, matrice de corrélation, matrices de confusion, courbe ROC.

---

## 📦 Dépendances

```
pandas
numpy
matplotlib
seaborn
scikit-learn
```

---

## 📈 Insights métier

Les variables les plus prédictives identifiées par la Feature Importance du Random Forest :

1. **Age** — les clients plus âgés (45+) churent significativement plus
2. **NumOfProducts** — les clients avec 3-4 produits churent massivement (paradoxe de sur-engagement)
3. **IsActiveMember** — les membres inactifs sont 2× plus susceptibles de partir
4. **Balance** — les soldes élevés sans activité sont un signal fort de départ imminent

---

## 🔧 Améliorations possibles

- [ ] Gérer le déséquilibre de classes avec SMOTE ou `class_weight='balanced'`
- [ ] Optimisation des hyperparamètres avec `GridSearchCV`
- [ ] Remplacer Random Forest par XGBoost ou LightGBM
- [ ] Ajouter l'interprétabilité avec SHAP
- [ ] Encapsuler dans un `Pipeline` scikit-learn
- [ ] Déployer comme API REST avec FastAPI

---

## 👤 Auteur

**Yanis** — Étudiant BTS SIO SLAM, en recherche d'alternance Data/IA (Licence/Bachelor)

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-blue)](https://linkedin.com/in/ton-profil)
[![Portfolio](https://img.shields.io/badge/Portfolio-Voir-black)](https://ton-portfolio.netlify.app)
