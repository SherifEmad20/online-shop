package com.example.company.Customer;

import com.example.company.Order.Order;
import com.example.company.Product.Product;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateful;
import jakarta.enterprise.context.SessionScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateful
@SessionScoped
public class CustomerBean implements Serializable {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("companyPU");
    private final EntityManager entityManager = emf.createEntityManager();

    @EJB
    private Customer customer;

    public String registerCustomer(Customer customer) {
        entityManager.getTransaction().begin();
        entityManager.persist(customer);
        entityManager.getTransaction().commit();
        return "Customer registered successfully!\n" + "Welcome Customer: " + customer.getUsername();
    }

    public String loginCustomer(@Context HttpServletRequest request, Customer customer) {
        Customer customerFromDB = entityManager.find(Customer.class, customer.getUsername());
        if (customerFromDB.getPassword().equals(customer.getPassword())) {
            this.customer = customerFromDB;
            request.getSession(true).setAttribute("customer", customer);
            return "Customer logged in successfully!";
        } else {
            return "Customer login failed!";
        }
    }

    public List<Customer> getAllCustomers() {
        return entityManager.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }

    public String updateCustomer(@Context HttpServletRequest request, String username, Customer customer) {
        try {
            if (request.getSession().getAttribute("customer") == null)
                return "Please login first!";

            Customer customerFromDB = entityManager.find(Customer.class, username);
            this.customer = customerFromDB;
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

    public String addToCart(@Context HttpServletRequest request, String username, Long productId) {
        try {
            if (request.getSession().getAttribute("customer") == null)
                return "Please login first!";

            Product product = entityManager.find(Product.class, productId);
            Customer customerFromDB = entityManager.find(Customer.class, username);
            this.customer = customerFromDB;
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

    public String deleteFromCart(@Context HttpServletRequest request, String username, Long productId) {
        try {
            if (request.getSession().getAttribute("customer") == null)
                return "Please login first!";

            Product product = entityManager.find(Product.class, productId);
            Customer customerFromDB = entityManager.find(Customer.class, username);
            this.customer = customerFromDB;
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

    public List<Order> getAllShippingOrders(@Context HttpServletRequest request) {
        if (request.getSession().getAttribute("customer") == null)
            return null;
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.orderStatus = 'shipping'", Order.class).getResultList();
    }

    public List<Order> getAllPendingOrders(@Context HttpServletRequest request) {
        if (request.getSession().getAttribute("customer") == null)
            return null;
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.orderStatus = 'pending'", Order.class).getResultList();
    }


    // return customer cart
    public List<Product> getCustomerCart(@Context HttpServletRequest request, String username) {
        if (request.getSession().getAttribute("customer") == null)
            return null;

        // get customer from db
        Customer customerFromDB = entityManager.find(Customer.class, username);
        this.customer = customerFromDB;
        // get cart from customer
        List<Product> cart = customerFromDB.getCart();
        return cart;
    }

    // get all notification messages for a customer
    public List<String> getAllNotifications(@Context HttpServletRequest request, String username) {
        if (request.getSession().getAttribute("customer") == null) {
            // create a list and add a message please login first
            List<String> notifications = new ArrayList<>();
            notifications.add("Please login first!");
            return notifications;
        }

        // get customer from db
        Customer customerFromDB = entityManager.find(Customer.class, username);
        this.customer = customerFromDB;
        // get notifications from db
        List<String> notifications = new ArrayList<>();
        for (int i = 0; i < customerFromDB.getNotifications().size(); i++) {
            notifications.add(customerFromDB.getNotifications().get(i).getMessage());
        }
        return notifications;
    }
}
