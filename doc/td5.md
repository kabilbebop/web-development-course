# Utilisation de l'ORM Spring-data : Hibernate

## Exercice 1 : modification d'une colonne de base de données

On va renommer la colonne ```name``` de la table ```manufacturer``` en ```brand_name```. 

- dans le fichier ```master.xml```, décommenter la partie ```TD5 changelog``` : un nouveau fichier xml sera lancé au démarrage et renommera la colonne en base de données
- redémarrer l'application et essayer d'afficher la page : vous constaterez que rien ne s'affiche
    - trouver le message qui explique la cause de l'erreur (le message doit être explicite)

Hibernate mappe automatiquement le nom des variables sur le nom des colonnes, en suivant cette règle :
```monAttribut``` en Java devient ```mon_attribut``` en SQL. Donc ```brand_name``` en SQL attend un attribut ```brandName```.

- essayer de renommer l'attribut ```name``` en ```brandName``` dans la classe ```Manufacturer``` (qui est devenue ```Brand``` depuis le TD4)
  - vous pouvez utiliser le menu _Refactor_ du TD4
- redémarrer l'application et constater que les voitures s'affichent bien, mais pas le ```manufacturer name```
    - pourquoi?

On constate qu'il y a déjà pas mal d'impact sur le code, ce n'est pas la solution la plus simple. 
Dans ce cas, le mieux est de changer de stratégie : on ne va pas renommer la variable Java, mais on va indiquer à Hibernate que le nom de la colonne a changé.

- annuler les modifications précédentes
    - vous pouvez faire un clic-droit sur la classe ```Manufacturer``` 
    - cliquer sur le menu _local history> show history_
    - faire un clic-droit sur la version à laquelle on veut revenir et _revert_

- utiliser une annotation @Column pour indiquer à hibernate le nouveau nom de la colonne : ```brand_name```
    - cette annotation est à placer au dessus du champs ```name``` 
- redémarrer et constater le bon fonctionnement de l'application 

## Exercice 2 : implémenter l'API appelée par les boutons "TOP 10"
Il y a 3 boutons TOP 10 : 
- TOP 10 Weight : les 10 voitures les plus légères
- TOP 10 Power : les 10 voitures les plus puissantes
- TOP 10 Ratio : les 10 voitures ayant le meilleur rapport poids/puissance (donc les 10 plus petites valeurs en kg/ch)

Chaque bouton appelle la méthode ```top10Click()``` du fichier typescript ```app.tsx```.
Cette méthode appelle une API qui n'est pas implémentée côté backend.

Pour cela, vous devez implémenter : 
- une méthode dans le contrôleur CarResource qui intercepte l'url de l'API
  - pour tester que la méthode est bien configurée, vous pouvez mettre un point d'arrêt dessus : un clic sur un des boutons doit déclencher le point d'arrêt
- cette méthode appelera une autre méthode de recherche que vous coderez dans ```CarRepository```
   - appuyez-vous sur la doc de [Spring Data](https://docs.spring.io/spring-data/jpa/docs/1.10.1.RELEASE/reference/html/#jpa.query-methods.query-creation)
   - **Attention** pour le calcul du ratio, vous devez ignorer les valeurs nulles de weight et power!


## Exercice 3 : implémenter l'API de filtre
Vous remarquerez un champs texte au dessus de la liste des voitures : ce champs texte appelle à chaque changement une API de filtre qui n'existe pas encore.
Vous devez implémenter cette API comme pour l'exercice précédent.
Le résultat doit être le suivant :  
- quand je tape du texte dans la partie _filter_, je dois voir apparaître uniquement les voitures dont le _manufacturer_ (_brand_), le _model_ ou la _variant_ comprends le texte, peu importe la casse (majuscules/minuscules)
- implémenter un test JUNIT qui valide le cas d'utilisation nominal : recherche avec un texte conforme qui ramène au moins 1 élément

## Exercice 4 (bonus) : implémenter un tri par défaut
On voudrait que la recherche initiale ```/api/cars``` et la recherche filtrée ```api/cars/search``` soient triées par défaut.
La logique de tri est l'ordre "naturel" :
- par ordre alphabétique sur le nom de la marque
- puis par ordre alphabétique sur le nom du modèle
- puis par ordre alphabétique sur le nom de la variante
- les valeurs nulles sont affichées en 1er
- on ignore la casse (majuscules/minuscules)
Il faut donc modifier les 2 API sans dupliquer le code de la logique de tri.
