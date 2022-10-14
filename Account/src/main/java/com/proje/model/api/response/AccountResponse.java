package com.proje.model.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
public class AccountResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date createDate;
    private List<AddressResponse> addresses = new ArrayList<>();

}
