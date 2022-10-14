package com.proje.service.impl;

import com.proje.mapper.AddressResponseMapper;
import com.proje.model.api.request.AddressCreateRequest;
import com.proje.model.api.request.AddressUpdateRequest;
import com.proje.model.api.response.AddressResponse;
import com.proje.model.entity.Account;
import com.proje.model.entity.Address;
import com.proje.model.exceptions.NotFoundException;
import com.proje.repository.AccountRepository;
import com.proje.repository.AddressRepository;
import com.proje.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressResponseMapper addressResponseMapper;
    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public List<AddressResponse> findAll() {
        return addressRepository.findAll()
                .stream()
                .map(addressResponseMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AddressResponse findById(Long id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isEmpty()) {
            throw new NotFoundException("Address not found by id :" + id);
        }
        return addressResponseMapper.map(optionalAddress.get());
    }

    @Transactional
    public AddressResponse create(AddressCreateRequest request) {
        Optional<Account> optionalAccount = accountRepository.findById(request.getAccountId());
        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("Account not found by id :" + request.getAccountId());
        }
        Account account = optionalAccount.get();
        Address address = new Address();
        address.setCity(request.getCity());
        address.setStreet(request.getStreet());
        address.setAccount(account);
        Address createdAddress = addressRepository.save(address);
        return addressResponseMapper.map(createdAddress);
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isEmpty()) {
            throw new NotFoundException("Address not found by id: " + id);
        }
        addressRepository.deleteById(optionalAddress.get().getId());
    }

    @Transactional
    public AddressResponse update(AddressUpdateRequest request) {
        Optional<Address> optionalAddress = addressRepository.findById(request.getId());
        if (optionalAddress.isEmpty()) {
            throw new NotFoundException("Address not found by id: " + request.getId());
        }
        Address address = optionalAddress.get();
        address.setCity(request.getCity());
        address.setStreet(request.getStreet());
        Address createdAddress = addressRepository.save(address);
        return addressResponseMapper.map(createdAddress);
    }

}
