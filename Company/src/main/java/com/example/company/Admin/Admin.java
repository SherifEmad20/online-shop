package com.example.company.Admin;

import jakarta.ejb.Singleton;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admin")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Singleton
public class Admin {

    @Id
    private String email;
    private String name;
    private String password;
}
