package com.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.product")
@EntityScan("com.product.entity")
@EnableJpaRepositories("com.product.repository")
public class EcommerceProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceProductApplication.class, args);
    }
} 