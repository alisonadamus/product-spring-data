package com.alisonadamus.product_spring_data.controller;

import com.alisonadamus.product_spring_data.entity.Category;
import com.alisonadamus.product_spring_data.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String getCategories(Model model,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(required = false) String searchValue,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "ASC") String orderBy) {

        Sort.Direction direction =
            orderBy.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Category> categories = categoryService.findAllForList(searchValue,pageable);

        model.addAttribute("categories", categories.getContent());
        model.addAttribute("totalPages", categories.getTotalPages());
        model.addAttribute("currentPage", page);
        return "categories-list";
    }
}
