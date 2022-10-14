package com.proje.service;

import com.proje.model.api.request.ProductCreateRequest;
import com.proje.model.api.response.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    List<ProductResponse> findAll();
    ProductResponse findById(Long id);
    ProductResponse create(ProductCreateRequest request);
    void deleteById(Long id);
    ProductResponse update(ProductCreateRequest request);

}
