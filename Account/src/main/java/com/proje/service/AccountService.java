package com.proje.service;

import com.proje.model.api.request.AccountCreateRequest;
import com.proje.model.api.request.AccountUpdateRequest;
import com.proje.model.api.response.AccountResponse;
import com.proje.model.api.response.AddressResponse;
import com.proje.model.entity.Address;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {

    List<AccountResponse> findAll();
    AccountResponse findById(Long id);
    AccountResponse create(AccountCreateRequest request);
    void deleteById(Long id);
    AccountResponse update(AccountUpdateRequest request);
    List<AddressResponse> findAccountAddressesById(Long id);
}
