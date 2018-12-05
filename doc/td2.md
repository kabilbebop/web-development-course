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

## Exercice: analyser et corriger un bug
Dans la page principale de l'application, sélectionner un fabriquant de voiture pour filtrer la liste.
Vous constatez que certaines voitures apparaissent en double dans la liste.
Pour résoudre le problème :
- Mettez un point d'arrêt dans la classe CarResource, méthode searchCarsByString()
- Analysez ce que fait la méthode et trouver une solution pour dédoublonner la liste renvoyée
- Plusieurs méthodes sont possibles : 
  - consulter la javadoc de la classe java.util.Set pour la solution la plus facile
  - la solution la plus performante est de n'effectuer qu'un seul appel à CarRepository
- Faire un git commit et un git push en utilisant IntelliJ
