package com.proje.service.impl;

import com.proje.mapper.ProductResponseMapper;
import com.proje.model.api.request.ProductCreateRequest;
import com.proje.model.api.response.ProductResponse;
import com.proje.model.entity.Brand;
import com.proje.model.entity.Category;
import com.proje.model.entity.Product;
import com.proje.model.exceptions.NotFoundException;
import com.proje.repository.BrandRepository;
import com.proje.repository.CategoryRepository;
import com.proje.repository.ProductRepository;
import com.proje.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductResponseMapper productResponseMapper;

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll(){
        return productRepository
                .findAll().stream().map(productResponseMapper::map).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public ProductResponse findById(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()){
            throw new NotFoundException("Product not found by id: " + id);
        }
        Product product = optionalProduct.get();
        return productResponseMapper.map(product);
    }
    @Transactional
    public ProductResponse create(ProductCreateRequest request){
        Optional<Brand> optionalBrand = brandRepository.findById(request.getBrandId());
        if (optionalBrand.isEmpty()) {
            throw new NotFoundException("Brand not found by id :" + request.getBrandId());
        }
        Brand brand = optionalBrand.get();

        Optional<Category> optionalCategory = categoryRepository.findById(request.getCategoryId());
        if (optionalCategory.isEmpty()) {
            throw new NotFoundException("Category not found by id :" + request.getCategoryId());
        }
        Category category = optionalCategory.get();
        if (!category.getChildCategory().isEmpty()){
            throw new RuntimeException("Can't add product to parent category !");
        }
        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setCategory(category);
        product.setBrand(brand);
        Product createdProduct = productRepository.save(product);
        return productResponseMapper.map(createdProduct);
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()){
            throw new NotFoundException("Product not found by id: " + id);
        }
        productRepository.deleteById(optionalProduct.get().getId());
    }
    @Transactional
    public ProductResponse update(ProductCreateRequest request) {
        Optional<Product> optionalProduct = productRepository.findById(request.getId());
        if (optionalProduct.isEmpty()){
            throw new NotFoundException("Product not found by id: " + request.getId());
        }
        Product product = optionalProduct.get();
        product.setProductName(request.getProductName());
        Product createdProduct = productRepository.save(product);
        return productResponseMapper.map(createdProduct);
    }
}
