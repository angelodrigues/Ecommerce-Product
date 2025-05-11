package com.product.service;

import com.product.entity.ProductImage;
import com.product.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImageService {
    @Autowired
    private ProductImageRepository productImageRepository;

    public List<ProductImage> findAll() {
        return productImageRepository.findAll();
    }

    public Optional<ProductImage> findById(Long id) {
        return productImageRepository.findById(id);
    }

    public Optional<ProductImage> findImageByProductId(Long productId) {
        return productImageRepository.findFirstByProductId(productId);
    }

    public List<ProductImage> findAllImagesBySupplierId(Long supplierId) {
        return productImageRepository.findAllBySupplierId(supplierId);
    }

    public ProductImage save(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }

    public void deleteById(Long id) {
        productImageRepository.deleteById(id);
    }
}
