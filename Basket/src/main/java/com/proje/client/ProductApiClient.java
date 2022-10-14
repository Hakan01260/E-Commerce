package com.proje.client;

import com.proje.model.api.response.ProductResponse;
import com.proje.model.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ProductApiClient {

    private final RestTemplate restTemplate;

    public ProductResponse getProductById(Long id) {
        try {
            return restTemplate.getForObject(
                    "http://localhost:9090/products/" + id, ProductResponse.class);
        } catch (HttpClientErrorException.NotFound e){
            throw new NotFoundException("Product not found by id: " + id);
        }
    }
}
