# TD 1 : Mise en place de l’environnement de développement
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
- src/main/java : le code java de l'application
- src/main/test : le code java des tests automatisés
#### Le front-end Javascript:
- src/webapp/app : le code javascript de l'application
### La partie configuration
#### Java : Gradle
- build.gradle : le fichier principal avec les dépendances et les scripts
- gradle/*.gradle : des sous-fichiers de scripts
- gradle.properties : un fichier de propriétés modifiable
#### Javascript :
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
