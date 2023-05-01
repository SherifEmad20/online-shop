package com.example.company.Selling;

import com.example.company.Product.Product;
import jakarta.ejb.Stateful;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "selling_company")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SellingCompany {

    @Id
    private String companyName;
    private String username;
    private String password;

    @OneToMany(mappedBy = "sellingCompany", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
}
