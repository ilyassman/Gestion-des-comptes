# Application Bancaire Android

Application Android pour la gestion des comptes bancaires, prenant en charge les formats JSON et XML.

## Fonctionnalités

- Liste des comptes bancaires
- Ajout de nouveaux comptes 
- Modification des comptes existants
- Suppression de comptes
- Basculement entre formats JSON et XML
- Synchronisation en temps réel avec le serveur



## Technologies Utilisées

- **Retrofit**: Client API REST
- **Simple XML**: Sérialisation XML
- **RecyclerView**: Affichage dynamique
- **Material Design**: Composants UI
- **AlertDialog**: Interactions utilisateur

## Points d'API

- GET `/banque/comptes`: Récupérer tous les comptes
- GET `/banque/comptes/{id}`: Récupérer un compte spécifique
- POST `/banque/comptes`: Créer un compte
- PUT `/banque/comptes/{id}`: Modifier un compte
- DELETE `/banque/comptes/{id}`: Supprimer un compte

## Installation

1. Cloner le dépôt
2. Ouvrir dans Android Studio
3. Configurer l'URL de l'API backend dans RetrofitClient
4. Compiler et lancer l'application

## Propriétés des Comptes

- `id`: Identifiant unique
- `solde`: Solde du compte
- `type`: Type de compte (COURANT/EPARGNE)
- `dateCreation`: Date de création

## Formats de Données

L'application prend en charge :
- JSON: Format par défaut
- XML: Format alternatif avec classe wrapper

## Prérequis

- Android SDK 21+
- Serveur backend en fonctionnement
- Connexion Internet
## Video Demonstration


https://github.com/user-attachments/assets/14a62812-7ea2-40c0-b694-b9d199331d2a


