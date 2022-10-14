package com.proje.model.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CategoryParentsResponse {

    private Long id;
    private String name;
    private Long parentId;
}
