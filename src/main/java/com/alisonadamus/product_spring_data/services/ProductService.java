package com.alisonadamus.product_spring_data.services;

import com.alisonadamus.product_spring_data.entities.Product;
import com.alisonadamus.product_spring_data.entities.ProductProjectionForList;
import com.alisonadamus.product_spring_data.repositories.ProductRepository;
import java.math.BigDecimal;
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
        return productRepository.findById(id).orElseThrow();
    }
}
