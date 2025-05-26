# Architecture Microservices avec Spring Boot

L'architecture microservices est une approche de conception logicielle où une application est décomposée en plusieurs petits services indépendants. Chaque service est responsable d'une fonctionnalité spécifique et communique avec les autres via des API. Voici les principaux composants d'une architecture microservices avec Spring Boot :

---

## 1. **Les Services**
Un service dans une architecture microservices est une application Spring Boot autonome qui gère une fonctionnalité spécifique. Par exemple :
- **UserService** : Gère les utilisateurs (inscription, authentification, etc.).
- **OrderService** : Gère les commandes.
- **InventoryService** : Gère les stocks.

Chaque service a sa propre base de données (principe de séparation des données) et communique avec d'autres services via des appels REST ou des messages asynchrones (RabbitMQ, Kafka, etc.).

---

## 2. **La Gateway**
### Rôle
La Gateway agit comme un point d'entrée unique pour tous les clients (applications web, mobiles, etc.). Elle sert de proxy pour acheminer les requêtes vers les services appropriés. Elle peut également gérer :
- L'authentification et l'autorisation.
- La journalisation et le suivi des requêtes.
- La gestion des erreurs globales.
- La limitation de débit (rate limiting).

### Exemple
Imaginons que nous ayons les services suivants :
- **UserService** : Accessible via `/users`.
- **OrderService** : Accessible via `/orders`.

La Gateway expose une API unifiée :
- `/api/users` redirige vers le service UserService.
- `/api/orders` redirige vers le service OrderService.

**Code Exemple avec Spring Cloud Gateway :**
```java
// filepath: src/main/resources/application.yml
spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: http://localhost:8081
          predicates:
            - Path=/api/users/**
          filters:
            - RewritePath=/api/users/(?<segment>.*), /${segment}
        - id: order-service
          uri: http://localhost:8082
          predicates:
            - Path=/api/orders/**
          filters:
            - RewritePath=/api/orders/(?<segment>.*), /${segment}