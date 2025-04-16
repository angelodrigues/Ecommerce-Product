package com.product_h2.dto;

import lombok.Data;

@Data
public class SupplierDTO {
    private Long id;
    private String name;
    private String cnpj;
    private String email;
    private String phone;
    private String address;
} 