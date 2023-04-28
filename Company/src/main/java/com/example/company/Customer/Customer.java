package com.example.company.Customer;

import com.example.company.Notifications.Notifications;
import com.example.company.Product.Product;
import jakarta.ejb.Stateful;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Stateful
public class Customer {

    @Id
    private String username;
    private String email;
    private String password;
    private String address;
    private double balance;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> cart = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Notifications> notifications = new ArrayList<>();

}
