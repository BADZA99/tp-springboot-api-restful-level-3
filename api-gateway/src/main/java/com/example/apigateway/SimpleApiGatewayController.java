package com.example.apigateway;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class SimpleApiGatewayController {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final Logger logger = LoggerFactory.getLogger(SimpleApiGatewayController.class);

    @GetMapping("/categories")
    public ResponseEntity<?> getCategories() {
        String url = "http://localhost:8081/api/categories"; // categories service URL
        return restTemplate.getForEntity(url, String.class);
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProducts() {
        String url = "http://localhost:8082/api/products"; // produitsq service URL
        return restTemplate.getForEntity(url, String.class);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> user) {
        String url = "http://localhost:8082/auth/register"; // user-service URL
        return restTemplate.postForEntity(url, user, String.class);
    }

    @PostMapping("/auth/token")
    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> credentials) {
        String url = "http://localhost:8082/auth/token"; // user-service URL
        return restTemplate.postForEntity(url, credentials, String.class);
    }

    @GetMapping("/auth/me")
    public ResponseEntity<?> getAuthenticatedUser(@RequestHeader("Authorization") String authorizationHeader) {
        String url = "http://localhost:8082/auth/me"; // user-service URL
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    @PostMapping("/auth/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authorizationHeader) {
        String url = "http://localhost:8082/auth/refresh-token"; // user-service URL
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        logger.info("Forwarding /auth/refresh-token request with Authorization header: {}", authorizationHeader);

        try {
            return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            logger.error("Error while forwarding /auth/refresh-token request: {}", e.getMessage());
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
