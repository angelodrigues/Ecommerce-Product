package com.product.repository;

import com.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    Optional<ProductImage> findFirstByProductId(Long productId);

    List<ProductImage> findAllBySupplierId(Long supplierId);
}
