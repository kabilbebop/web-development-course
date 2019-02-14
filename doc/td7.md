# Intégrer des données d'un référentiel externe
Notre application est _standalone_ : elle gère ses propres données dans sa base et ne communique avec personne d'autres que les utilisateurs.

Pour l'enrichir, on va intégrer des données externes : les photos de voitures du site [Car Imagery](carimagery.com).

Ce site propose une API décrite [ici](carimagery.com/api.pdf).

Le but est simple : pour chaque voiture de notre site weight-cars, on affichera une image miniature de celle-ci.


