package com.library.controller;

import com.library.entity.Category;
import com.library.repository.CategoryRepository;
import com.library.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@EnableScheduling
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;


    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getCategoryByID(@PathVariable Long id){
        if(categoryRepository.findById(id) == null){
            return ResponseEntity.ok().body("Category with id "+id+" is not existed !");
        }else {
            return ResponseEntity.ok().body(categoryRepository.findById(id));
        }
    }

    @PostMapping("/categories/add")
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @DeleteMapping("/categories/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }


    @PutMapping("/categories/save/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id,
                                                   @RequestBody Category category) {
        category = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(category);
    }

}
