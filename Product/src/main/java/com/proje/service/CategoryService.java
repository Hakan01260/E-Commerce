package com.proje.service;

import com.proje.model.api.request.CategoryCreateRequest;
import com.proje.model.api.request.CategoryUpdateRequest;
import com.proje.model.api.request.CategorySingleUpdateRequest;
import com.proje.model.api.response.CategoryResponse;
import com.proje.model.api.response.CategoryParentsResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface CategoryService {

    Set<CategoryResponse> findAll();
    CategoryResponse findById(Long id);
    CategoryResponse create(CategoryCreateRequest request);
    void deleteById(Long id);
    CategoryResponse update(CategoryUpdateRequest request);
    CategoryResponse updateParentCategory(CategorySingleUpdateRequest request);
    CategoryResponse childCategoryTransport(Long categoryId, Long parentId);
    CategoryResponse findHierarchyById(Long id);
    List<CategoryParentsResponse> findParentById(Long id);
}
