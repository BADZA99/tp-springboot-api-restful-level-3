package com.example.userservice.controllers;

import com.example.userservice.models.User;
import com.example.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        System.out.println("[DEBUG] registerUser called with login: " + user.getLogin());
        Optional<User> existingUser = userService.findByLogin(user.getLogin());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Login already exists");
        }
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/auth/token")
    public ResponseEntity<?> authenticateUser(@RequestBody User loginRequest) {
        Optional<User> userOpt = userService.findByLogin(loginRequest.getLogin());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of(
                            "statut", "error",
                            "code", 401,
                            "message", "Invalid login or password"
                    )
            );
        }
        User user = userOpt.get();
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of(
                            "statut", "error",
                            "code", 401,
                            "message", "Invalid login or password"
                    )
            );
        }
        // Generate a fake token
        String token = UUID.randomUUID().toString();
        return ResponseEntity.ok(
                Map.of(
                        "statut", "success",
                        "code", 200,
                        "message", "Authentication successful",
                        "token", token
                )
        );
    }

    @PostMapping("/auth/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of(
                            "statut", "error",
                            "code", 401,
                            "message", "Missing or invalid Authorization header"
                    )
            );
        }
        // Generate a new fake token
        String newToken = UUID.randomUUID().toString();
        return ResponseEntity.ok(
                Map.of(
                        "statut", "success",
                        "code", 200,
                        "message", "Token refreshed successfully",
                        "token", newToken
                )
        );
    }
}