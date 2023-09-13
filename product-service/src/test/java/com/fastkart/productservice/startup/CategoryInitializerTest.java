package com.fastkart.productservice.startup;

import com.fastkart.productservice.model.entity.Category;
import com.fastkart.productservice.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.Mockito.*;

public class CategoryInitializerTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryInitializer categoryInitializer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInitializeDatabaseWhenCategoriesExist() {
        // Mock the behavior of categoryRepository
        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(new Category()));

        // Call the initializeDatabase method
        categoryInitializer.run();

        // Verify that findAll() was called once, but no categories were saved
        verify(categoryRepository, times(1)).findAll();
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    public void testInitializeDatabaseWhenNoCategoriesExist() {
        // Mock the behavior of categoryRepository to return an empty list
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        // Call the initializeDatabase method
        categoryInitializer.run();

        // Verify that findAll() was called once, and categories were saved three times
        verify(categoryRepository, times(1)).findAll();
        verify(categoryRepository, times(3)).save(any(Category.class));
    }
}
