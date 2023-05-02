package com.example.company.Shipping;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shipping_company")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShippingCompany {

    @Id
    private String companyName;
    private String username;
    private String password;

    @OneToMany(mappedBy = "shippingCompany", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Location> locations = new ArrayList<>();


}
