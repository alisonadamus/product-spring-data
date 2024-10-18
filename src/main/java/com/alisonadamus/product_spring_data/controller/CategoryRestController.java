package com.alisonadamus.product_spring_data.controller;

import com.alisonadamus.product_spring_data.entity.Category;
import com.alisonadamus.product_spring_data.service.CategoryService;
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
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getCategories(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(required = false) String searchValue,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "ASC") String orderBy) {

        Sort.Direction direction =
            orderBy.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        List<Category> categories = categoryService.findAllForList(searchValue, pageable)
            .getContent();

        if (categories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
        }
        return ResponseEntity.ok(categories); //200
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable Long id) {
        return categoryService.findOptionalById(id)
            .map(ResponseEntity::ok) // 200
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // 404
    }

    @PostMapping
    public ResponseEntity<Void> addCategory(@RequestBody Category category) {
        if (category.getId() != null) {
            return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).build(); // 451
        }
        categoryService.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201
    }

    @PutMapping
    public ResponseEntity<Void> updateCategory(@RequestBody Category category) {
        if (category.getId() == null) {
            return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).build(); // 451
        }
        categoryService.save(category);
        return ResponseEntity.ok().build(); // 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        if (categoryService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
        } else {
            categoryService.deleteById(id);
            return ResponseEntity.noContent().build(); // 204
        }
    }
}
