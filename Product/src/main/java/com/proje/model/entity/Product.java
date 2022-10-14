package com.proje.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    public Product(String productName) {
        this.productName = productName;
    }
}
