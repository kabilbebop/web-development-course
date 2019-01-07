# Qualité de code, refactoring et générateurs
## Les outils de qualité de code (ou revue de code statique)
### IntelliJ pour le code Java
Importer du profil d'inspection :
* menu _File > settings_
* _Editor > Inspection_ : choisir la roue de configuration 🎡 puis _Import profile_
* importer le fichier XML ```IntelliJ-inspection-profile.xml``` à la racine du projet

Lancer l'analyse :
* Menu _Analyze > Inspect code_
* dans le menu déroulant _Inspection profile_ en bas choisir le profil _WeightCars_ que nous venons d'importer
* choisir _Custom scope_ puis ```...```
* créer un nouveau profil avec le bouton + et le nommer _Java_
* choisir _production classes > weight-cars > weight-cars_main_ puis _Include recursively_

Lancer l'analyse : vous devriez avoir 6 warnings à corriger.
Accéder à la description du problème en faisant un clic-droit sur le warning puis _Edit settings_.

### TSLint pour le code typescript
La liste des règles peut être configurée dans le fichier tsconfig.json
* lancer la tâche Gradle _Other > webpackBuildDev_
* constater que des warnings sont identifiés
* essayer de corriger tous les warnings
* identifier si certains sont compliqués à corriger sans désactiver la règle

## Les outils de refactoring
### Renommage
La classe ```Manufacturer``` n'est pas très parlant pour la marque d'une voiture, on veut la renommer en classe ```Brand```.
  - utiliser le menu contextuel _Refactor_ pour renommer la classe
  - comprendre les propositions d'IntelliJ pour garder la cohérence dans le nommage des variables et les commentaires
  - constater les limites avec le code javascript qui n'est pas pris en compte
  - corriger à la main et vérifier que l'application fonctionne toujours
### Factorisation
Lorsque l'on trouve du code dupliqué, on peut vouloir le factoriser pour appliquer le principe DRI.
C'est le cas dans CarResourceIntTest.java : 
- à la fin de ```createCar()``` et à la fin de ```updateCar()```
IntelliJ facilite le refactoring :
- sélectionner les lignes dupliquées
- clic-droit puis _Refactoring > extract > method_
- nommer la nouvelle sous-méthode ```assertCarEqual```
Une fenêtre s'ouvre pour proposer une signature différente : IntelliJ a détecté la duplication et vous propose de factoriser le code à votre place.
- accepter la nouvelle signature
- accepter le remplacement de la factorisation du code dupliqué
- constater le refactoring effectué
- relancer les tests pour voir si tout marche encore
## Génération de code
Pour être plus productif, il ne faut pas hésiter à faire appel au fonctionnalités de l'IDE pour générer du code rapidement.
### Exercice
- ouvrir la classe org.weightcars.Test
- décommenter la ligne de la méthode ```main()```
- faire en sorte que cette méthode affiche la sortie suivante en utilisant uniquement la génération de code

```Test{tata='tata', tutu=0, titi=Sat Dec 08 14:37:37 CET 2018}```

- _remarques_ :
  - titi = date du jour bien entendu!
  - pour lancer la méthode main, utiliser le bouton ▶ à côté de la méthode main()
 
