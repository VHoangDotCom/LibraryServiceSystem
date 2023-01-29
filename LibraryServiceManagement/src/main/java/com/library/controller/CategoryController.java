package com.library.controller;

import com.library.entity.Category;
import com.library.repository.CategoryRepository;
import com.library.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@EnableScheduling
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;


    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

   /* @GetMapping("/categories")
    public List<Category> getAllCategories(Pageable pageable) {
        return (List<Category>) categoryRepository.findAll(pageable);
    }*/

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getCategoryByID(@PathVariable Long id){
        if(categoryRepository.findById(id) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with id "+id+" is not existed !");
        }else {
            return ResponseEntity.ok().body(categoryRepository.findById(id));
        }
    }

    @PostMapping("/categories/add")
    public Category createCategory(@Validated @RequestBody Category category) {
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
