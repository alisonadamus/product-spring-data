package com.alisonadamus.product_spring_data.service;

import com.alisonadamus.product_spring_data.entity.Product;
import com.alisonadamus.product_spring_data.entity.ProductProjectionForList;
import com.alisonadamus.product_spring_data.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> findAll(String searchValue, Long categoryId,
        BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return productRepository.findAll(searchValue, categoryId, minPrice,
            maxPrice, pageable);
    }

    public Page<ProductProjectionForList> findAllForList(Pageable pageable) {
        return productRepository.findAllForList(pageable);
    }

    public Page<ProductProjectionForList> findAllForList(String searchValue, Long categoryId,
        BigDecimal minPrice, BigDecimal maxPrice,
        Pageable pageable) {
        return productRepository.findAllForList(searchValue, categoryId, minPrice,
            maxPrice, pageable);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Optional<Product> findOptionalById(Long id) {
        return productRepository.findById(id);
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
