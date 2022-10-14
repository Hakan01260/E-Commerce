package com.proje.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date createDate;
    @OneToMany(mappedBy = "account",fetch = FetchType.LAZY)
    private List<Address> addresses = new ArrayList<>();

    public Account(String firstName, String lastName, String phoneNumber, Date createDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
    }

}
