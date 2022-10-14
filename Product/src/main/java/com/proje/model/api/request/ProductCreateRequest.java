package com.proje.model.api.request;

import lombok.Data;

@Data
public class ProductCreateRequest {

    private Long id;

    private String productName;

    private Long brandId;

    private Long categoryId;
}
