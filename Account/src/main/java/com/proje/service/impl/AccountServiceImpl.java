package com.proje.service.impl;

import com.proje.mapper.AccountResponseMapper;
import com.proje.mapper.AddressResponseMapper;
import com.proje.model.api.request.AccountCreateRequest;
import com.proje.model.api.request.AccountUpdateRequest;
import com.proje.model.api.response.AccountResponse;
import com.proje.model.api.response.AddressResponse;
import com.proje.model.entity.Account;
import com.proje.model.exceptions.NotFoundException;
import com.proje.repository.AccountRepository;
import com.proje.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountResponseMapper accountResponseMapper;
    private final AddressResponseMapper addressResponseMapper;

    @Transactional(readOnly = true)
    public List<AccountResponse> findAll(){
        return accountRepository.findAll()
                .stream()
                .map(accountResponseMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AccountResponse findById(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("Account not found by id :" + id);
        }
        return accountResponseMapper.map(optionalAccount.get());
    }

    @Transactional
    public AccountResponse create(AccountCreateRequest request) {
        Account account = new Account();
        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());
        account.setPhoneNumber(request.getPhoneNumber());
        account.setCreateDate(new Date());

        Account createdAccount = accountRepository.save(account);
        return accountResponseMapper.map(createdAccount);
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("Account not found by id: " + id);
        }
        accountRepository.deleteById(optionalAccount.get().getId());
    }

    @Transactional
    public AccountResponse update(AccountUpdateRequest request) {
        Optional<Account> optionalAccount = accountRepository.findById(request.getId());
        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("Account not found by id: " + request.getId());
        }
        Account account = optionalAccount.get();
        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());
        account.setPhoneNumber(request.getPhoneNumber());

        Account createdAccount = accountRepository.save(account);
        return accountResponseMapper.map(createdAccount);
    }
    @Transactional(readOnly = true)
    public List<AddressResponse> findAccountAddressesById(Long id){
        return accountRepository.findAccountAddressesById(id).stream().map(addressResponseMapper::map).collect(Collectors.toList());
    }


}
