# Qualité de code, refactoring et générateurs

## Les outils de qualité de code (ou revue de code statique)

### Exercice 1 : IntelliJ pour le code Java
Importer du profil d'inspection :
  - menu _File > settings_
  - _Editor > Inspection_ : choisir la roue de configuration 🎡 puis _Import profile_
  - importer le fichier XML ```IntelliJ-inspection-profile.xml``` à la racine du projet

Lancer l'analyse :
  - Menu _Analyze > Inspect code_
  - dans le menu déroulant _Inspection profile_ en bas choisir le profil _WeightCars_ que nous venons d'importer
  - choisir _Custom scope_ puis ```...```
  - créer un nouveau profil avec le bouton + et le nommer _Java_
  - choisir _production classes > weight-cars > weight-cars_main_ puis _Include recursively_

Lancer l'analyse : vous devriez avoir 6 warnings à corriger.
Accéder à la description du problème en faisant un clic-droit sur le warning puis _Edit settings_.

### Exercice 2 : TSLint pour le code typescript
La liste des règles peut être configurée dans le fichier tsconfig.json
  - lancer la tâche Gradle verification > tsLint
  - constater que 4 warnings sont identifiés
  - essayer de corriger tous les warnings

## Les outils de refactoring

### Exercice 3 : Renommage d'une classe
Le nom de la classe ```Manufacturer``` n'est pas très parlant pour les marques de voiture, on veut la renommer en classe ```Brand```.
Dans cette exercice, vous allez utiliser le menu contextuel _Refactor_ pour renommer la classe :
  - clic-droit sur le nom de la classe, _Refactor > Rename_
  - IntelliJ va proposer d'impacter différentes portions du code : nommage des variables, commentaires, etc.
    Pour limiter les erreurs, on va se contenter de renommer le nom de la classe :
    - dans la 1ère fenêtre _Rename Variables_ : il ne faut rien cocher
    - dans la 2ème fenêtre _Refactoring Preview_, clic-droit pour exclure 
        - "usage in comments, strings and non-code files"
        - "references in generated code"
    - puis cliquer sur le bouton "do refactor"
  - constater le refactoring effectué en regardant les modifications dans l'onglet _Version Control > Local Changes_
    - seuls des fichier .java devraient avoir été modifiés
    - si ce n'est pas le cas, sélectionner tous les fichiers dans la vue _Local Changes_ puis clic-droit _Revert_
  - relancer les tests et l'application pour constater qu'ils fonctionnent toujours

### Exercice 4 : Factorisation de code
Lorsque l'on trouve du code dupliqué, on peut vouloir le factoriser pour appliquer le principe _DRY_.

C'est le cas dans ```CarResourceIntTest.java``` : 
  - à la fin de ```createCar()``` et à la fin de ```updateCar()```

IntelliJ facilite le refactoring :
  - sélectionner les lignes dupliquées
  - clic-droit puis _Refactoring > extract > method_
  - nommer la nouvelle sous-méthode ```assertCarEqual```

Une fenêtre s'ouvre pour proposer une signature différente : IntelliJ a détecté la duplication et vous propose de factoriser le code à votre place.
  - accepter la nouvelle signature
  - accepter le remplacement de la factorisation du code dupliqué
  - constater le refactoring effectué en regardant les modifications dans l'onglet _Version Control > Local Changes_
  - relancer les tests pour voir si tout marche encore

## Génération de code
Pour être plus productif, il ne faut pas hésiter à faire appel au fonctionnalités de l'IDE pour générer du code rapidement.

### Exercice 5
  - ouvrir la classe Generator
  - décommentez le code des méthodes ```testGenerator1``` et ```testGenerator2``` 
  - faire en sorte que les méthodes compilent et affichent les sorties suivantes

    ```
    [main] INFO playground.Generator - Film{name='The hitchhiker's guide to the galaxy', budget=50000000, date=2005-08-18}
    [main] INFO playground.Generator - Film{name='Snatch', budget=10000000, date=2000-11-15}
    ```

 Pour cela vous devez générer le code nécessaire dans la classe ```Film``` en bas du fichier :
 - faire un clic-droit dans le corps de la classe ```Film``` et utiliser le menu _Generate_ 
 - il est interdit de taper du code pour cette exercice!

## Gestion de dépendances avec Gradle
Nous allons observer comment la configuration des dépendances dans Gradle modifie les bibliothèques disponibles dans l'application.

### Exercice 6 : ajout d'une dépendance
- Ouvrir le fichier ```Dependencies.java``` et décommenter toutes les lignes
- Résoudre l'erreur de compilation en modifiant le fichier ```build.gradle```

### Exercice 7 : ménage
Le but de cet exercice est de voir comment la transitivité des dépendances _Spring Data_ rendent redondantes les dépendances _Hibernate_.
Nous allons donc faire le ménage :
- Dans ```build.gradle```, chercher les dépendances _Spring Boot Data JPA Starter_ et _Hibernate_.
- Ouvrir le menu _File > Project structure_ puis _Libraries_ : repérer les dépendances dans la liste
- Dans [mvnrepository.com](mvnrepository.com), Vérifier si les dépendances _Hibernate_ sont bien inclues dans la dépendance _Spring Boot Data JPA Starter_
- Supprimer les dépendances déjà inclues
- Lancer les tâches _clean_ et _build_
- Ouvrir le menu _File > Project structure_ puis _Libraries_ 
    - les dépendances sont-elles toujours dans la liste?
    - Pourquoi?

