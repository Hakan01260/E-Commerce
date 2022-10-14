package com.proje.controller;

import com.proje.model.api.request.AccountCreateRequest;
import com.proje.model.api.request.AccountUpdateRequest;
import com.proje.model.api.request.AddressCreateRequest;
import com.proje.model.api.request.AddressUpdateRequest;
import com.proje.model.api.response.AccountResponse;
import com.proje.model.api.response.AddressResponse;
import com.proje.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("addresses")
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public List<AddressResponse> getAllAddresses() {
        return addressService.findAll();
    }

    @GetMapping("/{id}")
    public AddressResponse getAddressById(@PathVariable Long id) {
        return addressService.findById(id);
    }

    @PostMapping
    public ResponseEntity<AddressResponse> posts(@RequestBody AddressCreateRequest request) {
        return new ResponseEntity<>(addressService.create(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteAddressById(@PathVariable Long id) {
        addressService.deleteById(id);
    }

    @PutMapping
    public AddressResponse put(@RequestBody AddressUpdateRequest request) {
        return addressService.update(request);
    }

}
