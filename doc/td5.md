# Utilisation de l'ORM Spring-data : Hibernate

## Exercice 1 : modification d'une colonne de base de données

On va renommer la colonne "name" de la table "manufacturer" en "brand_name". 

- dans le fichier master.xml, décommenter la partie "TD5 changelog" : cela appelera un nouveau fichier xml qui renommera la colonne en base de données
- redémarrer l'application et essayer d'afficher la page : vous constaterez que rien ne s'affiche
    - trouver le message qui explique la cause de l'erreur (le message doit être explicite)
- essayer de corriger le problème en renommant l'attribut "name" en "brandName" dans la classe Manufacturer
- redémarrer l'application et constater que les voitures s'affichent bien, mais pas le "manufacturer name"
    - pourquoi?

On constate qu'il y a déjà pas mal d'impact sur le code. Le mieux ce n'est pas de renommer la variable en Java mais d'indiquer à Hibernate que le nom de la colonne a changé.

- annuler les modifications précédentes
    - vous pouvez faire un clic-droit sur la classe Manufacturer 
    - cliquer sur le menu "local history>show history"
    - faire un click droit sur la version à laquelle on veut revenir et "revert"

- utiliser une annotation @Column pour indiquer à hibernate le nouveau nom de la colonne : "brand_name"
    - cette annotation est à placer au dessus du champs "name" 
- redémarrer et constater le bon fonctionnement de l'application 

## Exercice 3 : implémenter les API appelées par les boutons "TOP 10"
- !!Vous devez ignorer les valeurs nulles de weight et power!!
- TOP 10 Weight : les 10 voitures les plus légères
- TOP 10 Power : les 10 voitures les plus puissantes
- TOP 10 Ration : les 10 voitures ayant le meilleur rapport poids/puissance (donc les 10 plus petites valeurs en kg/ch)
- Soumettez votre correction sur une branche en utilisant IntelliJ et Gitlab
- Créez une merge request

## Exercice 4 : implémenter l'API de filtre
- Quand je tape du texte dans la partie "filter", je dois voir apparaître uniquement les voitures dont le "manufacturer", le "model" ou la "variant" comprends le texte, peu importe la casse
- Implémenter un test JUNIT qui valide le cas d'utilisation nominal : recherche avec un texte conforme qui ramène au moins 1 élément
- Soumettez votre correction sur une branche en utilisant IntelliJ et Gitlab
- Créez une merge request
