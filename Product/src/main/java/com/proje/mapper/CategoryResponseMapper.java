package com.proje.mapper;

import com.proje.model.api.response.CategoryResponse;
import com.proje.model.entity.Category;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryResponseMapper {

    public CategoryResponse map(Category category){
        String hierarchies = getHierarchies(category);
        Set<CategoryResponse> childCategory = category.getChildCategory()
                .stream().map(this::map).collect(Collectors.toSet());
        Long parentId = null;
        if (category.getParent() != null) {
            parentId = category.getParent().getId();
        }
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                parentId,
                hierarchies,
                childCategory);
    }

    public String getHierarchies(Category category) {
        String hierarcy = "/" + category.getId() + "/";
        while(category.getParent() != null) {
            category = category.getParent();
            hierarcy += category.getId() + "/";
        }
        return hierarcy;
    }
}
