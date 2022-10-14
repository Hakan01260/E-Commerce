package com.proje.model.api.request;

import lombok.Data;

@Data
public class AccountUpdateRequest {

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
