# Rapport de projet de développement mobile

Projet réalisé par Florian Bour et Thomas Gaignard

## Fonctionnalités développées

### Service de requêtes à l'API

La première étape du projet était de 
mettre en place un service de communication avec l'API de cocktails.
Nous avons cherchés à avoir un seul service capable 
de renvoyer toujours le même type (DrinksResponse).
Pour ce faire on utilise l'enum ApiUrls pour définir les différents endpoints
que l'on utilise.

### Activité principale

L'idée était d'avoir une activité principale qui ne contient pas toutes
les pages de navigation donc on a choisit de mettre un emplacement de 
fragment et lorsque l'on clique sur un bouton de navigation, cela instancie
le fragment correspondant.

- **Fragment Recherche**: contient la liste des cocktails reçus par l'API 
suivant la barre de recherche. Lorsque l'on clique dessus,
on obtient le détail du cocktail.

- **Fragment Catégories**: contient la liste de toutes les catégories.
Lorsque l'on clique sur une catégorie, on obtient une liste de cocktails 
de cette catégorie.

- **Fragment Ingrédients**: même principe que pour le fragment catégories
mais avec les ingrédients.

### Page détails

Pour le détail des cocktails, on a choisit de faire une nouvelle activité.
Cette activité récupère l'id du cocktail cliqué grâce à l'intent ayant
généré l'activité qui a définit un id.
Cette page affiche un maximum d'information sur le cocktail reçu par l'API.
Elle affiche un logo différent à côté du nom suivant si le cocktail est 
alcoolisé ou non ou s'il on peut choisir.

### Barre de navigation

La barre de navigation en haut de l'application a été ajoutée plus tard. Elle
a permis plusieurs choses:
- Avoir le nom de la navigation sélectionnée dans la page principale
- Faire une recherche sans utiliser trop de place dans l'UI (nous avons
utilisé les SearchView et les menus). 
Cela a impliqué également de changer le fragment vers celui de recherche
lorsque l'on cliquait sur la recherche dans un autre onglet de navigation
(ingrédients ou catégories). 
- Affichage du bouton de gestion des favoris bien intégré dans l'UI
grâce aux menus.

### Préférences de cocktails

Nous avons ajouté la gestion des favoris en utilisant non pas les 
SharedPreferences comme conseillé dans le sujet mais plutôt des DataStore
car plus récents et efficace.  
Lorsque l'on clique sur le coeur, on ajoute le cocktail dans le datastore
grâce à la classe FavoriteUtils qui gère le DataStore.  
Si l'on reclique dessus, on le supprime des favoris 
avec une UI correspondante.

### Fragment de cocktails aléatoire

## Difficultés rencontrées

- **Thomas**  
Pour ma part le plus difficile a été de bien comprendre comment fonctionne
Android et toutes les intéractions entre les classes notamment les fragments.  
Mais une fois que l'on commence à comprendre, il y a beaucoup de documentation
donc on avance vite et on arrive à créer beaucoup de choses.
- **Florian**  
