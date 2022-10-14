package com.proje.service.impl;

import com.google.common.collect.Sets;
import com.proje.mapper.CategoryResponseMapper;
import com.proje.mapper.CategoryResponseParentMapper;
import com.proje.model.api.request.CategoryCreateRequest;
import com.proje.model.api.request.CategoryUpdateRequest;
import com.proje.model.api.request.CategorySingleUpdateRequest;
import com.proje.model.api.response.CategoryResponse;
import com.proje.model.api.response.CategoryParentsResponse;
import com.proje.model.entity.Category;
import com.proje.model.entity.Product;
import com.proje.model.exceptions.BadRequestException;
import com.proje.model.exceptions.NotFoundException;
import com.proje.repository.CategoryRepository;
import com.proje.repository.ProductRepository;
import com.proje.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryResponseMapper categoryResponseMapper;
    private final CategoryResponseParentMapper categoryResponseParentMapper;

    @Transactional(readOnly = true)
    public Set<CategoryResponse> findAll() {
        return categoryRepository
                .findAll().stream().map(categoryResponseMapper::map).collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public CategoryResponse findById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new NotFoundException("Category not found by id: " + id);
        }
        Category category = optionalCategory.get();
        return categoryResponseMapper.map(category);
    }

    @Transactional
    public CategoryResponse create(CategoryCreateRequest request) {
        if (request.getParentId() == null) {
            Category category = new Category();
            category.setName(request.getName());
            Category createdCategory = categoryRepository.save(category);
            return categoryResponseMapper.map(createdCategory);
        }
        Optional<Category> optionalParentCategory = categoryRepository.findById(request.getParentId());
        if (optionalParentCategory.isEmpty()) {
            throw new NotFoundException("Parent Category not found by id: " + request.getParentId());
        }
        Category parentCategory = optionalParentCategory.get();
        boolean exists = productRepository.existsByCategoryId(parentCategory.getId());
        if (exists) {
            throw new BadRequestException("Parent category has products");
        }
        checkSameNameInCategoryHierarchies(request);
        Category category = new Category();
        category.setName(request.getName());
        category.setParent(parentCategory);
        Category createdCategory = categoryRepository.save(category);
        return categoryResponseMapper.map(createdCategory);
    }

    private void checkSameNameInCategoryHierarchies(CategoryCreateRequest request) {
        Long parentId = request.getParentId();
        Set<String> categoryNames = Sets.newHashSet(request.getName());
        Optional<Category> optionalParentCategory = categoryRepository.findById(parentId);
        if (optionalParentCategory.isEmpty()) {
            throw new NotFoundException("Parent category not found by id: " + parentId);
        }
        Category parentCategory = optionalParentCategory.get();
        do {
            if (categoryNames.contains(parentCategory.getName())) {
                throw new RuntimeException("Already exist category name in hierarchy");
            }
            categoryNames.add(parentCategory.getName());
            parentCategory = parentCategory.getParent();
        } while (parentCategory != null);
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new NotFoundException("Category not found by id: " + id);
        }
        if (productRepository.existsByCategoryId(optionalCategory.get().getId())) {
            throw new RuntimeException("The category cannot be deleted because the main category is the product owner. !");
        }
        if (!optionalCategory.get().getChildCategory().isEmpty()) {
            throw new BadRequestException("Category can't transport because have child category !");
        }
        categoryRepository.deleteById(id);
    }

    @Transactional
    public CategoryResponse update(CategoryUpdateRequest request) {
        //categorinin parentId guncelledik ve child categorileri ile birlikte taşıdık.
        if (request.getParentId() != null) {
            Optional<Category> optionalParentCategory = categoryRepository.findById(request.getParentId());
            if (optionalParentCategory.isEmpty()) {
                throw new NotFoundException("Parent Category not found by id: " + request.getParentId());
            }
            Category parentCategory = optionalParentCategory.get();
            if (productRepository.existsByCategoryId(parentCategory.getId())) {
                throw new BadRequestException("The category cannot be updated because the main category is the product owner. !");
            }
            Optional<Category> optionalCategory = categoryRepository.findById(request.getId());
            if (optionalCategory.isEmpty()) {
                throw new NotFoundException("Category not found by id: " + request.getId());
            }
            Category category = optionalCategory.get();
            category.setName(request.getName());
            category.setParent(parentCategory);
            Category createdCategory = categoryRepository.save(category);
            return categoryResponseMapper.map(createdCategory);
        }
        Optional<Category> optionalCategory = categoryRepository.findById(request.getId());
        if (optionalCategory.isEmpty()) {
            throw new NotFoundException("Category not found by id: " + request.getId());
        }
        Category category = optionalCategory.get();
        category.setName(request.getName());
        category.setParent(null);
        Category createdCategory = categoryRepository.save(category);
        return categoryResponseMapper.map(createdCategory);
    }

    @Transactional
    public CategoryResponse updateParentCategory(CategorySingleUpdateRequest request) {
        //categoryi tek başına taşıyarak id ve parent ıd guncelleme
        Optional<Category> optionalCategory = categoryRepository.findById(request.getId());
        if (optionalCategory.isEmpty()) {
            throw new NotFoundException("Category not found by id: " + request.getId());
        }
        if (productRepository.existsByCategoryId(optionalCategory.get().getId())) {
            throw new RuntimeException("The category immovable because the category have product !");
        }
        Category category = optionalCategory.get();
        Category parentCategory = category.getParent();
        if (parentCategory == null) {
            throw new NotFoundException("Parent category not found !");
        }
        Set<Category> childCategory = new HashSet<>();
        if (!category.getChildCategory().isEmpty()) {
            parentCategory.setChildCategory(category.getChildCategory());
            childCategory = category.getChildCategory();
            for (Category child : childCategory) {
                child.setParent(parentCategory);
            }
            category.setChildCategory(null);
        }

        Optional<Category> optionalParentCategory = categoryRepository.findById(request.getParentId());
        if (optionalParentCategory.isEmpty()) {
            throw new NotFoundException("Parent category not found by id: " + request.getParentId());
        }
        Category parent = optionalParentCategory.get();
        category.setParent(parent);
        Category createdCategory = categoryRepository.save(category);
        return categoryResponseMapper.map(createdCategory);
    }

    @Transactional
    public CategoryResponse childCategoryTransport(Long categoryId, Long parentId) {
        //en alt categorinin ürünlerini silip taşıma
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty()) {
            throw new NotFoundException("Category not found by id: " + categoryId);
        }
        Category findCategory = optionalCategory.get();
        List<Product> products = findCategory.getProducts();
        if (products != null) {
            productRepository.deleteAll(products);
            findCategory.setProducts(null);
        }
        Optional<Category> optionalParent = categoryRepository.findById(parentId);
        if (optionalParent.isEmpty()) {
            throw new NotFoundException("Parent category not found by id: " + categoryId);
        }
        findCategory.setParent(optionalParent.get());
        Category createdCategory = categoryRepository.save(findCategory);
        return categoryResponseMapper.map(createdCategory);
    }

    @Transactional(readOnly = true)
    public CategoryResponse findHierarchyById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new NotFoundException("Category not found by id: " + id);
        }
        Category category = optionalCategory.get();
        return categoryResponseMapper.map(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryParentsResponse> findParentById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new NotFoundException("Category not found by id: " + id);
        }
        Category category = optionalCategory.get();
        List<CategoryParentsResponse> categories = getParents(category);
        return categories.stream().map(categoryResponseParentMapper::map).collect(Collectors.toList());
    }

    public List<CategoryParentsResponse> getParents(Category category) {
        List<CategoryParentsResponse> categories = new ArrayList<>();
        while (category.getParent() != null) {
            categories.add(new CategoryParentsResponse(category.getId(), category.getName(), category.getParent().getId()));
            category = category.getParent();
        }
        categories.add(new CategoryParentsResponse(category.getId(),category.getName(),null));
        Collections.reverse(categories);
        return categories;
    }
}
