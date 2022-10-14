package com.proje.model.api.request;

import lombok.Data;

@Data
public class BasketUpdateRequest {

    private Long id;

    private Long ProductId;

    private Long AccountId;
}
