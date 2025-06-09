package com.example.springwebmvc.service;

import com.example.springwebmvc.model.Category;

import java.util.List;

public interface CategoryService {

    void createCategory(Category category);
    void updateCategory(Integer id, Category category);
    void deleteCategory(Integer id);
    List<Category> findAllCategories();
    Category findCategoryById(Integer id);
    Category findCategoryByName(String name);
}
