package com.product.service.impl;

import com.product.dto.ProductDTO;
import com.product.entity.Product;
import com.product.entity.Supplier;
import com.product.mapper.EntityMapper;
import com.product.repository.ProductRepository;
import com.product.service.ProductService;
import com.product.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private EntityMapper mapper;

    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDTO> findById(Long id) {
        return productRepository.findById(id)
                .map(mapper::toDTO);
    }

    @Override
    public ProductDTO create(ProductDTO productDTO) {
        return supplierService.findById(productDTO.getSupplierId())
                .map(supplierDTO -> {
                    Supplier supplier = new Supplier();
                    supplier.setId(supplierDTO.getId());
                    Product product = mapper.toEntity(productDTO, supplier);
                    return mapper.toDTO(productRepository.save(product));
                })
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    @Override
    public ProductDTO update(Long id, ProductDTO productDTO) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }

        return supplierService.findById(productDTO.getSupplierId())
                .map(supplierDTO -> {
                    Supplier supplier = new Supplier();
                    supplier.setId(supplierDTO.getId());
                    productDTO.setId(id);
                    Product product = mapper.toEntity(productDTO, supplier);
                    return mapper.toDTO(productRepository.save(product));
                })
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    @Override
    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }
} 