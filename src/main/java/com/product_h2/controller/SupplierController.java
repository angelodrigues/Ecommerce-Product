package com.product_h2.controller;

import com.product_h2.dto.SupplierDTO;
import com.product_h2.entity.Supplier;
import com.product_h2.mapper.EntityMapper;
import com.product_h2.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/suppliers")
@Tag(name = "Supplier", description = "Supplier management APIs")
public class SupplierController {
    
    @Autowired
    private SupplierService supplierService;
    
    @Autowired
    private EntityMapper mapper;

    @GetMapping
    @Operation(summary = "Get all suppliers", description = "Retrieves a list of all suppliers")
    public List<SupplierDTO> findAll() {
        return supplierService.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get supplier by ID", description = "Retrieves a supplier by their ID")
    public ResponseEntity<SupplierDTO> findById(@PathVariable Long id) {
        return supplierService.findById(id)
                .map(supplier -> ResponseEntity.ok(mapper.toDTO(supplier)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new supplier", description = "Creates a new supplier with the provided details")
    public SupplierDTO create(@RequestBody SupplierDTO supplierDTO) {
        Supplier supplier = mapper.toEntity(supplierDTO);
        return mapper.toDTO(supplierService.save(supplier));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a supplier", description = "Updates an existing supplier with the provided details")
    public ResponseEntity<SupplierDTO> update(@PathVariable Long id, @RequestBody SupplierDTO supplierDTO) {
        if (!supplierService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        supplierDTO.setId(id);
        Supplier supplier = mapper.toEntity(supplierDTO);
        return ResponseEntity.ok(mapper.toDTO(supplierService.save(supplier)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a supplier", description = "Deletes a supplier by their ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!supplierService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        supplierService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 