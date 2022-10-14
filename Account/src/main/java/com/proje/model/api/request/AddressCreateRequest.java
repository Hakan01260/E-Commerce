package com.proje.model.api.request;

import lombok.Data;

@Data
public class AddressCreateRequest {

    private String street;
    private String city;
    private Long accountId;
}
