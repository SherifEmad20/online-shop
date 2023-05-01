package com.example.company.Customer;

import com.example.company.Order.Order;
import com.example.company.Product.Product;
import com.example.company.Shipping.ShippingCompany;
import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateful;
import jakarta.enterprise.context.SessionScoped;
import jakarta.jms.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Context;

import javax.naming.InitialContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateful
public class CustomerBean {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("companyPU");
    private final EntityManager entityManager = emf.createEntityManager();

    @EJB
    private Customer customer;

    @Resource(mappedName = "java:/jms/queue/myOrders")
    private Queue queue;

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


    public List<Customer> getAllCustomers(@Context HttpServletRequest request) {
        if (request.getSession().getAttribute("customer") == null)
            return null;

        return entityManager.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }

    public String updateCustomer(@Context HttpServletRequest request, Customer customer) {
        try {
            if (request.getSession().getAttribute("customer") == null)
                return "Please login first!";

            this.customer.setUsername(customer.getUsername());
            this.customer.setEmail(customer.getEmail());
            this.customer.setPassword(customer.getPassword());
            this.customer.setAddress(customer.getAddress());
            entityManager.getTransaction().begin();
            entityManager.merge(this.customer);
            entityManager.getTransaction().commit();
            return "Customer updated successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Customer update failed!";
        }
    }

    public String addToCart(@Context HttpServletRequest request, Long productId) {
        try {
            if (request.getSession().getAttribute("customer") == null)
                return "Please login first!";

            Product product = entityManager.find(Product.class, productId);
            product.setCustomer(this.customer);
            this.customer.getCart().add(product);
            entityManager.getTransaction().begin();
            entityManager.merge(this.customer);
            entityManager.getTransaction().commit();
            return "Product added to cart successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Product addition to cart failed!";
        }
    }

    public String deleteFromCart(@Context HttpServletRequest request, Long productId) {
        try {
            if (request.getSession().getAttribute("customer") == null)
                return "Please login first!";

            Product product = entityManager.find(Product.class, productId);
            product.setCustomer(null);
            this.customer.getCart().remove(product);
            entityManager.getTransaction().begin();
            entityManager.merge(this.customer);
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
        return entityManager.createQuery("SELECT o FROM Order o JOIN o.customers c WHERE c.username = :username AND o.orderStatus = 'shipping'", Order.class)
                .setParameter("username", this.customer.getUsername())
                .getResultList();
    }

    public List<Order> getAllPendingOrders(@Context HttpServletRequest request) {
        if (request.getSession().getAttribute("customer") == null)
            return null;
        return entityManager.createQuery("SELECT o FROM Order o JOIN o.customers c WHERE c.username = :username AND o.orderStatus = 'pending'", Order.class)
                .setParameter("username", this.customer.getUsername())
                .getResultList();
    }


    // return customer cart
    public List<Product> getCustomerCart(@Context HttpServletRequest request) {
        if (request.getSession().getAttribute("customer") == null)
            return null;

        // get customer from db
        // get cart from customer
        List<Product> cart = this.customer.getCart();
        return cart;
    }

    // get all notification messages for a customer
    public List<String> getAllNotifications(@Context HttpServletRequest request) {
        if (request.getSession().getAttribute("customer") == null) {
            // create a list and add a message please login first
            List<String> notifications = new ArrayList<>();
            notifications.add("Please login first!");
            return notifications;
        }

        // get customer from db
        // get notifications from db
        List<String> notifications = new ArrayList<>();
        for (int i = 0; i < this.customer.getNotifications().size(); i++) {
            notifications.add(this.customer.getNotifications().get(i).getMessage());
        }
        return notifications;
    }


    public String makeOrder(@Context HttpServletRequest request) {
        try {
            if (request.getSession().getAttribute("customer") == null)
                return "please login first!";


            if (this.customer.getCart().size() == 0) return "Please add products to your cart first!";

            // calculate total price
            double totalPrice = 0;
            List<Product> cartProducts = this.customer.getCart();

            for (Product product : cartProducts) {
                totalPrice += product.getProductPrice();
            }

            if (this.customer.getBalance() < totalPrice) return "You don't have enough money to make this order!";

            this.customer.setBalance(this.customer.getBalance() - totalPrice);

            // create order
            Order order = new Order();
            order.getCustomers().add(this.customer);
            order.setOrderDate(java.time.LocalDate.now().toString());
            order.setOrderTotal(totalPrice);
            order.setOrderStatus("pending");

            // add products to order
            List<Product> orderProducts = new ArrayList<>();
            for (Product product : cartProducts) {
                // Set the order for the product to this order
                product.setOrder(order);
                orderProducts.add(product);
                product.setProductQuantity(product.getProductQuantity() - 1);
            }
            order.setProducts(orderProducts);

            entityManager.getTransaction().begin();
            entityManager.persist(order);
            entityManager.getTransaction().commit();

            this.customer.setOrder(order);

            // clear customer cart and add products to order
            for (int i = 0; i < this.customer.getCart().size(); i++) {
                deleteFromCart(request, this.customer.getCart().get(i).getProductId());
            }

            return "Order made successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Order making failed!";
        }
    }


    public void submitOrder(String notification) {
        try {
            javax.naming.Context context = new InitialContext();
            ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("java:/ConnectionFactory");
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(this.queue);
            ObjectMessage message = session.createObjectMessage();
            message.setObject(notification);
            producer.send(message);
            session.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String requestShipping(String shippingCompanyName) {

        ShippingCompany shippingCompany = entityManager.find(ShippingCompany.class, shippingCompanyName);

        if (shippingCompany == null) {
            submitOrder(this.customer.getUsername() + ","
                    + shippingCompanyName + " doesn't exist");

            return "Shipping Company not found!";

        }

        for (int i = 0; i < shippingCompany.getLocations().size(); i++) {
            if (shippingCompany.getLocations().get(i).getLocationName().equals(this.customer.getAddress())) {
                this.customer.getOrder().setOrderStatus("shipping");
                entityManager.getTransaction().begin();
                entityManager.merge(this.customer.getOrder());
                entityManager.getTransaction().commit();

                submitOrder(this.customer.getUsername() + ", getting order shipped by company: "
                        + shippingCompanyName);

                return "Shipping requested successfully!";
            }
        }

        submitOrder(this.customer.getUsername() + ","
                + shippingCompanyName + " doesn't deliver to your location");
        return "Shipping Company can't deliver to your location";

    }


}
