package com.example.company.Order;

import com.example.company.Customer.Customer;
import com.example.company.Product.Product;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class OrderBean {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("companyPU");
    private final EntityManager entityManager = emf.createEntityManager();


    // add order
    public String addOrder(Order order) {
        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.getTransaction().commit();
        return "Order added successfully!";
    }

    // update order
    public String updateOrder(Long orderId, Order order) {
        try {
            Order orderFromDB = entityManager.find(Order.class, orderId);
            orderFromDB.setOrderDate(order.getOrderDate());
            orderFromDB.setOrderStatus(order.getOrderStatus());
            orderFromDB.setOrderTotal(order.getOrderTotal());
            entityManager.getTransaction().begin();
            entityManager.merge(orderFromDB);
            entityManager.getTransaction().commit();
            return "Order updated successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Order update failed!";
        }
    }

    // delete order
    public String deleteOrder(Long orderId) {
        try {
            Order orderFromDB = entityManager.find(Order.class, orderId);
            entityManager.getTransaction().begin();
            entityManager.remove(orderFromDB);
            entityManager.getTransaction().commit();
            return "Order deleted successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Order delete failed!";
        }
    }

    // get all orders
    public List<Order> getAllOrders() {
        return entityManager.createQuery("SELECT o FROM Order o", Order.class).getResultList();
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

    public String makeOrder(String username) {
        try {
            Customer customer = entityManager.find(Customer.class, username);
            if (customer.getCart().size() == 0) return "Please add products to your cart first!";

            // calculate total price
            double totalPrice = 0;
            List<Product> cartProducts = customer.getCart();

            for (Product product : cartProducts) {
                totalPrice += product.getProductPrice();
            }

            if (customer.getBalance() < totalPrice) return "You don't have enough money to make this order!";

            customer.setBalance(customer.getBalance() - totalPrice);

            // create order
            Order order = new Order();
            order.setCustomerUsername(customer.getUsername());
            order.setOrderDate(java.time.LocalDate.now().toString());
            order.setOrderTotal(totalPrice);
            order.setOrderStatus("pending");

            // add products to order
            List<Product> orderProducts = new ArrayList<>();
            for (Product product : cartProducts) {
                // Set the order for the product to this order
                product.setOrder(order);
                orderProducts.add(product);
            }
            order.setProducts(orderProducts);

            entityManager.getTransaction().begin();
            entityManager.persist(order);
            entityManager.getTransaction().commit();

            // clear customer cart and add products to order
            for (int i = 0; i < customer.getCart().size(); i++) {
                deleteFromCart(username, customer.getCart().get(i).getProductId());
            }

            return "Order made successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Order making failed!";
        }
    }

    // return order made by customer with username
    public List<Order> getOrdersByCustomer(String username) {
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.customerUsername = :username", Order.class)
                .setParameter("username", username)
                .getResultList();
    }

    // return order made by customer with username and the order status is pending
    public List<Order> getPendingOrdersByCustomer(String username) {
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.customerUsername = :username AND o.orderStatus = :status", Order.class)
                .setParameter("username", username)
                .setParameter("status", "pending")
                .getResultList();
    }

    public List<Order> getShippingOrdersByCustomer(String username) {
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.customerUsername = :username AND o.orderStatus = :status", Order.class)
                .setParameter("username", username)
                .setParameter("status", "shipping")
                .getResultList();
    }

}
