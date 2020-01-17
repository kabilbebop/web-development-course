# TD4

Intégrer les web components W3C dans une application ReactJS.

## Initialisation du projet ReactJS
On va initialiser une application from scratch dans un nouveau répertoire.
Se positionner dans le répertoire parent du projet actuel et lancer :

`npx create-react-app sportscars`

⇒ cela va générer le code de l'application d'exemple.

Se placer dans le nouveau répertoire et lancer :

`npm run start` 

⇒ l’app d’exemple s’affiche bien

## Migration des fichiers web component

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


## Revoir les imports

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

## Migration de index.html et index.js vers App.js

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
