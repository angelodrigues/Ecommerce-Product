package com.product.service.impl;

import com.product.dto.SupplierDTO;
import com.product.entity.Supplier;
import com.product.mapper.EntityMapper;
import com.product.repository.SupplierRepository;
import com.product.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private EntityMapper mapper;

    @Override
    public List<SupplierDTO> findAll() {
        return supplierRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SupplierDTO> findById(Long id) {
        return supplierRepository.findById(id)
                .map(mapper::toDTO);
    }

    @Override
    public SupplierDTO create(SupplierDTO supplierDTO) {
        Supplier supplier = mapper.toEntity(supplierDTO);
        return mapper.toDTO(supplierRepository.save(supplier));
    }

    @Override
    public SupplierDTO update(Long id, SupplierDTO supplierDTO) {
        if (!supplierRepository.existsById(id)) {
            throw new RuntimeException("Supplier not found");
        }
        supplierDTO.setId(id);
        Supplier supplier = mapper.toEntity(supplierDTO);
        return mapper.toDTO(supplierRepository.save(supplier));
    }

    @Override
    public void deleteById(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new RuntimeException("Supplier not found");
        }
        supplierRepository.deleteById(id);
    }
} 