package com.proje.mapper;

import com.proje.model.api.response.BrandResponse;
import com.proje.model.api.response.CategoryResponse;
import com.proje.model.api.response.ProductResponse;
import com.proje.model.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductResponseMapper {

    private final BrandResponseMapper brandResponseMapper;
    private final CategoryResponseMapper categoryResponseMapper;

    public ProductResponse map(Product product) {
        CategoryResponse categoryResponse = categoryResponseMapper.map(product.getCategory());
        BrandResponse brandResponse = brandResponseMapper.map(product.getBrand());
        return new ProductResponse(product.getId(), product.getProductName(), brandResponse, categoryResponse);
    }
}
