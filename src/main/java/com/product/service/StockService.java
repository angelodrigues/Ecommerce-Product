package com.product.service;

import com.product.dto.StockDTO;
import java.util.List;
import java.util.Optional;

public interface StockService {
    List<StockDTO> findAll();
    Optional<StockDTO> findById(Long id);
    StockDTO create(StockDTO stockDTO);
    StockDTO update(Long id, StockDTO stockDTO);
    void deleteById(Long id);
} 