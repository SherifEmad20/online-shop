package com.example.company.Customer;

import com.example.company.Order.Order;
import com.example.company.Product.Product;
import jakarta.annotation.ManagedBean;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

import java.io.Serializable;
import java.util.List;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ManagedBean
@SessionScoped
public class CustomerController implements Serializable {

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
    @Path("/updateCustomer")
    public String updateCustomer(@Context HttpServletRequest request, Customer customer) {
        return customerBean.updateCustomer(request, customer);
    }

    // add products to cart
    @PUT
    @Path("/addToCart/{productId}")
    public String addToCart(@Context HttpServletRequest request, @PathParam("productId") Long productId) {
        return customerBean.addToCart(request, productId);

    }

    // delete products from cart
    @DELETE
    @Path("/deleteFromCart/{productId}")
    public String deleteFromCart(@Context HttpServletRequest request, @PathParam("productId") Long productId) {
        return customerBean.deleteFromCart(request, productId);
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

    @GET
    @Path("/getCart")
    public List<Product> getCart(@Context HttpServletRequest request) {
        return customerBean.getCustomerCart(request);
    }

    // get all notification messages for a customer
    @GET
    @Path("/getNotifications")
    public List<String> getNotifications(@Context HttpServletRequest request) {
        return customerBean.getAllNotifications(request);
    }

    @POST
    @Path("/makeOrder")
    public String makeOrder(@Context HttpServletRequest request) {
        return customerBean.makeOrder(request);
    }

    // make a shipping request
    @POST
    @Path("/makeShippingRequest/{shippingCompanyName}")
    public String makeShippingRequest(@PathParam("shippingCompanyName") String shippingCompanyName) {
        return customerBean.requestShipping(shippingCompanyName);
    }


}
