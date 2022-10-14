package com.proje.client;

import com.proje.model.api.response.AccountResponse;
import com.proje.model.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AccountApiClient {

    private final RestTemplate restTemplate;

    public AccountResponse getAccountById(Long id) {
       try {
           return restTemplate.getForObject(
                   "http://localhost:8080/accounts/" + id, AccountResponse.class);
       } catch (HttpClientErrorException.NotFound e){
           throw new NotFoundException("Account not found by id: " + id);
       }
    }
}
