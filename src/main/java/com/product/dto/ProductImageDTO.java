package com.product.dto;

import lombok.Data;

@Data
public class ProductImageDTO {
    private Long id;
    private byte[] imageData;
    private boolean isLogo;
    private Long productId;
    private Long supplierId;
}