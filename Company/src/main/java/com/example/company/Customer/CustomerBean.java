package com.example.company.Customer;

import com.example.company.Order.Order;
import com.example.company.Product.Product;
import com.example.company.Shipping.ShippingCompany;
import jakarta.ejb.MessageDriven;
import jakarta.ejb.Stateful;
import jakarta.enterprise.context.SessionScoped;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.management.Notification;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateful
@SessionScoped
public class CustomerBean implements Serializable {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("companyPU");
    private final EntityManager entityManager = emf.createEntityManager();

    public String registerCustomer(Customer customer) {
        entityManager.getTransaction().begin();
        entityManager.persist(customer);
        entityManager.getTransaction().commit();
        return "Customer registered successfully!\n" + "Welcome Customer: " + customer.getUsername();
    }

    public String loginCustomer(Customer customer) {
        Customer customerFromDB = entityManager.find(Customer.class, customer.getUsername());
        if (customerFromDB.getPassword().equals(customer.getPassword())) {
            return "Customer logged in successfully!";
        } else {
            return "Customer login failed!";
        }
    }

    public List<Customer> getAllCustomers() {
        return entityManager.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }

    public String updateCustomer(String username, Customer customer) {
        try {
            Customer customerFromDB = entityManager.find(Customer.class, username);
            customerFromDB.setUsername(customer.getUsername());
            customerFromDB.setEmail(customer.getEmail());
            customerFromDB.setPassword(customer.getPassword());
            customerFromDB.setAddress(customer.getAddress());
            entityManager.getTransaction().begin();
            entityManager.merge(customerFromDB);
            entityManager.getTransaction().commit();
            return "Customer updated successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Customer update failed!";
        }
    }

    public String addToCart(String username, Long productId) {
        try {
            Product product = entityManager.find(Product.class, productId);
            Customer customerFromDB = entityManager.find(Customer.class, username);
            product.setCustomer(customerFromDB);
            customerFromDB.getCart().add(product);
            entityManager.getTransaction().begin();
            entityManager.merge(customerFromDB);
            entityManager.getTransaction().commit();
            return "Product added to cart successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Product addition to cart failed!";
        }
    }

    public String deleteFromCart(String username, Long productId) {
        try {
            Product product = entityManager.find(Product.class, productId);
            Customer customerFromDB = entityManager.find(Customer.class, username);
            product.setCustomer(null);
            customerFromDB.getCart().remove(product);
            entityManager.getTransaction().begin();
            entityManager.merge(customerFromDB);
            entityManager.getTransaction().commit();
            return "Product deleted from cart successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Product deletion from cart failed!";
        }
    }

    public List<Order> getAllShippingOrders() {
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.orderStatus = 'shipping'", Order.class).getResultList();
    }

    public List<Order> getAllPendingOrders() {
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.orderStatus = 'pending'", Order.class).getResultList();
    }

//    // get messages from notification
//    public List<String> getAllNotifications(String username) {
//        // get customer from db
//        Customer customerFromDB = entityManager.find(Customer.class, username);
//        // get notifications from db
//        List<String> messages = entityManager.createQuery("SELECT n.message FROM Notifications n", String.class).getResultList();
//        return messages;
//    }


    // return customer cart
    public List<Product> getCustomerCart(String username) {
        // get customer from db
        Customer customerFromDB = entityManager.find(Customer.class, username);
        // get cart from customer
        List<Product> cart = customerFromDB.getCart();
        return cart;
    }

    // get all notification messages for a customer
    public List<String> getAllNotifications(String username) {
        // get customer from db
        Customer customerFromDB = entityManager.find(Customer.class, username);
        // get notifications from db
        List<String> notifications = new ArrayList<>();
        for (int i = 0; i < customerFromDB.getNotifications().size(); i++) {
            notifications.add(customerFromDB.getNotifications().get(i).getMessage());
        }
        return notifications;
    }
}
