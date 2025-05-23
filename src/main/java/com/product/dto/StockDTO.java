package com.product.dto;

import lombok.Data;

@Data
public class StockDTO {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Integer minimumQuantity;
    private String location;
} 