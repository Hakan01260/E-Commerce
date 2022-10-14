package com.proje.model.api.request;

import lombok.Data;

@Data
public class CategoryCreateRequest {

    private String name;
    private Long parentId;
}
