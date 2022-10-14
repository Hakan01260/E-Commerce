package com.proje.model.api.request;

import lombok.Data;

@Data
public class AccountCreateRequest {

    private String firstName;
    private String lastName;
    private String phoneNumber;
}
