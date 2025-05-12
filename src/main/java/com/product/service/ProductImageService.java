package com.product.service;

import com.product.dto.ProductImageDTO;
import java.util.List;
import java.util.Optional;

public interface ProductImageService {
    List<ProductImageDTO> findAll();
    Optional<ProductImageDTO> findById(Long id);
    Optional<ProductImageDTO> findImageByProductId(Long productId);
    List<ProductImageDTO> findAllImagesBySupplierId(Long supplierId);
    ProductImageDTO create(ProductImageDTO productImageDTO);
    ProductImageDTO update(Long id, ProductImageDTO productImageDTO);
    void deleteById(Long id);
}
