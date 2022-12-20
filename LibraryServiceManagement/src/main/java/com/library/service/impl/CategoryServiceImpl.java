package com.library.service.impl;

import com.library.entity.Category;
import com.library.repository.CategoryRepository;
import com.library.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(Category category) {
        categoryRepository.save(category);
        return category;
    }

    @Override
    public List<Category> getAllCategories() {
        log.info("Fetching all categories");
        return categoryRepository.findAll();
    }

    @Override
    public String deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).get();
        if(category == null){
            return "Cannot find Category " +id;
        }else{
            categoryRepository.delete(category);
            return "Category "+id+ " has been deleted !";
        }
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Category categoryExisted = categoryRepository.findById(id).get();
        categoryExisted.setName(category.getName());
        categoryRepository.save(categoryExisted);
        return category;
    }
}
