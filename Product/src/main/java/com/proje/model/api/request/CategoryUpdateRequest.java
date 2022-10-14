package com.proje.model.api.request;

import lombok.Data;

@Data
public class CategoryUpdateRequest {

    private Long id;
    private String name;
    private Long parentId;
}
