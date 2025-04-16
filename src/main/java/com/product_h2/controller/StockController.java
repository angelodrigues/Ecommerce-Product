package com.product_h2.controller;

import com.product_h2.dto.StockDTO;
import com.product_h2.entity.Stock;
import com.product_h2.mapper.EntityMapper;
import com.product_h2.service.ProductService;
import com.product_h2.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stocks")
@Tag(name = "Stock", description = "Stock management APIs")
public class StockController {
    
    @Autowired
    private StockService stockService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private EntityMapper mapper;

    @GetMapping
    @Operation(summary = "Get all stock entries", description = "Retrieves a list of all stock entries")
    public List<StockDTO> findAll() {
        return stockService.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get stock by ID", description = "Retrieves a stock entry by its ID")
    public ResponseEntity<StockDTO> findById(@PathVariable Long id) {
        return stockService.findById(id)
                .map(stock -> ResponseEntity.ok(mapper.toDTO(stock)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new stock entry", description = "Creates a new stock entry with the provided details")
    public ResponseEntity<StockDTO> create(@RequestBody StockDTO stockDTO) {
        return productService.findById(stockDTO.getProductId())
                .map(product -> {
                    Stock stock = mapper.toEntity(stockDTO, product);
                    return ResponseEntity.ok(mapper.toDTO(stockService.save(stock)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a stock entry", description = "Updates an existing stock entry with the provided details")
    public ResponseEntity<StockDTO> update(@PathVariable Long id, @RequestBody StockDTO stockDTO) {
        if (!stockService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        return productService.findById(stockDTO.getProductId())
                .map(product -> {
                    stockDTO.setId(id);
                    Stock stock = mapper.toEntity(stockDTO, product);
                    return ResponseEntity.ok(mapper.toDTO(stockService.save(stock)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a stock entry", description = "Deletes a stock entry by its ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!stockService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        stockService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 