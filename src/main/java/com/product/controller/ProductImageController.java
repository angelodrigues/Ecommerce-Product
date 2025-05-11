package com.product.controller;

import com.product.dto.ProductImageDTO;
import com.product.entity.Product;
import com.product.entity.ProductImage;
import com.product.entity.Supplier;
import com.product.mapper.EntityMapper;
import com.product.service.ProductImageService;
import com.product.service.ProductService;
import com.product.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products/image")
@Tag(name = "Images", description = "Images management APIs")
public class ProductImageController {
    
    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierService supplierService;
    
    @Autowired
    private EntityMapper mapper;

    @GetMapping
    @Operation(summary = "Get all images", description = "Retrieves a list of all products")
    public List<ProductImageDTO> findAll() {
        return productImageService.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get image by ID", description = "Retrieves a image by its ID")
    public ResponseEntity<ProductImageDTO> findById(@PathVariable Long id) {
        return productImageService.findById(id)
                .map(product -> ResponseEntity.ok(mapper.toDTO(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get product image by product ID", description = "Retrieves the product image associated with the given product ID")
    public ResponseEntity<ProductImageDTO> findImageByProductId(@PathVariable Long productId) {
        return productImageService.findImageByProductId(productId)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/supplier/{supplierId}")
    @Operation(summary = "Get all images by supplier ID", description = "Retrieves all product images associated with a supplier")
    public ResponseEntity<List<ProductImageDTO>> findAllImagesBySupplierId(@PathVariable Long supplierId) {
        List<ProductImageDTO> images = productImageService.findAllImagesBySupplierId(supplierId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

        return images.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(images);
    }

    @PostMapping
    @Operation(summary = "Upload a new product image")
    public ResponseEntity<ProductImageDTO> create(@RequestBody ProductImageDTO dto) {
        Optional<Product> productOpt = Optional.ofNullable(dto.getProductId())
                .flatMap(productService::findById);
        Optional<Supplier> supplierOpt = Optional.ofNullable(dto.getSupplierId())
                .flatMap(supplierService::findById);

        if (dto.getProductId() != null && productOpt.isEmpty())
            return ResponseEntity.badRequest().body(null);

        if (dto.getSupplierId() != null && supplierOpt.isEmpty())
            return ResponseEntity.badRequest().body(null);

        ProductImage entity = mapper.toEntity(dto, productOpt.orElse(null), supplierOpt.orElse(null));
        return ResponseEntity.ok(mapper.toDTO(productImageService.save(entity)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product image")
    public ResponseEntity<ProductImageDTO> update(@PathVariable Long id, @RequestBody ProductImageDTO dto) {
        if (!productImageService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Product> productOpt = Optional.ofNullable(dto.getProductId())
                .flatMap(productService::findById);
        Optional<Supplier> supplierOpt = Optional.ofNullable(dto.getSupplierId())
                .flatMap(supplierService::findById);

        if (dto.getProductId() != null && productOpt.isEmpty())
            return ResponseEntity.badRequest().body(null);

        if (dto.getSupplierId() != null && supplierOpt.isEmpty())
            return ResponseEntity.badRequest().body(null);

        dto.setId(id);
        ProductImage entity = mapper.toEntity(dto, productOpt.orElse(null), supplierOpt.orElse(null));
        return ResponseEntity.ok(mapper.toDTO(productImageService.save(entity)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product image")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!productImageService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        productImageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 