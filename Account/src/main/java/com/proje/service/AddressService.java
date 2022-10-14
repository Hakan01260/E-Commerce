package com.proje.service;

import com.proje.model.api.request.AddressCreateRequest;
import com.proje.model.api.request.AddressUpdateRequest;
import com.proje.model.api.response.AddressResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {

    List<AddressResponse> findAll();
    AddressResponse findById(Long id);
    AddressResponse create(AddressCreateRequest request);
    void deleteById(Long id);
    AddressResponse update(AddressUpdateRequest request);
}
