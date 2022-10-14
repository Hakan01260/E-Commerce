package com.proje.model.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasketItemResponse {

    private Long id;

    private Long productId;

    private Long accountId;

    private Date addedDate;
}
