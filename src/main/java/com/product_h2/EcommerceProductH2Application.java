package com.product_h2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.product_h2")
@EntityScan("com.product_h2.entity")
@EnableJpaRepositories("com.product_h2.repository")
public class EcommerceProductH2Application {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceProductH2Application.class, args);
    }
} 