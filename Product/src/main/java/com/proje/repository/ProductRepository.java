package com.proje.repository;

import com.proje.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    boolean existsByCategoryId(Long categoryId);
}
