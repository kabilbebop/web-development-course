# Setup environnement de dev

## Explorer et comprendre l’architecture de l’application
### Architecture
[Schema](architecture.odt)
### Le code source
#### Le back-end Java :
- src/main/test : le code java des tests automatisés
- src/main/java : le code java de l'application
  - org.weightcars.domain : les "domain class" ou "model" ou "entités" sont les objets représentant les tables de la base
  - org.weightcars.repository : les "repository" sont des interfaces permettant de faire des requêtes sur les objets métiers
  - org.weightcars.web.rest : les "ressources" ou "controllers" sont les classes qui contiennent les traitements des requêtes HTTP ("API REST" ou "web services REST")
  - utiliser les raccourcis pour naviguer dans le code :
    - ctrl+click pour descendre dans les appels de méthode
    - clic-droit + "find usages" pour voir où la méthode est appelée
    - ctrl+shift+F pour les recherches globales
#### Le front-end Javascript:
- src/webapp/app : le code javascript de l'application
#### Exercice
_1) Chercher quelle est l'API appelée par le front-end javascript (à l'aide de la lib axios)_ 

_2) Chercher quelle méthode de controller traite cet appel_

_3) Chercher quelle est la méthode qui fera la requête en base de données_
### La partie configuration
#### Java : Gradle
- build.gradle : le fichier principal avec les dépendances et les scripts
- gradle/*.gradle : des sous-fichiers de scripts
- gradle.properties : un fichier de propriétés modifiable
#### Java : Intellij
- clic-droit sur le projet > open module settings
#### Javascript : NPM
- package.json : le fichier principal avec les dépendances et les scripts
- webpack/*.js : des sous-fichiers de script
### Les répertoires temporaires
- node_modules (Javascript)
- build (Java)
## Lancer les tâches de build
compiler
- lancer la tâche node > npmInstall
    - observer la création du répertoire node_modules
- lancer la tâche build > build
    - observer la création du répertoire build
- compiler Java uniquement : menu Build > Build Project
## Lancer les tâches de test
- Java : 
  - Tool window "Gradle" à droite : task > verification > test
  - pour avoir une vue plus sympa : run > edit configuration
    - cliquer sur "+" et choisir junit
    - choisir "Search for test: in whole project"
- Javascript : dans un terminal
  - npm run test
## Lancer le packaging
- Tool window "Gradle" à droite : task > build > bootJar
  - un fichier .jar est généré dans build/libs/
## Exécuter l’application sur votre poste local
- Tool window "Gradle" à droite : task > application > bootRun
  - cela lance le jar Java avec l'application javascript contenu à l'intérieur
  
- pour développer en Javascript et profiter du "live reload"
  - dans un terminal : npm run start
