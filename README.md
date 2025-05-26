# MoovieMood - Android App

**MoovieMood** est une application mobile Android développée en Java. Elle recommande des films en fonction de l’humeur de l’utilisateur, tout en offrant une navigation par **inclinaison du téléphone** (capteur de rotation).  
Elle permet également de sauvegarder ses films favoris et de filtrer les suggestions selon l’humeur du jour.

---

## Fonctionnalités principales

- **Choix de l’humeur** :  
  Interface stylisée pour sélectionner une humeur (Happy, Sad, Nostalgic, etc.)

- **Recommandation de films** :  
  Récupération des films depuis l’API publique **TMDb** selon les genres liés à l’humeur choisie.

- **Navigation via inclinaison** :  
  Le changement de film se fait en inclinant légèrement le téléphone vers la droite ou la gauche (capteur `TYPE_ROTATION_VECTOR`).

- **Favoris** :

  - Ajout/suppression d’un film aux favoris avec l’icône .
  - Affichage des films likés dans une page "Profil"

- **Design responsive & expérience fluide** :
  Interface intuitive avec transitions fluides, icônes personnalisées, et styles adaptés à l’humeur choisie.

---

---

## Fonctionnement de la détection d’inclinaison

- Capteur utilisé : `Sensor.TYPE_ROTATION_VECTOR`
- Seuil : ±18 degrés sur l’axe **Y**
- États :
  - Inclinaison vers la droite → film suivant
  - Inclinaison vers la gauche → film précédent
  - Retour à 0° : aucun changement (évite les effets indésirables)

---

## API utilisée

**[TMDb (The Movie Database)](https://www.themoviedb.org/documentation/api)**  
L’API permet de récupérer :

- Les films selon des genres (`/discover/movie`)
- Les plateformes de visionnage (`/movie/{id}/watch/providers`)

Nécessite une clé API à générer sur le site officiel.

---

### Installation

Pour démarrer avec ce projet, suivez ces étapes :

1. Clonez le repository :

2. Ouvrir dans Android Studio :

   ```Android Studio > "Open an existing project" > MoovieMood

   ```

3. Ajouter votre clé API TMDb:

   ```Modifier la constante dans Constants.java
    public static final String TMDB_API_KEY = "VOTRE_CLE_API_ICI";
   ```

4. Lancer sur l’émulateur ou appareil physique

### Sitan
