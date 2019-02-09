# Intégration continue et déploiement continu
## Tester les scripts en local

Les différentes tâches gradle que nous utiliserons pour le TD sont :
- ```bootJar``` *avec le profile prod*
- ```test``` pour les tests java
- ```jsTest``` pour les tests javascript
- ```tsLint``` pour la revue de code javascript

Vérifier avant tout que tous ces scripts fonctionnent bien en local.

## Intégration continue sur Gitlab ISIMA
## Créer le pipeline Gitlab CI 
Compléter le fichier manifest ```.gitlab-ci.yml``` pour créer 2 stages :
  - build
  - test

Le stage build comportera 1 job :
- nom : "build jar"

Le stage test comportera 3 jobs :
- "test java"
- "test javascript"
- "review javascript"

## Tester le pipeline
- Faire un push de votre branche
- Connectez vous à gitlab.isima.fr
- Ouvrir le projet weightcars
- Aller dans la rubrique CI/CD > pipeline
- Chercher le pipeline de votre branche et observez si tous les "stages" passent au verts
- Si vous avez des "stages" rouge, cliquez dessus et essayer d'analyser la trace 

## Déploiement continu sur une plateforme Cloud
### Pivotal CloudFoundry
- Créer son compte sur la plateforme [Pivotal CloudFoundry](https://run.pivotal.io)
- Créer une organisation
- Créer un service ElephantSQL qui correspondra à la base de données postgres de prod
- Charger cette base avec le fichier ```config/liquibase/dump-2500cars.sql```

### Gitlab.com
Pousser les sources dans Gitlab.com
- Créer son compte sur [Gitlab.com](https://gitlab.com) 
- Créer un projet
- Ajouter ce projet comme remote
- Faire un push

### Ajouter le pipeline de déploiement continu

Ajouter les variables suivantes dans Gitlab (menu _Settings > CI / CD > environment variables_) : 
- CF_USERNAME : nom de votre utilisateur sur Pivotal CloudFoundry
- CF_PASSWORD : mot de passe

Dans le fichier manifest ```.gitlab-ci.yml```, ajouter le pipeline de déploiement :

```
push to cloudfoundry:
    stage: deploy
    script:
    - curl --location "https://cli.run.pivotal.io/stable?release=linux64-binary&source=github" | tar zx
    - ./cf login -u $CF_USERNAME -p $CF_PASSWORD -a api.run.pivotal.io
    - ./cf push
    only:
    - master
```

Le job de build génère un fichier ```weight-cars-1.0.jar``` qu'il faut ensuite passer au job de deploy, pour faire cela il faut utiliser la commande suivante :
```
    artifacts:
        paths:
        - build/libs/*.jar
```


- Commit & push du fichier
- Vérifier l'exécution du job
- Connectez-vous sur console.run.pivotal.io pour vérifier l'état de l'application
