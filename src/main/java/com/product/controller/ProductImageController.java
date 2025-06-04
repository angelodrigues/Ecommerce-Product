package com.product.controller;

import com.product.dto.ProductImageDTO;
import com.product.service.ProductImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-images")
@Tag(name = "Product Image", description = "Product image management APIs")
public class ProductImageController {
    
    @Autowired
    private ProductImageService productImageService;

    @GetMapping
    @Operation(summary = "Get all product images", description = "Retrieves a list of all product images")
    public List<ProductImageDTO> findAll() {
        return productImageService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product image by ID", description = "Retrieves a product image by its ID")
    public ResponseEntity<ProductImageDTO> findById(@PathVariable Long id) {
        return productImageService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get product image by product ID", description = "Retrieves a product image by its product ID")
    public ResponseEntity<ProductImageDTO> findByProductId(@PathVariable Long productId) {
        return productImageService.findImageByProductId(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/supplier/{supplierId}")
    @Operation(summary = "Get all product images by supplier ID", description = "Retrieves all product images for a specific supplier")
    public List<ProductImageDTO> findAllBySupplierId(@PathVariable Long supplierId) {
        return productImageService.findAllImagesBySupplierId(supplierId);
    }

    @PostMapping
    @Operation(summary = "Create a new product image", description = "Creates a new product image with the provided details")
    public ResponseEntity<ProductImageDTO> create(@RequestBody ProductImageDTO productImageDTO) {
        try {
            return ResponseEntity.ok(productImageService.create(productImageDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product image", description = "Updates an existing product image with the provided details")
    public ResponseEntity<ProductImageDTO> update(@PathVariable Long id, @RequestBody ProductImageDTO productImageDTO) {
        try {
            return ResponseEntity.ok(productImageService.update(id, productImageDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product image", description = "Deletes a product image by its ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            productImageService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{productId}/image")
    @Operation(summary = "Get image as PNG", description = "Retrieves the image in PNG format by product ID")
    public ResponseEntity<byte[]> getImageAsPng(@PathVariable Long productId) {
        var opt = productImageService.findImageByProductId(productId);        

        try {
            ProductImageDTO imageDTO = opt.orElseThrow(() -> new RuntimeException("Image not found"));
            byte[] imageData = imageDTO.getImageData();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(imageData.length);
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (RuntimeException e) {            
            return ResponseEntity.notFound().build();
        }
    }
} 