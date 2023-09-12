package com.fastkart.productservice.startup;

import com.fastkart.productservice.model.entity.Category;
import com.fastkart.productservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {
        if (categoryRepository.findAll().isEmpty()) {
            Category category1 = new Category();
            Category category2 = new Category();
            Category category3 = new Category();
            category1.setCategoryName("category1");
            category2.setCategoryName("category2");
            category3.setCategoryName("category3");
            categoryRepository.save(category1);
            categoryRepository.save(category2);
            categoryRepository.save(category3);
        }
    }
}
