# TD Fondamentaux Java
## Exercice 1 : les types de collection
1. Dans le fichier CollectionPlayground.java
    1. observer la méthode testArray()
    2. lancer le test avec ▶ dans la marge
    3. observer la sortie console
2. Ajouter une méthode testArrayList() qui fait exactement la même chose, en remplaçant

    ```
    String[] strings = new String[3];
    ```

    par

    ```
    ArrayList<String> strings = new ArrayList<>();
    ```
    
3. Ecrire une méthode testHashSet() qui fait exactement la même chose, en remplaçant ArrayList par HashSet.
    1. pour afficher le contenu, utiliser System.out.println(strings);
    2. observer la différence des valeurs affichées

4. Dans la méthode testHashMap1(), remplacer le commentaire pour avoir la sortie suivante :
    
    {1=A, 2=b, 3=c}  

5. Dans la méthode testHashMap2(), remplacer le commentaire pour afficher la sortie suivante :
    
    a    
    b    
    c    
    
### Exercice 2 : les boucles

1. Au début du fichier CollectionLoop.java :
    1. vous pouvez observer qu'un objet MyObject est déclaré
    2. une liste de ces objets nommée _myObjects_ est également déclarée, c'est cette liste que nous manipulerons

2. Ajouter à ce fichiers 3 méthodes qui parcourent la liste et qui affiche chaque objet avec System.out.println() :
    1. testForLoop1 qui parcourt la liste avec une boucle for ( : )
    2. testForLoop2 qui parcourt la liste avec une boucle for avec compteur
    3. testForLoop3 qui parcourt la liste avec une boucle forEach

### Exercice 3 : les exceptions

1. Ouvrez le fichier Exceptions.java : ajouter l'annotation @Test sur la méthode testThrowsException() et exécutez le test. Observez l'exception de la sortie console.
2. Modifier la méthode pour intercepter l'exception qui est lancée et afficher uniquement le message simplifié :
/ by zero
3. Modifier la méthode pour intercepter l'exception et relancer un autre exception de type RuntimeException
    1. cette RuntimeException devra avoir comme message "Une erreur est survenue"
    2. elle devra contenir comme cause l'exception interceptée
    3. la sortie du test devrait donc être :
 
```
        java.lang.RuntimeException: Une erreur est survenue
        	at playground.Exceptions.testCausedBy(Exceptions.java:29)
        Caused by: java.lang.ArithmeticException: / by zero
        	at playground.Exceptions.testCausedBy(Exceptions.java:27)
        	... 22 more
```
4. Enlever l'annotation @Test pour ne pas laisser un test qui échoue dans le projet

### Excercice 4 : les loggers

1. Ouvrez le fichier Loggers.java et observer la déclaration de l'attribut _logger_
2. Dans la méthode testLogForLoop, ajoutez un appel à logger pour afficher à la place de System.out.println pour afficher l'objet. Observer les différences dans la sortie console.
3. Dans la méthode testLogException, à la place des commentaires, ajoutez un appel à logger pour afficher à la fois le message "Une erreur est survenue" et la stack trace de l'exception interceptée
4. La méthode testLogLevel() appelle la méthode logLevels() qui construit des variables _element_ prenant les valeurs A1, A2... C3. 
Utiliser le logger pour afficher la sortie suivante. Attention il faut modifier sa configuration dans le fichier logback.xml pour afficher le niveau DEBUG

```
WARN   playground.Loggers - Start
INFO   playground.Loggers - A
DEBUG  playground.Loggers - A1
DEBUG  playground.Loggers - A2
DEBUG  playground.Loggers - A3
INFO   playground.Loggers - B
DEBUG  playground.Loggers - B1
DEBUG  playground.Loggers - B2
DEBUG  playground.Loggers - B3
INFO   playground.Loggers - C
DEBUG  playground.Loggers - C1
DEBUG  playground.Loggers - C2
DEBUG  playground.Loggers - C3
WARN   playground.Loggers - End
```
