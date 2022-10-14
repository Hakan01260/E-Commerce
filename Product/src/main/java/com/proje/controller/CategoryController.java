package com.proje.controller;

import com.proje.model.api.request.CategoryCreateRequest;
import com.proje.model.api.request.CategoryUpdateRequest;
import com.proje.model.api.request.CategorySingleUpdateRequest;
import com.proje.model.api.response.CategoryResponse;
import com.proje.model.api.response.CategoryParentsResponse;
import com.proje.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public Set<CategoryResponse> getAll(){
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id){
        return categoryService.findById(id);
    }

    @GetMapping("/hierarchy/{id}")
    public CategoryResponse getCategoryHierarchyById(@PathVariable Long id){
        return categoryService.findHierarchyById(id);
    }
    @GetMapping("/parent/{id}")
    public List<CategoryParentsResponse> getParentById(@PathVariable Long id){
        return categoryService.findParentById(id);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryCreateRequest request) {
        return new ResponseEntity<>(categoryService.create(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

    @PutMapping
    public CategoryResponse update(@RequestBody CategoryUpdateRequest request) {
        return categoryService.update(request);
    }
    @PutMapping("/single-transport")
    public CategoryResponse CategorySingleUpdate(@RequestBody CategorySingleUpdateRequest request){
        return categoryService.updateParentCategory(request);
    }
    @PutMapping("/{categoryId}/{parentId}")
    public CategoryResponse childCategoryTransport(@PathVariable Long categoryId,@PathVariable Long parentId){
        return categoryService.childCategoryTransport(categoryId,parentId);
    }
}
