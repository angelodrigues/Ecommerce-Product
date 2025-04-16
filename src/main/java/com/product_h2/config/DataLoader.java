package com.product_h2.config;

import com.product_h2.entity.Product;
import com.product_h2.entity.Stock;
import com.product_h2.entity.Supplier;
import com.product_h2.repository.ProductRepository;
import com.product_h2.repository.StockRepository;
import com.product_h2.repository.SupplierRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Arrays;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(
            SupplierRepository supplierRepository,
            ProductRepository productRepository,
            StockRepository stockRepository
    ) {
        return args -> {
            // Criar fornecedores
            Supplier supplier1 = new Supplier();
            supplier1.setName("Fornecedor Eletrônicos");
            supplier1.setCnpj("12345678901234");
            supplier1.setEmail("eletronicos@fornecedor.com");
            supplier1.setPhone("11999887766");
            supplier1.setAddress("Rua da Tecnologia, 123");

            Supplier supplier2 = new Supplier();
            supplier2.setName("Fornecedor Móveis");
            supplier2.setCnpj("98765432109876");
            supplier2.setEmail("moveis@fornecedor.com");
            supplier2.setPhone("11988776655");
            supplier2.setAddress("Avenida dos Móveis, 456");

            supplierRepository.saveAll(Arrays.asList(supplier1, supplier2));

            // Criar produtos
            Product product1 = new Product();
            product1.setName("Smartphone XYZ");
            product1.setDescription("Smartphone última geração");
            product1.setPrice(new BigDecimal("2999.99"));
            product1.setSku("PHONE-001");
            product1.setSupplier(supplier1);

            Product product2 = new Product();
            product2.setName("Notebook ABC");
            product2.setDescription("Notebook de alta performance");
            product2.setPrice(new BigDecimal("4999.99"));
            product2.setSku("NOTE-001");
            product2.setSupplier(supplier1);

            Product product3 = new Product();
            product3.setName("Mesa de Escritório");
            product3.setDescription("Mesa ergonômica para escritório");
            product3.setPrice(new BigDecimal("899.99"));
            product3.setSku("MESA-001");
            product3.setSupplier(supplier2);

            productRepository.saveAll(Arrays.asList(product1, product2, product3));

            // Criar registros de estoque
            Stock stock1 = new Stock();
            stock1.setProduct(product1);
            stock1.setQuantity(50);
            stock1.setMinimumQuantity(10);
            stock1.setLocation("Prateleira A1");

            Stock stock2 = new Stock();
            stock2.setProduct(product2);
            stock2.setQuantity(30);
            stock2.setMinimumQuantity(5);
            stock2.setLocation("Prateleira B2");

            Stock stock3 = new Stock();
            stock3.setProduct(product3);
            stock3.setQuantity(20);
            stock3.setMinimumQuantity(8);
            stock3.setLocation("Estante C3");

            stockRepository.saveAll(Arrays.asList(stock1, stock2, stock3));
        };
    }
} 