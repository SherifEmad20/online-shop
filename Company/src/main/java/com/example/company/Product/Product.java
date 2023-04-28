package com.example.company.Product;

import com.example.company.Customer.Customer;
import com.example.company.Order.Order;
import com.example.company.Selling.SellingCompany;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Stateless
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;
    private String productDescription;
    private String productImage;
    private double productPrice;
    private int productQuantity;
    private boolean sold;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private SellingCompany sellingCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Order order;

}

