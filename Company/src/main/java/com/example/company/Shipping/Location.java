package com.example.company.Shipping;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "location")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Stateless
public class Location {

    @Id
    private String locationCode;
    private String locationName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ShippingCompany shippingCompany;
}
