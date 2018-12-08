# TD 2 : Analyser le comportement de l'application
## Base de données
La base de donnée est une simple base H2 stockée dans un fichier.
Essayer de vous connecter à ce fichier avec un client JDBC : 
- Télécharger et installer https://dbeaver.io/download/
- Au lancement de l'application sélectionner une connexion de type H2 embedded
- Renseigner l'URL JDBC avec les infos du fichier application.yml
  - il faut que l'application soit arrêtée pour accéder au fichier
- Explorer les tables et les données et générer le modèle de données

## Debbugger Chrome
- Se connecter avec Chrome à http://localhost:8745
- Ouvrir Chrome Devtools (F12)
- Ouvrir l'onglet Tools
- Ouvrir la barre de recherche et taper app.tsx pour l'ouvrir
- Mettre un point d'arrêt dans le code et raffraichir la page

**le point d'arrêt doit bloquer l'exécution du navigateur**
- Utiliser la touche F10 pour avancer pas à pas et F8 pour libérer l'exécution
- Ouvrir l'onglet Network et filtrer les requêtes XHR
- Observer l'échange avec le back-end

## Debugger Intellij
- Ouvrir la classe CarResource (ctrl + n)
- Placer un point d'arrêt dans la méthode getAllCars()
  - c'est cette méthode qui est appelée au chargement de la page par la requête HTTP vue ci-dessus
- Sélectionner la configuration "bootRun" dans la liste déroulante de la barre d'outil en haut à droite
- Cliquer sur le bouton de debug juste à côté
- Recharger la page de l'application

**le point d'arrêt doit bloquer l'exécution du back-end**
- Utiliser la touche F8 pour avancer pas à pas et F9 pour libérer l'exécution
- Placer le curseur au dessus de la variable cars de la ligne "return cars;" pour inspecter le résultat de l'appel à findAll()

## Les outils de test d'API
### Swagger UI
http://localhost:8745/swagger-ui/index.html

* Déplier les opérations sur la ressource "Manufacturer"
* Poster un nouvel objet Manufacturer au format JSON pour le créer en base

### Postman
Lancer Postman 
* effectuer une requête GET sur l'url "cars" appelée au chargement de la page dans l'onglet réseau
* effectuer la requête POST identique à celle effectuée avec Swagger

## Les outils de qualité de code (ou revue de code statique)
### IntelliJ pour le code Java
Importer un profil d'inspection fourni à la racine du projet
* menu File > settings
* Inspection : choisir la roue de configuration et importer
* importer le fichier XML
Créer un custom scope :
* Menu Analyze > Inspect code
* choisir custom scope puis "..."
* choisir "production classes" > "weight-cars" > "weight-cars_main"
Lancer l'analyse et corriger les problèmes identifiés.

### TSLint pour le code typescript
La liste des règles peut être configurée dans le fichier tsconfig.json
* lancer la tâche Gradle other > webpackBuildDev
* constater que des warnings sont identifiés
* essayer de corriger tous les warnings
* identifier si certains sont compliqués à corriger sans désactiver la règle
