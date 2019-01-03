# TD Fondamentaux Java
## Exercice 1 : les types de collection
1. Dans le fichier CollectionTypes.java
    * observer la méthode testArray()
    * lancer le test avec ▶ dans la marge
    * observer la sortie console
2. Ajouter une méthode testArrayList() qui fait exactement la même chose, en remplaçant

    ```
    String[] strings = new String[3];
    ```

    par

    ```
    ArrayList<String> strings = new ArrayList<>();
    ```
    
3. Ecrire une méthode testHashSet() qui fait exactement la même chose, en remplaçant ArrayList par HashSet.
    * pour afficher le contenu, utiliser System.out.println(strings);
    * observer la différence des valeurs affichées

4. Dans la méthode testHashMap1(), remplacer le commentaire pour avoir la sortie suivante :
    ```
    {1=A, 2=b, 3=c}  
    ```
5. Dans la méthode testHashMap2(), remplacer le commentaire pour afficher la sortie suivante :
    ```
    a    
    b    
    c   
    ``` 
    
### Exercice 2 : les boucles

1. Au début du fichier CollectionLoop.java :
    * vous pouvez observer qu'un objet ```MyObject``` est déclaré
    * une liste de ces objets nommée ```myObjects``` est également déclarée, c'est cette liste que nous manipulerons

2. Ajouter à ce fichiers 3 méthodes qui parcourent la liste et qui affiche chaque objet avec System.out.println() :
    * ```testForLoop1``` qui parcourt la liste avec une boucle for ( : )
    * ```testForLoop2``` qui parcourt la liste avec une boucle for avec compteur
    * ```testForLoop3``` qui parcourt la liste avec une boucle forEach

### Exercice 3 : les exceptions

1. Ouvrez le fichier Exceptions.java : ajouter l'annotation ```@Test``` sur la méthode ```testThrowsException()``` et exécutez le test. Observez l'exception de la sortie console.
2. Modifier la méthode pour intercepter l'exception qui est lancée et afficher uniquement le message simplifié :
/ by zero
3. Modifier la méthode pour intercepter l'exception et relancer un autre exception de type ```RuntimeException```
    * cette ```RuntimeException``` devra avoir comme message "Une erreur est survenue"
    * elle devra contenir comme cause l'exception interceptée
    * la sortie du test devrait donc être :
 
    ```
    java.lang.RuntimeException: Une erreur est survenue
        at playground.Exceptions.testCausedBy(Exceptions.java:29)
    Caused by: java.lang.ArithmeticException: / by zero
        at playground.Exceptions.testCausedBy(Exceptions.java:27)
        ... 22 more
    ```
4. Enlever l'annotation ```@Test``` pour ne pas laisser un test qui échoue dans le projet

### Excercice 4 : les loggers

1. Observez la configuration des logs :
   * dans le fichier ```Loggers.java``` : déclaration de l'attribut ```logger```.
   * dans le fichier ```application.yml``` du répertoire ```/src/test/resources/config``` : la partie ```logging``` à la fin. 
2. Exécutez la méthode ```testLogForLoop``` :
   * observez la sortie standard dans la console

   ```
   un (1)
   deux (2)
   trois (3)
   quatre (4)
   cinq (5)
   six (6)
   ```
   * rien n'apparaît dans le fichier ```test.log``` ! corriger le problème 
3. Dans la méthode ```testLogException```, à la place des commentaires, ajoutez un appel à ```logger``` pour afficher à la fois le message _"Une erreur est survenue"_ ainsi que la pile d'exécution (stacktrace) de l'exception interceptée. Tout cela doit apparaître dans le fichier ```test.log```
4. La méthode ```testLogLevel()``` appelle la méthode ```logLevels()``` qui construit des variables ```element``` prenant les valeurs A1, A2... C3. 
Utiliser la configuration (uniquement) pour afficher la sortie suivante. 

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
