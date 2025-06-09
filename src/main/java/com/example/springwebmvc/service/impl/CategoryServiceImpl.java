package com.example.springwebmvc.service.impl;

import com.example.springwebmvc.model.Category;
import com.example.springwebmvc.repository.CategoryRepository;
import com.example.springwebmvc.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void createCategory(Category category) {
        boolean exists = categoryRepository.existsCategoryByName(category.getName());
        if (exists) {
            throw new RuntimeException("Category with name '" + category.getName() + "' already exists.");
        }
        categoryRepository.insertCategory(category);
        log.info("Category created: {}", category.getName());
    }

    @Override
    @Transactional
    public void updateCategory(Integer id, Category category) {
        Category existingCategory = categoryRepository.findCategoryById(id)
                .orElseThrow(() -> new RuntimeException("Category with ID " + id + " not found."));

        // If the name is the same, skip duplicate check (or skip rename logic if name unchanged)
        if (!existingCategory.getName().equalsIgnoreCase(category.getName())) {
            boolean existsByName = categoryRepository.existsCategoryByName(category.getName());
            if (existsByName) {
                throw new RuntimeException("Another category with name '" + category.getName() + "' already exists.");
            }
        }

        category.setId(id);
        categoryRepository.updateCategory(category);
        log.info("Category updated: {}", category.getName());
    }

    @Override
    @Transactional
    public void deleteCategory(Integer id) {
        categoryRepository.deleteCategoryById(id);
        log.info("Category deleted: ID={}", id);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAllCategories();
    }

    @Override
    public Category findCategoryById(Integer id) {
        return categoryRepository.findCategoryById(id)
                .orElseThrow(() -> new RuntimeException("Category with ID " + id + " not found."));
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name)
                .orElseThrow(() -> new RuntimeException("Category with name '" + name + "' not found."));
    }
}
