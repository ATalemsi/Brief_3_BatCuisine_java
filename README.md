# Bati-Cuisine - Application d'Estimation des Coûts de Construction des Cuisines

## Description

**Bati-Cuisine** est une application Java destinée aux professionnels de la construction et de la rénovation de cuisines. Elle permet d'estimer avec précision les coûts des projets en prenant en compte les matériaux utilisés, la main-d'œuvre, ainsi que les taxes. L'application offre également une gestion complète des clients, des devis personnalisés et une vue d'ensemble sur les aspects financiers et logistiques des projets.

---

## Fonctionnalités

### 1. Gestion des Projets
- Création et gestion de projets de rénovation ou de construction de cuisines.
- Ajout de clients associés à chaque projet.
- Gestion des composants du projet (matériaux, main-d'œuvre).
- Génération de devis pour chaque projet avec estimation des coûts avant le début des travaux.
- Suivi des états du projet : `En cours`, `Terminé`, ou `Annulé`.

### 2. Gestion des Composants
- **Matériaux** : Suivi du coût unitaire, des quantités, et des coûts de transport des matériaux.
- **Main-d'œuvre** : Suivi des heures de travail, des taux horaires, et de la productivité des ouvriers.
- Calcul précis des coûts avec application de la TVA et ajustement selon la qualité des matériaux et la productivité des ouvriers.

### 3. Gestion des Clients
- Enregistrement des informations de base des clients.
- Différenciation entre clients professionnels et particuliers.
- Calcul et application de remises spécifiques selon le type de client.

### 4. Création de Devis
- Génération de devis avant le début des travaux, incluant les coûts estimés des matériaux, de la main-d'œuvre, et des taxes.
- Gestion des dates d'émission et de validité des devis.
- Possibilité pour le client d'accepter ou de refuser un devis.

### 5. Calcul des Coûts
- Intégration des coûts des matériaux et de la main-d'œuvre pour calculer le coût total du projet.
- Application de la marge bénéficiaire pour obtenir le coût final.
- Gestion des ajustements des coûts basés sur la qualité des matériaux ou la productivité des ouvriers.

### 6. Affichage des Détails et Résultats
- Affichage des détails complets des projets (client, composants, coûts).
- Génération d'un récapitulatif détaillé incluant les matériaux, la main-d'œuvre, les taxes, et la marge bénéficiaire.

---

## Exigences Techniques

### Langage
- Java

### Environnement
- Application standalone pour les professionnels de la construction.

### Bibliothèques Utilisées
- TBD

---

## Structure de la Base de Données

### Projet
- **nomProjet** : `String` - Nom du projet de rénovation.
- **margeBeneficiaire** : `double` - Marge bénéficiaire appliquée.
- **coutTotal** : `double` - Coût total estimé du projet.
- **etatProjet** : `Enum` - Statut du projet (En cours, Terminé, Annulé).

### Matériaux
- **nom** : `String` - Nom du matériau.
- **coutUnitaire** : `double` - Coût unitaire du matériau.
- **quantite** : `double` - Quantité utilisée dans le projet.
- **typeComposant** : `String` - Matériel ou Main-d'œuvre.
- **tauxTVA** : `double` - Taux de TVA applicable.
- **coutTransport** : `double` - Coût du transport du matériau.
- **coefficientQualite** : `double` - Coefficient qualité du matériau.

### Main-d'œuvre
- **nom** : `String` - Nom de la main-d'œuvre.
- **tauxHoraire** : `double` - Taux horaire.
- **heuresTravail** : `double` - Nombre d'heures travaillées.
- **productiviteOuvrier** : `double` - Facteur de productivité.

### Client
- **nom** : `String` - Nom du client.
- **adresse** : `String` - Adresse du client.
- **telephone** : `String` - Numéro de téléphone.
- **estProfessionnel** : `boolean` - Indique si le client est un professionnel ou un particulier.

### Devis
- **montantEstime** : `double` - Estimation du montant total du projet.
- **dateEmission** : `Date` - Date d'émission du devis.
- **dateValidite** : `Date` - Date de validité du devis.
- **accepte** : `boolean` - Indique si le devis a été accepté par le client.

---

## Instructions d'Installation

1. Clonez le dépôt :  
   `git clone https://github.com/username/bati-cuisine.git`
   
2. Ouvrez le projet dans votre IDE préféré (par exemple IntelliJ IDEA ou Eclipse).

3. Compilez le projet :  
   `mvn clean install`

4. Exécutez le fichier principal `Main.java`.

---

## Utilisation

1. **Créer un nouveau projet** : Utilisez l'interface pour créer un nouveau projet, associez un client, et ajoutez des composants (matériaux et main-d'œuvre).
2. **Générer un devis** : Calculez les coûts totaux des composants et générez un devis.
3. **Gestion des clients** : Enregistrez les informations clients et appliquez des remises ou des taxes spécifiques.

---

## Auteurs

- **Nom** : [Votre nom]
- **Email** : [Votre email]

---

## License

Ce projet est sous licence MIT. Veuillez consulter le fichier LICENSE pour plus de détails.
