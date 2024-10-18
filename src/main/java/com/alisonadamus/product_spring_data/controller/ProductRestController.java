package com.alisonadamus.product_spring_data.controller;

import com.alisonadamus.product_spring_data.entity.Product;
import com.alisonadamus.product_spring_data.service.ProductService;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String searchValue,
        @RequestParam(required = false) Long categoryId,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "ASC") String orderBy,
        @RequestParam(defaultValue = "0") BigDecimal minPrice,
        @RequestParam(defaultValue = "100") BigDecimal maxPrice) {

        Sort.Direction direction =
            orderBy.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        List<Product> products = productService.findAll(searchValue, categoryId, minPrice, maxPrice,
            pageable).getContent();

        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
        }
        return ResponseEntity.ok(products); //200
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id) {
        return productService.findOptionalById(id)
            .map(ResponseEntity::ok) // 200
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // 404
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(@RequestBody Product product) {
        if (product.getId() != null) {
            return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).build(); // 451
        }
        productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201
    }

    @PutMapping
    public ResponseEntity<Void> updateProduct(@RequestBody Product product) {
        if (product.getId() == null) {
            return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).build(); // 451
        }
        productService.save(product);
        return ResponseEntity.ok().build(); // 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
        } else {
            productService.deleteById(id);
            return ResponseEntity.noContent().build(); // 204
        }
    }
}
