package com.proje.mapper;

import com.proje.model.api.response.BasketItemResponse;
import com.proje.model.entity.BasketItem;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BasketItemResponseMapper {

    public BasketItemResponse map(BasketItem basket){
        basket.setAddedDate(new Date());
        return new BasketItemResponse(
                basket.getId(),
                basket.getProductId(),
                basket.getAccountId(),
                basket.getAddedDate());
    }
}
