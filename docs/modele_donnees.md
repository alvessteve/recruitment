# Modèle de Données - Plateforme de Restauration

## Remarques générales

- Ce n'est pas le format attendu et je m'en excuse mais pour moi c'est le moyen le plus efficient que j'avais pour faire ce schéma.
Néanmoins, mes choix ont été documenté au besoin et les informations contenues dans un modèle relationnel sont présents (entités, cardinalités etc...).
- Du fait de la phrase "Voir les commandes passées pour **mon** restaurant" au sujet du restaurateur, j'en ai déduit
qu'un restaurateur ne gérait qu'un seul restaurant. Ainsi pour une chaine, chaque restaurant aurait un restaurateur différent.
- DB choisie: postgresql

## Modèle de données

### 1. Table CUSTOMER
| Colonne | Type | Contraintes |
|---------|------|------------|
| id | UUID | PRIMARY KEY, DEFAULT uuid_generate_v7() |
| first_name | VARCHAR(100) | NOT NULL |
| last_name | VARCHAR(100) | NOT NULL |
| type | VARCHAR(20) | NOT NULL, CHECK (type IN (CHILD, STUDENT, OTHER)) |
| created_at | TIMESTAMP WITH TIME ZONE | NOT NULL |

### 2. Table RESTAURANT
| Colonne | Type | Contraintes |
|---------|------|-------------|
| id | UUID | PRIMARY KEY, DEFAULT uuid_generate_v7() |
| name | VARCHAR(100) | NOT NULL |
| owner_id | UUID | FOREIGN KEY → RESTAURANT_OWNER(id) |
| created_at | TIMESTAMP WITH TIME ZONE | NOT NULL |

### 3. Table RESTAURANT_OWNER
| Colonne | Type | Contraintes |
|---------|------|-------------|
| id | UUID | PRIMARY KEY, DEFAULT uuid_generate_v7() |
| first_name | VARCHAR(100) | NOT NULL |
| last_name | VARCHAR(100) | NOT NULL |
| created_at | TIMESTAMP WITH TIME ZONE | NOT NULL |

### 4. Table MEAL
| Colonne | Type | Contraintes |
|---------|------|-------------|
| id | UUID | PRIMARY KEY, DEFAULT uuid_generate_v7() |
| name | VARCHAR(100) | NOT NULL |
| price | DECIMAL(10,2) | NOT NULL |
| restaurant_id | UUID | FOREIGN KEY → RESTAURANT(id) |
| created_at | TIMESTAMP WITH TIME ZONE | NOT NULL |

### 5. Table ORDER
| Colonne | Type | Contraintes |
|---------|------|-------------|
| id | UUID | PRIMARY KEY, DEFAULT uuid_generate_v7() |
| customer_id | UUID | FOREIGN KEY → CUSTOMER(id) |
| restaurant_id | UUID | FOREIGN KEY → RESTAURANT(id) |
| order_date | TIMESTAMP WITH TIME ZONE | NOT NULL |
| price | DECIMAL(10,2) | NOT NULL |

### 6. Table ORDER_MEAL

Il s'agit d'une table de jointure entre les tables ORDER et MEAL, permettant de gérer les repas commandés dans une commande.
On a une cardinalité N:N entre ces deux tables, car une commande peut contenir plusieurs repas et un repas peut être commandé dans plusieurs commandes.

| Colonne | Type | Contraintes |
|---------|------|-------------|
| id | UUID | PRIMARY KEY (order_id, meal_id) |
| order_id | UUID | FOREIGN KEY → ORDER(id) |
| meal_id | UUID | FOREIGN KEY → MEAL(id) |
| quantity | INT | NOT NULL |

### 7. Table DISCOUNT

- Du fait de la nature très mouvantes/libres des promotions (on peut jouer sur différents critères), cela ne faisait pas 
de sens pour moi d'ajouter des colonnes. Ainsi je suis parti sur du jsonb
- D'après le Cdc, il n'y a pas de notion de date de début/fin pour les promotions. Donc je ne l'ai pas ajouté.
- De plus, les promotions ne semblent pas liées à un restaurant en particulier, mais plutôt à un type de client et 
au nombre de commandes passées dans un restaurant. Ainsi je n'ai pas ajouté de jointure.

| Colonne | Type | Contraintes |
|---------|------|-------------|
| id | UUID | PRIMARY KEY, DEFAULT uuid_generate_v7() |
| name | VARCHAR(100) | NOT NULL |
| discount_detail | JSONB | NOT NULL |
| created_at | TIMESTAMP WITH TIME ZONE | NOT NULL |
