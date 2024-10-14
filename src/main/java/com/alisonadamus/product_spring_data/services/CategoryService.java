package com.alisonadamus.product_spring_data.services;

import com.alisonadamus.product_spring_data.entities.Category;
import com.alisonadamus.product_spring_data.entities.CategoryProjection;
import com.alisonadamus.product_spring_data.repositories.CategoryRepository;
import java.util.List;
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

}
