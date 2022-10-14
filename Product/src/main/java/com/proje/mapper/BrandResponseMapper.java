package com.proje.mapper;

import com.proje.model.api.response.BrandResponse;
import com.proje.model.entity.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandResponseMapper {

    public BrandResponse map(Brand brand){

        return new BrandResponse(brand.getId(), brand.getName());
    }
}
