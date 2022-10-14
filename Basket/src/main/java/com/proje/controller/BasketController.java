package com.proje.controller;

import com.proje.model.api.request.BasketCreateRequest;
import com.proje.model.api.request.BasketUpdateRequest;
import com.proje.model.api.response.BasketGroupedByProductIdResponse;
import com.proje.model.api.response.BasketItemResponse;
import com.proje.model.api.response.BasketResponse;
import com.proje.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("baskets")
public class BasketController {

    private final BasketService basketService;

    @GetMapping
    public List<BasketItemResponse> getAll() {
        return basketService.findAll();
    }

    @GetMapping("/{id}")
    public BasketItemResponse getBasketById(@PathVariable Long id) {
        return basketService.findById(id);
    }

    @GetMapping("/account/{accountId}")
    public BasketResponse getBasketItemByAccountId(@PathVariable Long accountId) {
        return basketService.getBasketByAccountId(accountId);
    }

    @GetMapping("/product/{productId}")
    public BasketGroupedByProductIdResponse getBasketItemByProductId(@PathVariable Long productId) {
        return basketService.getBasketByProductId(productId);
    }

    @PostMapping
    public ResponseEntity<BasketItemResponse> posts(@RequestBody BasketCreateRequest request) {
        return new ResponseEntity<>(basketService.create(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteBasketById(@PathVariable Long id) {
        basketService.deleteById(id);
    }

    @PutMapping
    public BasketItemResponse update(@RequestBody BasketUpdateRequest request) {
        return basketService.update(request);
    }
}
