package com.product_h2.mapper;

import com.product_h2.dto.ProductDTO;
import com.product_h2.dto.StockDTO;
import com.product_h2.dto.SupplierDTO;
import com.product_h2.entity.Product;
import com.product_h2.entity.Stock;
import com.product_h2.entity.Supplier;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    public SupplierDTO toDTO(Supplier supplier) {
        if (supplier == null) return null;
        
        SupplierDTO dto = new SupplierDTO();
        dto.setId(supplier.getId());
        dto.setName(supplier.getName());
        dto.setCnpj(supplier.getCnpj());
        dto.setEmail(supplier.getEmail());
        dto.setPhone(supplier.getPhone());
        dto.setAddress(supplier.getAddress());
        return dto;
    }

    public Supplier toEntity(SupplierDTO dto) {
        if (dto == null) return null;
        
        Supplier supplier = new Supplier();
        supplier.setId(dto.getId());
        supplier.setName(dto.getName());
        supplier.setCnpj(dto.getCnpj());
        supplier.setEmail(dto.getEmail());
        supplier.setPhone(dto.getPhone());
        supplier.setAddress(dto.getAddress());
        return supplier;
    }

    public ProductDTO toDTO(Product product) {
        if (product == null) return null;
        
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setSku(product.getSku());
        if (product.getSupplier() != null) {
            dto.setSupplierId(product.getSupplier().getId());
            dto.setSupplierName(product.getSupplier().getName());
        }
        return dto;
    }

    public Product toEntity(ProductDTO dto, Supplier supplier) {
        if (dto == null) return null;
        
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setSku(dto.getSku());
        product.setSupplier(supplier);
        return product;
    }

    public StockDTO toDTO(Stock stock) {
        if (stock == null) return null;
        
        StockDTO dto = new StockDTO();
        dto.setId(stock.getId());
        if (stock.getProduct() != null) {
            dto.setProductId(stock.getProduct().getId());
            dto.setProductName(stock.getProduct().getName());
        }
        dto.setQuantity(stock.getQuantity());
        dto.setMinimumQuantity(stock.getMinimumQuantity());
        dto.setLocation(stock.getLocation());
        return dto;
    }

    public Stock toEntity(StockDTO dto, Product product) {
        if (dto == null) return null;
        
        Stock stock = new Stock();
        stock.setId(dto.getId());
        stock.setProduct(product);
        stock.setQuantity(dto.getQuantity());
        stock.setMinimumQuantity(dto.getMinimumQuantity());
        stock.setLocation(dto.getLocation());
        return stock;
    }
} 