package com.product_h2.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String sku;
    private Long supplierId;
    private String supplierName;
} 