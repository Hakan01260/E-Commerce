package com.proje.mapper;

import com.proje.model.api.response.AccountResponse;
import com.proje.model.api.response.AddressResponse;
import com.proje.model.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class AccountResponseMapper {

    private final AddressResponseMapper addressResponseMapper;

    public AccountResponse map(Account account){
        List<AddressResponse> addresses = account.getAddresses()
                .stream()
                .map(addressResponseMapper::map)
                .collect(Collectors.toList());
        return new AccountResponse(
                account.getId(),
                account.getFirstName(),
                account.getLastName(),
                account.getPhoneNumber(),
                account.getCreateDate(),
                addresses);
    }
}
