package com.proje.repository;

import com.proje.model.entity.Account;
import com.proje.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select a.addresses from Account a where a.id = ?1")
    List<Address> findAccountAddressesById(Long id);
}
