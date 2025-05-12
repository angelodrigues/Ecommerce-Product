package com.product.controller;

import com.product.dto.SupplierDTO;
import com.product.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@Tag(name = "Supplier", description = "Supplier management APIs")
public class SupplierController {
    
    @Autowired
    private SupplierService supplierService;

    @GetMapping
    @Operation(summary = "Get all suppliers", description = "Retrieves a list of all suppliers")
    public List<SupplierDTO> findAll() {
        return supplierService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get supplier by ID", description = "Retrieves a supplier by its ID")
    public ResponseEntity<SupplierDTO> findById(@PathVariable Long id) {
        return supplierService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new supplier", description = "Creates a new supplier with the provided details")
    public ResponseEntity<SupplierDTO> create(@RequestBody SupplierDTO supplierDTO) {
        try {
            return ResponseEntity.ok(supplierService.create(supplierDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a supplier", description = "Updates an existing supplier with the provided details")
    public ResponseEntity<SupplierDTO> update(@PathVariable Long id, @RequestBody SupplierDTO supplierDTO) {
        try {
            return ResponseEntity.ok(supplierService.update(id, supplierDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a supplier", description = "Deletes a supplier by its ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            supplierService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 