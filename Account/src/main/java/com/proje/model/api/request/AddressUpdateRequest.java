package com.proje.model.api.request;

import lombok.Data;

@Data
public class AddressUpdateRequest {

    private Long id;
    private String street;
    private String city;
}
