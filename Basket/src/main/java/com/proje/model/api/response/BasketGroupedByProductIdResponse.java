package com.proje.model.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) //diger taraftan istemedigimiz json bilgisi gelirse(address gibi) önemseme.
public class BasketGroupedByProductIdResponse {

    private Product product;

    private List<Account> accounts;


    @Data
    public static class Product{
        private Long id;
        private String productName;
    }
    @Data
    public static class Account{
        private Long id;
        private String firstName;
        private String lastName;
        private int count;
    }
}
