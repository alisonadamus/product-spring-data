package com.alisonadamus.product_spring_data.services;

import com.alisonadamus.product_spring_data.entities.Category;
import com.alisonadamus.product_spring_data.entities.Product;
import com.alisonadamus.product_spring_data.repositories.CategoryRepository;
import com.alisonadamus.product_spring_data.repositories.ProductRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class FillingTables {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final List<String> colors = List.of("Red", "Green", "Blue", "Yellow", "Black", "Purple",
        "Orange");
    private final Random random = new Random();

    public FillingTables(CategoryRepository categoryRepository,
        ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public void fillCategories() {
        List<Category> categories = new ArrayList<>();
        for (String color : colors) {
            Category category = new Category();
            category.setName("Category " + color);
            categories.add(category);
        }
        categoryRepository.saveAll(categories);
    }

    public void fillProducts() {
        productRepository.deleteAll();

        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            Product product = new Product();
            String randomColor = colors.get(random.nextInt(colors.size()));
            product.setName(randomColor + " product");
            product.setCost(BigDecimal.valueOf(random.nextInt(10, 100)));
            product.setPhotoPath("/images/" + randomColor.toLowerCase() + "_product.jpg");
            product.setDescription("Description for " + randomColor + " product " + i);
            product.setCategory(categoryRepository.findByName("Category " + randomColor).orElseThrow());
            products.add(product);
        }
        productRepository.saveAll(products);
    }
}
