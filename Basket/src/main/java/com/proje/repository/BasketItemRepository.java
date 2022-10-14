package com.proje.repository;

import com.proje.model.entity.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {

    @Query("select b from BasketItem b where b.accountId = ?1")
    List<BasketItem> findByAccountId(Long accountId);

    @Query("select p from BasketItem p where p.productId = ?1")
    List<BasketItem> findByProductId(Long productId);

}
