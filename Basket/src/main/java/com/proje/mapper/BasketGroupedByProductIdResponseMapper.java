package com.proje.mapper;

import com.proje.client.AccountApiClient;
import com.proje.client.ProductApiClient;
import com.proje.model.api.response.AccountResponse;
import com.proje.model.api.response.BasketGroupedByProductIdResponse;
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
public class BasketGroupedByProductIdResponseMapper {

    private final ProductApiClient productApiClient;
    private final AccountApiClient accountApiClient;

    public BasketGroupedByProductIdResponse map(Long productId, List<BasketItem> basketItems){

        ProductResponse productResponse = productApiClient.getProductById(productId);
        BasketGroupedByProductIdResponse.Product product = new BasketGroupedByProductIdResponse.Product();
        product.setId(productId);
        product.setProductName(productResponse.getProductName());

        Map<Long, List<BasketItem>> basketListGrouped =
                basketItems.stream().collect(Collectors.groupingBy(BasketItem::getAccountId));

        List<BasketGroupedByProductIdResponse.Account> accounts = new ArrayList<>();
        basketListGrouped.forEach((accountId, items) -> {
            AccountResponse accountResponse = accountApiClient.getAccountById(accountId);
            BasketGroupedByProductIdResponse.Account account = new BasketGroupedByProductIdResponse.Account();
            account.setId(accountId);
            account.setFirstName(accountResponse.getFirstName());
            account.setLastName(accountResponse.getLastName());
            account.setCount(items.size());

            accounts.add(account);
        });

        BasketGroupedByProductIdResponse basketGroupedAccountResponse = new BasketGroupedByProductIdResponse();
        basketGroupedAccountResponse.setProduct(product);
        basketGroupedAccountResponse.setAccounts(accounts);
        return basketGroupedAccountResponse;
    }


}
