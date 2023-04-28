package com.example.company.Customer;

import com.example.company.Order.Order;
import com.example.company.Product.Product;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
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
    public String loginCustomer(@Context HttpServletRequest request, Customer customer) {

        return customerBean.loginCustomer(request, customer);
    }

    // method to get all customers
    @GET
    @Path("/getAllCustomers")
    public List<Customer> getAllCustomers() {
        return customerBean.getAllCustomers();
    }

    @PUT
    @Path("/updateCustomer/{username}")
    public String updateCustomer(@Context HttpServletRequest request, @PathParam("username") String username, Customer customer) {
        return customerBean.updateCustomer(request, username, customer);
    }

    // add products to cart
    @PUT
    @Path("/addToCart/{username}/{productId}")
    public String addToCart(@Context HttpServletRequest request, @PathParam("username") String username, @PathParam("productId") Long productId) {
        return customerBean.addToCart(request, username, productId);

    }

    // delete products from cart
    @DELETE
    @Path("/deleteFromCart/{username}/{productId}")
    public String deleteFromCart(@Context HttpServletRequest request, @PathParam("username") String username, @PathParam("productId") Long productId) {
        return customerBean.deleteFromCart(request, username, productId);
    }

    @GET
    @Path("/shippingOrders")
    public List<Order> getAllShippingOrders(@Context HttpServletRequest request) {
        return customerBean.getAllShippingOrders(request);
    }

    @GET
    @Path("/pendingOrders")
    public List<Order> getAllPendingOrders(@Context HttpServletRequest request) {
        return customerBean.getAllPendingOrders(request);
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
    public List<Product> getCart(@Context HttpServletRequest request, @PathParam("username") String username) {
        return customerBean.getCustomerCart(request, username);
    }

    // get all notification messages for a customer
    @GET
    @Path("/getNotifications/{username}")
    public List<String> getNotifications(@Context HttpServletRequest request, @PathParam("username") String username) {
        return customerBean.getAllNotifications(request, username);
    }

}
