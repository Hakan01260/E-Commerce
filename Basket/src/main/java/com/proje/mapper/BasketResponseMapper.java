package com.proje.mapper;

import com.proje.client.AccountApiClient;
import com.proje.client.ProductApiClient;
import com.proje.model.api.response.AccountResponse;
import com.proje.model.api.response.BasketResponse;
import com.proje.model.api.response.ProductResponse;
import com.proje.model.entity.BasketItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BasketResponseMapper {

    private final ProductApiClient productApiClient;
    private final AccountApiClient accountApiClient;

    public BasketResponse map(Long accountId, List<BasketItem> basketItems){

        AccountResponse accountResponse = accountApiClient.getAccountById(accountId);

        BasketResponse.Account account = new BasketResponse.Account();
        account.setId(accountId);
        account.setFirstName(accountResponse.getFirstName());
        account.setLastName(accountResponse.getLastName());

        Map<Long, List<BasketItem>> basketListGrouped =
                basketItems.stream().collect(Collectors.groupingBy(BasketItem::getProductId));

        List<BasketResponse.Product> products = new ArrayList<>();
        basketListGrouped.forEach((productId, items) -> {
            ProductResponse productResponse = productApiClient.getProductById(productId);
            BasketResponse.Product product = new BasketResponse.Product();
            product.setCount(items.size());
            product.setId(productId);
            product.setProductName(productResponse.getProductName());
            products.add(product);
        });

        BasketResponse basketResponse = new BasketResponse();
        basketResponse.setProducts(products);
        basketResponse.setAccount(account);
        return basketResponse;
    }
}
