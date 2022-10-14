package com.proje.service;

import com.proje.client.AccountApiClient;
import com.proje.client.ProductApiClient;
import com.proje.mapper.BasketGroupedByProductIdResponseMapper;
import com.proje.mapper.BasketItemResponseMapper;
import com.proje.mapper.BasketResponseMapper;
import com.proje.model.api.request.BasketCreateRequest;
import com.proje.model.api.request.BasketUpdateRequest;
import com.proje.model.api.response.*;
import com.proje.model.entity.BasketItem;
import com.proje.model.exceptions.NotFoundException;
import com.proje.repository.BasketItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BasketServiceImpl implements BasketService{

    private final ProductApiClient productApiClient;
    private final AccountApiClient accountApiClient;
    private final BasketItemRepository basketRepository;
    private final BasketResponseMapper basketResponseMapper;
    private final BasketItemResponseMapper basketItemResponseMapper;
    private final BasketGroupedByProductIdResponseMapper basketGroupedByProductIdResponseMapper;

    @Transactional(readOnly = true)
    public List<BasketItemResponse> findAll(){
        return basketRepository
                .findAll().stream().map(basketItemResponseMapper::map).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BasketItemResponse findById(Long id){
        Optional<BasketItem> optionalBasket = basketRepository.findById(id);
        if (optionalBasket.isEmpty()){
            throw new NotFoundException("Basket not found by id: " + id);
        }
        BasketItem basket = optionalBasket.get();
        return basketItemResponseMapper.map(basket);
    }

    @Transactional(readOnly = true)
    public BasketResponse getBasketByAccountId(Long accountId){
        List<BasketItem> basketItems = basketRepository.findByAccountId(accountId);
        return basketResponseMapper.map(accountId, basketItems);
    }

    @Transactional(readOnly = true)
    public BasketGroupedByProductIdResponse getBasketByProductId(Long productId){
        List<BasketItem> basketItems = basketRepository.findByProductId(productId);
        return basketGroupedByProductIdResponseMapper.map(productId, basketItems);
    }

    @Transactional
    public BasketItemResponse create(BasketCreateRequest request){
        AccountResponse account = accountApiClient.getAccountById(request.getAccountId());
        if (account == null){
          throw new NotFoundException("Account not found by id" + request.getAccountId());
        }
        ProductResponse product = productApiClient.getProductById(request.getProductId());
        if (product == null){
            throw new NotFoundException("Product not found by id" + request.getProductId());
        }
        BasketItem basket = new BasketItem();
        basket.setAccountId(request.getAccountId());
        basket.setProductId(request.getProductId());
        BasketItem createdBasket = basketRepository.save(basket);
        return basketItemResponseMapper.map(createdBasket);
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<BasketItem> optionalBasket = basketRepository.findById(id);
        if (optionalBasket.isEmpty()){
            throw new NotFoundException("Basket not found by id: " + id);
        }
        basketRepository.deleteById(optionalBasket.get().getId());
    }
    @Transactional
    public BasketItemResponse update(BasketUpdateRequest request) {
        Optional<BasketItem> optionalBasket = basketRepository.findById(request.getId());
        if (optionalBasket.isEmpty()){
            throw new NotFoundException("Basket not found by id: " + request.getId());
        }
        BasketItem basket = optionalBasket.get();
        basket.setAccountId(request.getAccountId());
        basket.setProductId(request.getProductId());
        BasketItem createdBasket = basketRepository.save(basket);
        return basketItemResponseMapper.map(createdBasket);
    }
}
