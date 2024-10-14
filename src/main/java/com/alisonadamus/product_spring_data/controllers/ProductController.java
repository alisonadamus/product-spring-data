package com.alisonadamus.product_spring_data.controllers;

import com.alisonadamus.product_spring_data.entities.Category;
import com.alisonadamus.product_spring_data.entities.Product;
import com.alisonadamus.product_spring_data.entities.ProductProjectionForList;
import com.alisonadamus.product_spring_data.services.CategoryService;
import com.alisonadamus.product_spring_data.services.FillingTables;
import com.alisonadamus.product_spring_data.services.ProductService;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final FillingTables fillingTables;

    @GetMapping
    public String getProducts(Model model,
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

        Page<ProductProjectionForList> products = productService.findAllForList(searchValue,
            categoryId, minPrice, maxPrice, pageable);

        List<Category> categories = categoryService.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("products", products.getContent());
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("currentPage", page);
        return "products-list";
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);

        model.addAttribute("product", product);
        return "product-view";
    }

}
