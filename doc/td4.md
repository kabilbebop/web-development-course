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
* choisir _Custom scope_ puis "..."
* créer un nouveau profil avec le bouton + et le nommer _Java_
* choisir _production classes > weight-cars > weight-cars_main_ puis _Include recursively_

Lancer l'analyse : vous devriez avoir 6 warnings à corriger.
Accéder à la description du problème en faisant un clic-droit sur le warning puis _Edit settings_.

### TSLint pour le code typescript
La liste des règles peut être configurée dans le fichier tsconfig.json
* lancer la tâche Gradle verification > tsLint
* constater que 4 warnings sont identifiés
* essayer de corriger tous les warnings

## Les outils de refactoring
### Renommage
Le nom de la classe ```Manufacturer``` n'est pas très parlant pour les marques de voiture, on veut la renommer en classe ```Brand```.
  - utiliser le menu contextuel _Refactor_ pour renommer la classe
  - comprendre les propositions d'IntelliJ pour garder la cohérence entre le nommage des variables et les commentaires
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


 
