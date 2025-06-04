package com.product.service.impl;

import com.product.dto.ProductImageDTO;
import com.product.entity.Product;
import com.product.entity.ProductImage;
import com.product.entity.Supplier;
import com.product.mapper.EntityMapper;
import com.product.repository.ProductImageRepository;
import com.product.service.ProductImageService;
import com.product.service.ProductService;
import com.product.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private EntityMapper mapper;

    @Override
    public List<ProductImageDTO> findAll() {
        return productImageRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductImageDTO> findById(Long id) {
        return productImageRepository.findById(id)
                .map(mapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductImageDTO> findImageByProductId(Long productId) {
        return productImageRepository.findFirstByProductId(productId)
                .map(mapper::toDTO);
    }

    @Override
    public List<ProductImageDTO> findAllImagesBySupplierId(Long supplierId) {
        return productImageRepository.findAllBySupplierId(supplierId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductImageDTO create(ProductImageDTO productImageDTO) {
        Product product = null;
        Supplier supplier = null;

        if (productImageDTO.getProductId() != null) {
            product = productService.findById(productImageDTO.getProductId())
                    .map(productDTO -> {
                        Product p = new Product();
                        p.setId(productDTO.getId());
                        return p;
                    })
                    .orElseThrow(() -> new RuntimeException("Product not found"));
        }

        if (productImageDTO.getSupplierId() != null) {
            supplier = supplierService.findById(productImageDTO.getSupplierId())
                    .map(supplierDTO -> {
                        Supplier s = new Supplier();
                        s.setId(supplierDTO.getId());
                        return s;
                    })
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
        }

        ProductImage productImage = mapper.toEntity(productImageDTO, product, supplier);
        return mapper.toDTO(productImageRepository.save(productImage));
    }

    @Override
    public ProductImageDTO update(Long id, ProductImageDTO productImageDTO) {
        if (!productImageRepository.existsById(id)) {
            throw new RuntimeException("Product image not found");
        }

        Product product = null;
        Supplier supplier = null;

        if (productImageDTO.getProductId() != null) {
            product = productService.findById(productImageDTO.getProductId())
                    .map(productDTO -> {
                        Product p = new Product();
                        p.setId(productDTO.getId());
                        return p;
                    })
                    .orElseThrow(() -> new RuntimeException("Product not found"));
        }

        if (productImageDTO.getSupplierId() != null) {
            supplier = supplierService.findById(productImageDTO.getSupplierId())
                    .map(supplierDTO -> {
                        Supplier s = new Supplier();
                        s.setId(supplierDTO.getId());
                        return s;
                    })
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
        }

        productImageDTO.setId(id);
        ProductImage productImage = mapper.toEntity(productImageDTO, product, supplier);
        return mapper.toDTO(productImageRepository.save(productImage));
    }

    @Override
    public void deleteById(Long id) {
        if (!productImageRepository.existsById(id)) {
            throw new RuntimeException("Product image not found");
        }
        productImageRepository.deleteById(id);
    }
} 