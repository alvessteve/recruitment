## API endpoints

Suivant le cahier des charges (CdC), voici une proposition d'API pour le projet :

L'idée est de pouvoir proposer une API REST qui respecte au mieux le niveau 3 du modèle de Richardson (HATEOAS)

### Profils

Nous avons 2 types de profils pour l'authorisation: CLIENT et RESTAURANT_OWNER

### Endpoints

- GET /api/v1/restaurant/{restaurant_id}/meal?page={page}&size={size}
  - Description: Récupérer la liste des repas d'un restaurant
  - Authorisation: CLIENT
  - Réponse: Liste de repas paginée (par défaut 10 repas par page)
  - Example de payload:

```json
{
  "meals": [
    {
      "id": "uuid",
      "name": "Pizza",
      "price": 12.99,
      "_links": {
        "self": "/api/v1/restaurant/{restaurant_id}/meal/{meal_id}",
        "order": "/api/v1/restaurant/{restaurant_id}/order"
      }
    }
  ],
  "_links": {
    "self": "/api/v1/restaurant/{restaurant_id}/meal?page=1",
    "next": "/api/v1/restaurant/{restaurant_id}/meal?page=2"
  }
}
```

- POST /api/v1/restaurant/{restaurant_id}/order
  - Description: Créer une commande pour un restaurant
  - Authorisation: CLIENT
  - Corps de la requête: Détails de la commande (repas, quantités, etc.)
  - Réponse: 201 Created

- GET /api/v1/restaurant/{restaurant_id}/order?page={page}&size={size}
  - Description: Récupérer la liste des commandes d'un restaurant
  - Authorisation: RESTAURANT_OWNER ou CLIENT (restreint aux commandes passées par le client)
  - Réponse: Liste de commandes paginée (par défaut 20 commandes par page)
  - Example de payload:

```json
{
  "orders": [
    {
      "id": "uuid",
      "customer": {
        "id": "uuid",
        "first_name": "John",
        "last_name": "Doe"
      },
      "meals": [
        {
          "id": "uuid",
          "name": "Pizza",
          "_links": {
            "self": "/api/v1/restaurant/{restaurant_id}/meal/{meal_id}"
          }
        }
      ],
      "order_price": 25.99,
      "order_date": "2023-10-01T12:00:00Z",
      "_links": {
        "self": "/api/v1/restaurant/{restaurant_id}/order/{order_id}",
        "customer": "/api/v1/customer/{customer_id}"
      }
    }
  ],
  "_links": {
    "self": "/api/v1/restaurant/{restaurant_id}/order?page=1",
    "next": "/api/v1/restaurant/{restaurant_id}/order?page=2"
  }
}
```

- GET /api/v1/client/{client_id}/order?page={page}&size={size}
  - Description: Récupérer la liste des commandes d'un client
  - Authorisation: CLIENT (et restriction suivant l'id du client côté application pour ne pas voir les commandes des autres clients)
  - Réponse: Liste de commandes paginée (par défaut 20 commandes par page)
  - Example de payload:

```json
{
  "orders": [
    {
      "id": "uuid",
      "meals": [
        {
          "id": "uuid",
          "name": "Pizza",
          "_links": {
            "self": "/api/v1/restaurant/{restaurant_id}/meal/{meal_id}"
          }
        }
      ],
      "order_price": 25.99,
      "order_date": "2023-10-01T12:00:00Z",
      "_links": {
        "self": "/api/v1/restaurant/{restaurant_id}/order/{order_id}",
        "customer": "/api/v1/customer/{customer_id}"
      }
    }
  ],
  "_links": {
    "self": "/api/v1/client/{client_id}/order?page=1",
    "next": "/api/v1/client/{client_id}/order?page=2"
  }
}
```

- POST /api/v1/restaurant/{restaurant_id}/meal
  - Description: Ajouter un repas à un restaurant
  - Authorisation: RESTAURANT_OWNER
  - Corps de la requête: Détails du repas (nom, prix, etc.)
  - Réponse: 201 Created / 409 Conflict (si le repas existe déjà)

