package com.proje.mapper;

import com.proje.model.api.response.CategoryParentsResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryResponseParentMapper {

    public CategoryParentsResponse map(CategoryParentsResponse category){
        return new CategoryParentsResponse(category.getId(), category.getName(), category.getParentId());
    }
}
