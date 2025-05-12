package com.product.service.impl;

import com.product.dto.StockDTO;
import com.product.entity.Product;
import com.product.entity.Stock;
import com.product.mapper.EntityMapper;
import com.product.repository.StockRepository;
import com.product.service.ProductService;
import com.product.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private EntityMapper mapper;

    @Override
    public List<StockDTO> findAll() {
        return stockRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<StockDTO> findById(Long id) {
        return stockRepository.findById(id)
                .map(mapper::toDTO);
    }

    @Override
    public StockDTO create(StockDTO stockDTO) {
        return productService.findById(stockDTO.getProductId())
                .map(productDTO -> {
                    Product product = new Product();
                    product.setId(productDTO.getId());
                    Stock stock = mapper.toEntity(stockDTO, product);
                    return mapper.toDTO(stockRepository.save(stock));
                })
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public StockDTO update(Long id, StockDTO stockDTO) {
        if (!stockRepository.existsById(id)) {
            throw new RuntimeException("Stock not found");
        }

        return productService.findById(stockDTO.getProductId())
                .map(productDTO -> {
                    Product product = new Product();
                    product.setId(productDTO.getId());
                    stockDTO.setId(id);
                    Stock stock = mapper.toEntity(stockDTO, product);
                    return mapper.toDTO(stockRepository.save(stock));
                })
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public void deleteById(Long id) {
        if (!stockRepository.existsById(id)) {
            throw new RuntimeException("Stock not found");
        }
        stockRepository.deleteById(id);
    }
} 