# TP1 : Microservices - Application Eshop

## Aperçu du Projet
L'application Eshop est un projet basé sur des microservices conçu pour gérer une boutique en ligne. Elle inclut des entités telles que `Produit`, `Categorie`, `Commande` et `Utilisateur`. L'application est développée en Java et suit le modèle de maturité API RESTful de niveau 3, garantissant une architecture robuste et évolutive.

---

## Architecture
Le projet est structuré comme suit :

- **Contrôleurs** : Gèrent les requêtes HTTP et les mappent aux services appropriés.
- **Modèles** : Représentent les entités principales de l'application (par exemple, `Produit`, `Categorie`, etc.).
- **Référentiels** : Fournissent l'accès à la base de données et les opérations CRUD pour les entités.
- **Ressources** : Contiennent les fichiers de configuration, les ressources statiques et les modèles.

L'application est construite à l'aide du framework Spring Boot, qui simplifie le développement d'API RESTful et de microservices.

---

## Dépendances
Le projet utilise les dépendances suivantes :

- **Spring Boot Starter Web** : Pour construire des services web RESTful.
- **Spring Boot Starter Data JPA** : Pour les interactions avec la base de données via JPA.
- **Base de données H2** : Une base de données en mémoire pour le développement et les tests.
- **Springdoc OpenAPI** : Pour générer la documentation de l'API (Swagger UI et Redoc).

Les dépendances sont gérées via Maven, et le fichier `pom.xml` contient toutes les configurations nécessaires.

---

## Entités
L'application inclut les entités suivantes :

1. **Produit** : Représente un produit dans la boutique.
2. **Categorie** : Représente une catégorie à laquelle appartiennent les produits.
3. **Commande** : Représente une commande client.
4. **Utilisateur** : Représente un utilisateur de l'application.
5. **CommandeProduit** : Représente la relation entre `Commande` et `Produit` (relation plusieurs-à-plusieurs).

Chaque entité est mappée à une table de la base de données à l'aide des annotations JPA.

---

## API RESTful - Niveau 3
L'application respecte le modèle de maturité API RESTful de niveau 3, qui inclut :

1. **URIs basées sur les ressources** : Chaque entité est exposée comme une ressource avec un URI unique.
   - Exemple : `/api/produits`, `/api/categories`, `/api/commandes`, `/api/utilisateurs`

2. **Méthodes HTTP** :
   - `GET` : Récupérer des ressources.
   - `POST` : Créer de nouvelles ressources.
   - `PUT` : Mettre à jour des ressources existantes.
   - `DELETE` : Supprimer des ressources.

3. **HATEOAS (Hypermedia as the Engine of Application State)** :
   - Les réponses incluent des liens vers des ressources et actions associées.
   - Exemple :
     ```json
     {
       "id": 1,
       "name": "Produit A",
       "_links": {
         "self": { "href": "/api/produits/1" },
         "category": { "href": "/api/categories/1" }
       }
     }
     ```

4. **Négociation de contenu** :
   - Prend en charge les formats JSON et XML.

---

## Documentation de l'API
L'application inclut une documentation complète de l'API :

1. **Swagger UI** :
   - Accessible à `/swagger-ui.html`.
   - Fournit une interface interactive pour explorer et tester l'API.

2. **Redoc** :
   - Accessible à `/static/redoc.html`.
   - Offre une interface de documentation de l'API propre et conviviale.

3. **Collection Postman** :
   - Une collection Postman est fournie pour tester les points de terminaison de l'API.
   - Inclut toutes les opérations CRUD pour chaque entité.

---

## Comment Exécuter le Projet
1. Clonez le dépôt.
2. Naviguez dans le répertoire du projet.
3. Exécutez l'application à l'aide de Maven :
   ```bash
   ./mvnw spring-boot:run
   ```
4. Accédez à la documentation de l'API :
   - Swagger UI : [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
   - Redoc : [http://localhost:8080/static/redoc.html](http://localhost:8080/static/redoc.html)

---

## Conclusion
Ce projet démontre l'implémentation d'une architecture basée sur des microservices avec une API RESTful de niveau 3. Il inclut une documentation complète et des outils pour tester et explorer l'API. Le dépôt est prêt pour soumission.