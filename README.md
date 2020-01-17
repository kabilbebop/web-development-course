# TD4

Ce TD est composé en 2 parties :
1. Intégrer les web components W3C dans l'application ReactJS.
2. Ré-écrire ces composants en composants ReactJS.

## Intégration des web components W3C dans une application ReactJS.

### Initialisation du projet ReactJS
On va initialiser une application from scratch dans un nouveau répertoire.
Se positionner dans le répertoire parent du projet actuel et lancer :

`npx create-react-app sportscars`

⇒ cela va générer le code de l'application d'exemple.

Se placer dans le nouveau répertoire et lancer :

`npm run start` 

⇒ l’app d’exemple s’affiche bien

### Migration des fichiers web component

Pour migrer progressivement, on va séparer le code de nos web components actuels dans les répertoires _public_ et _src_ : 

- public
    - hangar.jpg
    - logo.jpg
    - components
        - header
            - header.html
            - header.css
        - model
            - model.html
            - model.css
            - car
                - car.html
                - car.css
- src
    - index.css
    - theme.css
    - components
        - header
            - header.js
        - model
            - model.js
            - car
                - car.js


### Revoir les imports

React va packager pour nous tous les fichiers `.js` et `.css`.
Pour que cela fonctionne il faut que les chemins d'import soient relatifs.

Revoir tous les imports de _util.js_ :

```
import getTemplate from '/components/util.js';
```

Devient :

```
import getTemplate from '../util.js';
ou
import getTemplate from '../../util.js';

```

Revoir les imports css :

```
@import '/theme.css';
```

Devient :

```
@import '../../theme.css';
ou
@import '../../../theme.css';

```

### Migration de index.html et index.js vers App.js

Dans `App.js`, ajouter le contenu de `<body>` depuis `index.html` et le contenu de `index.js` :

```
    import HeaderComponent from './components/header/header';
    ...
    <fonctions index.js>
    function App() {
        useEffect(() => {
            <contenu de document.addEventListener('DOMContentLoaded'...) dans index.js>
        });
        return (<contenu de body>);
    }
```

Remplacer `class="..."` par `className="..."` (spécificité React).

Constater que l'application s’affiche bien dans la page et qu'il n'y a pas d'erreur javascript.

## Ré-écriture des web components en composants ReactJS

### _Model_
Créer le répertoire `src/components/model` et y déplacer `model.css` et `model.js`.
Modifier `model.js` :
- pour informer React qu'il s'agit d'un composant, ajouter la ligne suivante :
    ```
        import React from 'react';
    ```
- tout ce qui concerne les web components devient inutile : `HTMLElement`, `observedAttributes`, `attachShadow`, `shadowRoot`, `attributeChangedCallback`
- étant donné que le code construit le template, on ne doit plus utiliser `getTemplate`, `querySelector` ni `appendChild`
- remplacer la classe par une fonction comme dans `App.js`
- recopier et adapter le code de `model.html` dans le retour de cette fonction
- importer les styles comme un fichier Javascript :
    ```
        import './model.css';
    ```
- ajouter le composant _Model_ au composant _App_ après l'avoir importé dans `App.js`:
    ```
        import ModelComponent from './components/model/model';
    ```

### _Car_
Migrer le composants _car_ en utilisant le même principe.

## Ajout d'un état pour stocker les données
ReactJS propose de stocker les données de l'application dans un espace mémoire spécial appelé le _state_

Dans le cadre de notre application, nous allons y stocker les données retournées par l'API.

Dans `App.js`, déplacer `ReactDOM.render()` dans le retour d’appel de l’API d’initialisation.
Passer le résultat au composant _App_ comme ceci `<App data={data} />`
Dans `App.js`, les valeurs sont disponibles en paramètre : par convention, on appelle ces attributs “props”.
Modifier l'instruction de retour comme ceci pour vérifier que les données sont bien là :
```
 return (
    <div className="App">
      <header-component></header-component>
      { props.data[0].name }
    </div>
  );
```
Passer le reste des données en utilisant le même principe.
 	
