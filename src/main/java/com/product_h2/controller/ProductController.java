package com.product_h2.controller;

import com.product_h2.dto.ProductDTO;
import com.product_h2.entity.Product;
import com.product_h2.mapper.EntityMapper;
import com.product_h2.service.ProductService;
import com.product_h2.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product", description = "Product management APIs")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private SupplierService supplierService;
    
    @Autowired
    private EntityMapper mapper;

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieves a list of all products")
    public List<ProductDTO> findAll() {
        return productService.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieves a product by its ID")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        return productService.findById(id)
                .map(product -> ResponseEntity.ok(mapper.toDTO(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new product", description = "Creates a new product with the provided details")
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO productDTO) {
        return supplierService.findById(productDTO.getSupplierId())
                .map(supplier -> {
                    Product product = mapper.toEntity(productDTO, supplier);
                    return ResponseEntity.ok(mapper.toDTO(productService.save(product)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product", description = "Updates an existing product with the provided details")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        if (!productService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        return supplierService.findById(productDTO.getSupplierId())
                .map(supplier -> {
                    productDTO.setId(id);
                    Product product = mapper.toEntity(productDTO, supplier);
                    return ResponseEntity.ok(mapper.toDTO(productService.save(product)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product", description = "Deletes a product by its ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!productService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 