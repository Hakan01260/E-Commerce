package com.proje.model.api.request;

import lombok.Data;

@Data
public class CategorySingleUpdateRequest {

    private Long id;
    private Long parentId;
}
