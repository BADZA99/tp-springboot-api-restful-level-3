package com.example.categorieservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CategorieServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CategorieServiceApplication.class, args);
    }
}
