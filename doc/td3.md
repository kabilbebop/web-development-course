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
