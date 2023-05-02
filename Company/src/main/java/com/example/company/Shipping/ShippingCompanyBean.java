package com.example.company.Shipping;

import com.example.company.Customer.Customer;
import com.example.company.Order.Order;
import jakarta.annotation.Resource;
import jakarta.ejb.Stateful;
import jakarta.jms.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.http.HttpServletRequest;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.security.SecureRandom;
import java.util.List;

@Stateful
public class ShippingCompanyBean {


    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("companyPU");
    private final EntityManager entityManager = emf.createEntityManager();

    @Resource(mappedName = "java:/jms/queue/myOrders")
    private Queue queue;


    public String generateRandomPassword(int length) {
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()_+-=[]{}|;:,./<>?";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }
        return password.toString();
    }


    // add shipping company
    public String addShippingCompany(ShippingCompany shippingCompany) {
        entityManager.getTransaction().begin();
        shippingCompany.setPassword(generateRandomPassword(8));
        entityManager.persist(shippingCompany);
        entityManager.getTransaction().commit();
        return "Shipping company added successfully!";
    }

    // login shipping company
    public String loginShippingCompany(ShippingCompany shippingCompany) {
        ShippingCompany shippingCompanyFromDB = entityManager.find(ShippingCompany.class, shippingCompany.getCompanyName());
        if (shippingCompanyFromDB.getPassword().equals(shippingCompany.getPassword())) {
            return "Shipping company logged in successfully!";
        } else {
            return "Shipping company login failed!";
        }
    }

    // get all shipping companies
    public List<ShippingCompany> getAllShippingCompanies() {
        return entityManager.createQuery("SELECT s FROM ShippingCompany s", ShippingCompany.class).getResultList();
    }

    // update shipping company
    public String updateShippingCompany(ShippingCompany shippingCompany) {
        ShippingCompany sippingCompanyFromDB = entityManager.find(ShippingCompany.class, shippingCompany.getCompanyName());

        entityManager.getTransaction().begin();
        sippingCompanyFromDB.setCompanyName(shippingCompany.getCompanyName());
        sippingCompanyFromDB.setUsername(shippingCompany.getUsername());
        sippingCompanyFromDB.setPassword(shippingCompany.getPassword());
        entityManager.getTransaction().commit();
        return "Shipping company updated successfully!";
    }

    // delete shipping company
    public String deleteShippingCompany(String companyName) {
        ShippingCompany shippingCompanyFromDB = entityManager.find(ShippingCompany.class, companyName);
        entityManager.getTransaction().begin();
        entityManager.remove(shippingCompanyFromDB);
        entityManager.getTransaction().commit();
        return "Shipping company deleted successfully!";
    }

    public String addLocation(String username, Location location) {
        ShippingCompany company = entityManager.find(ShippingCompany.class, username);
        location.setShippingCompany(company);
        company.getLocations().add(location);
        entityManager.getTransaction().begin();
        entityManager.merge(company);
        entityManager.getTransaction().commit();
        return "Location added successfully!";
    }
}
