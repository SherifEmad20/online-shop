package com.example.company.Customer;

import com.example.company.Order.Order;
import com.example.company.Product.Product;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;


import java.util.List;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerController {

    @EJB
    private CustomerBean customerBean;

    @POST
    @Path("/register")
    public String registerCustomer(Customer customer) {
        return customerBean.registerCustomer(customer);
    }

    @POST
    @Path("/login")
    public String loginCustomer(Customer customer) {

        return customerBean.loginCustomer(customer);
    }

    // method to get all customers
    @GET
    @Path("/getAllCustomers")
    public List<Customer> getAllCustomers() {
        return customerBean.getAllCustomers();
    }

    @PUT
    @Path("/updateCustomer/{username}")
    public String updateCustomer(@PathParam("username") String username, Customer customer) {
        return customerBean.updateCustomer(username, customer);
    }

    // add products to cart
    @PUT
    @Path("/addToCart/{username}/{productId}")
    public String addToCart(@PathParam("username") String username, @PathParam("productId") Long productId) {
        return customerBean.addToCart(username, productId);

    }

    // delete products from cart
    @DELETE
    @Path("/deleteFromCart/{username}/{productId}")
    public String deleteFromCart(@PathParam("username") String username, @PathParam("productId") Long productId) {
        return customerBean.deleteFromCart(username, productId);
    }

    @GET
    @Path("/shippingOrders")
    public List<Order> getAllShippingOrders() {
        return customerBean.getAllShippingOrders();
    }

    @GET
    @Path("/pendingOrders")
    public List<Order> getAllPendingOrders() {
        return customerBean.getAllPendingOrders();
    }

//    // get notifications
//    @GET
//    @Path("/getNotifications/{username}")
//    public List<String> getNotifications(@PathParam("username") String username) {
//        return customerBean.getAllNotifications(username);
//    }

    // return customer cart
    @GET
    @Path("/getCart/{username}")
    public List<Product> getCart(@PathParam("username") String username) {
        return customerBean.getCustomerCart(username);
    }

    // get all notification messages for a customer
    @GET
    @Path("/getNotifications/{username}")
    public List<String> getNotifications(@PathParam("username") String username) {
        return customerBean.getAllNotifications(username);
    }

}
