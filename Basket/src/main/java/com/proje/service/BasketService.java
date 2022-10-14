package com.proje.service;

import com.proje.model.api.request.BasketCreateRequest;
import com.proje.model.api.request.BasketUpdateRequest;
import com.proje.model.api.response.BasketGroupedByProductIdResponse;
import com.proje.model.api.response.BasketItemResponse;
import com.proje.model.api.response.BasketResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BasketService {

    List<BasketItemResponse> findAll();
    BasketItemResponse findById(Long id);
    BasketItemResponse create(BasketCreateRequest request);
    void deleteById(Long id);
    BasketItemResponse update(BasketUpdateRequest request);
    BasketResponse getBasketByAccountId(Long accountId);
    BasketGroupedByProductIdResponse getBasketByProductId(Long productId);
}
