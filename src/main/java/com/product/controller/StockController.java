package com.product.controller;

import com.product.dto.StockDTO;
import com.product.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@Tag(name = "Stock", description = "Stock management APIs")
public class StockController {
    
    @Autowired
    private StockService stockService;

    @GetMapping
    @Operation(summary = "Get all stocks", description = "Retrieves a list of all stocks")
    public List<StockDTO> findAll() {
        return stockService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get stock by ID", description = "Retrieves a stock by its ID")
    public ResponseEntity<StockDTO> findById(@PathVariable Long id) {
        return stockService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new stock", description = "Creates a new stock with the provided details")
    public ResponseEntity<StockDTO> create(@RequestBody StockDTO stockDTO) {
        try {
            return ResponseEntity.ok(stockService.create(stockDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a stock", description = "Updates an existing stock with the provided details")
    public ResponseEntity<StockDTO> update(@PathVariable Long id, @RequestBody StockDTO stockDTO) {
        try {
            return ResponseEntity.ok(stockService.update(id, stockDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a stock", description = "Deletes a stock by its ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            stockService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 