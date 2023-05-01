package com.example.company.Notifications;

import com.example.company.Customer.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.ejb.Stateful;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Stateful
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private Customer customer;

    private String message;
}
