package com.alisonadamus.product_spring_data.service;

import com.alisonadamus.product_spring_data.entity.Category;
import com.alisonadamus.product_spring_data.entity.CategoryProjection;
import com.alisonadamus.product_spring_data.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Page<Category> findAllForList(String searchValue, Pageable pageable) {
        return categoryRepository.findAllForList(searchValue, pageable);
    }

    public List<CategoryProjection> findAllForSelection() {
        return categoryRepository.findAllForSelection();
    }

    public Optional<Category> findOptionalById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

}
