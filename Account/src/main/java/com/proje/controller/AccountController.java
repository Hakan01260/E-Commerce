package com.proje.controller;

import com.proje.model.api.request.AccountCreateRequest;
import com.proje.model.api.request.AccountUpdateRequest;
import com.proje.model.api.response.AccountResponse;
import com.proje.model.api.response.AddressResponse;
import com.proje.model.entity.Address;
import com.proje.service.AccountService;
import com.proje.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public List<AccountResponse> getAllAccounts() {
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public AccountResponse getAccountById(@PathVariable Long id) {
        return accountService.findById(id);
    }

    @PostMapping
    public ResponseEntity<AccountResponse> posts(@RequestBody AccountCreateRequest request) {
        return new ResponseEntity<>(accountService.create(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteAccountById(@PathVariable Long id) {
        accountService.deleteById(id);
    }

    @PutMapping
    public AccountResponse put(@RequestBody AccountUpdateRequest request) {
        return accountService.update(request);
    }

    @GetMapping("/{id}/addresses")
    public List<AddressResponse> getAccountAddressesById(@PathVariable Long id) {
        return accountService.findAccountAddressesById(id);
    }
}
