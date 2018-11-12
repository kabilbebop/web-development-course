# TD 1 : Setup environnement de dev
## Cloner le repo GIT
git clone https://gitlab.com/pjben/weight-cars.git

## Importer le projet dans Intellij
- menu File > Open 
- Import project from external model : Gradle
- (optional) Select Gradle JVM
- Finish

## Explorer et comprendre l’architecture de l’application
### Architecture
[Schema](architecture.jpg)
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
#### Javascript : NPM
- package.json : le fichier principal avec les dépendances et les scripts
- webpack/*.js : des sous-fichiers de script
### Les répertoires temporaires
- node_modules (Javascript)
- build (Java)
## Lancer les tâches de build
- Java : menu Build > Build Project
- Javascript : dans un terminal
  - npm install
  - npm run start
## Lancer les tâches de test
- Java : 
  - Tool window "Gradle" à droite : task > verification > test
- Javascript : dans un terminal
  - npm run test
## Lancer le packaging
- Tool window "Gradle" à droite : task > build > bootJar
  - un fichier .jar est généré dans build/libs/
## Exécuter l’application sur votre poste local
- Tool window "Gradle" à droite : task > application > bootRun
