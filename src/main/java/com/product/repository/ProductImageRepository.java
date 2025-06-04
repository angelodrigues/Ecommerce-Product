package com.product.repository;

import com.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    @Query("SELECT pi FROM ProductImage pi WHERE pi.product.id = :productId")
    Optional<ProductImage> findFirstByProductId(@Param("productId") Long productId);

    List<ProductImage> findAllBySupplierId(Long supplierId);
}
