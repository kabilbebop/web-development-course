# Initialiser l'application

- aller sur https://start.spring.io/
- remplir le formulaire [comme ceci](spring-initializr.png)
- cliquer sur "generate" et dézipper l'archive sur votre PC
- [ouvrir avec intellij]
- [ajouter dépendances et base de données, liquibase]
- 
- [démarrer l'app]


# Explorer et comprendre l’architecture de l’application


## L'architecture de l'application
[Schema](architecture.odt)

## Le code source

### Le back-end Java :
- **src/main/test** : le code java des tests automatisés
- **src/main/java** : le code java de l'application
  - org.weightcars.**domain** : les "domain class" ou "model" ou "entités" sont les objets représentant les tables de la base
  - org.weightcars.**repository** : les "repository" sont des interfaces permettant de faire des requêtes sur les objets métiers
  - org.weightcars.**controller** : les "ressources" ou "controllers" sont les classes qui contiennent les traitements des requêtes HTTP ("API REST" ou "web services REST")
  - utiliser les raccourcis pour naviguer dans le code :
    - ctrl+click pour descendre dans les appels de méthode
    - clic-droit + "find usages" pour voir où la méthode est appelée
    - ctrl+shift+F pour les recherches globales
- **src/resources/application.properties** : fichier de configuration principal de Spring
- **src/resources/db/** : fichier d'initialisation de la bdd (tables + données)

### Le front-end web de l'application :
- **src/resources/public/** : placer le contenu javascript/CSS/HTML dans ce répertoire permet de le rendre accessible par l'url de l'application

#### Exercice 1
_1) Chercher quelle est l'API appelée par le front-end javascript (à l'aide de la lib axios)_ 

_2) Chercher quelle méthode de controller traite cet appel_

_3) Chercher quelle est la méthode qui fera la requête en base de données_


## La partie configuration

### Java : Gradle
- build.gradle : le fichier principal avec les dépendances et les scripts
- gradle/*.gradle : des sous-fichiers de scripts
- gradle.properties : un fichier de propriétés modifiable

### Java : Intellij
- clic-droit sur le projet > open module settings

### Javascript : NPM
- package.json : le fichier principal avec les dépendances et les scripts
- webpack/*.js : des sous-fichiers de script

#### Exercice 2 : lancer les tâches de build
compiler l'application :
- lancer la tâche node > npmInstall
    - observer la création du répertoire node_modules
- lancer la tâche build > build
    - observer la création du répertoire build
- compiler Java uniquement : menu Build > Build Project

#### Exercice 3 : Lancer les tâches de test
- Java : 
  - Tool window "Gradle" à droite : task > verification > test
  - pour avoir une vue plus sympa : run > edit configuration
    - cliquer sur "+" et choisir junit
    - choisir "Search for test: in whole project"
- Javascript : dans un terminal
  - npm run test


## Le packaging

#### Exercice 4 : 
- Lancer le packaging :
  - depuis l'onglet "Gradle" sur le côté droit de l'écran, lancer la tâche "task > build > bootJar"
      - un fichier **.jar** est généré dans build/libs/
      - ouvrez ce fichier et trouver dans quel répertoires sont les classes et les lib
  - lancer la tâche "task > build > bootWar"
      - un fichier **.war** est généré dans build/libs/
      - ouvrez ce fichier et trouver dans quel répertoires sont les classes et les lib
- Exécuter l’application sur votre poste local :
  - lancer la tâche "task > application > bootRun"
  - cette tâche va lancer l'application, connectez-vous à l'URL [http://localhost:8745](http://localhost:8745)
  
