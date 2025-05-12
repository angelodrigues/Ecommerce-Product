package com.product.service;

import com.product.dto.SupplierDTO;
import java.util.List;
import java.util.Optional;

public interface SupplierService {
    List<SupplierDTO> findAll();
    Optional<SupplierDTO> findById(Long id);
    SupplierDTO create(SupplierDTO supplierDTO);
    SupplierDTO update(Long id, SupplierDTO supplierDTO);
    void deleteById(Long id);
} 