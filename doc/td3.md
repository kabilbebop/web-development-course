# Qualité de code, refactoring et générateurs
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

## Les outils de refactoring
### Renommage
La classe Manufacturer n'est pas très parlante, on peut vouloir la renommer en classe Brand (Marque)
  - comprendre les propositions d'IntelliJ pour garder la cohérence dans le nommage des variables et les commentaires
  - constater les limites avec le code javascript qui n'est pas pris en compte
  - corriger à la main et vérifier que l'application fonctionne toujours
### Factorisation
Lorsque l'on trouve du code dupliqué, on peut vouloir le factoriser pour appliquer le principe DRI.
C'est le cas dans CarResourceIntTest.java : 
- à la fin de createCar() et à la fin de updateCar()
IntelliJ facilite le refactoring :
- sélectionner les lignes dupliquées
- clic-droit > refactoring > extract > method
- nommer la nouvelle sous-méthode assertCarEqual
Une fenêtre s'ouvre pour proposer une signature différente : IntelliJ a détecté la duplication et vous propose de factoriser le code à votre place.
- accepter la nouvelle signature
- accepter le remplacement de la factorisation du code dupliqué
- constater le refactoring effectué
- relancer les tests pour voir si tout marche encore
## Génération de code
Pour être plus productif, il ne faut pas hésiter à faire appel au fonctionnalités de l'IDE pour générer du code rapidement.
### Exercice
- ouvrir la classe org.weightcars.Test
- décommenter la ligne de la méthode main()
- faire en sorte que cette méthode affiche la sortie suivante en utilisant uniquement la génération de code

```Test{tata='tata', tutu=0, titi=Sat Dec 08 14:37:37 CET 2018}```

- _remarques_ :
  - titi = date du jour bien entendu!
  - pour lancer la méthode main, utiliser le bouton ▶ à côté de la méthode main()
 
