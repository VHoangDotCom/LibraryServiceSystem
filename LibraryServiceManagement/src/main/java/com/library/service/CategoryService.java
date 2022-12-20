package com.library.service;

import com.library.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);

    List<Category> getAllCategories();

    String deleteCategory(Long id);

    Category updateCategory(Long id, Category category);
}
