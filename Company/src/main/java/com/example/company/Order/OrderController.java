package com.example.company.Order;

import jakarta.annotation.ManagedBean;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.io.Serializable;
import java.util.List;

@Path("/order")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ManagedBean
@RequestScoped
public class OrderController implements Serializable {

    @EJB
    private OrderBean orderBean;

    @POST
    @Path("/addOrder")
    public String addOrder(Order order) {
        return orderBean.addOrder(order);
    }

    @PUT
    @Path("/updateOrder/{orderId}")
    public String updateOrder(@PathParam("orderId") Long orderId, Order order) {
        return orderBean.updateOrder(orderId, order);
    }

    @DELETE
    @Path("/deleteOrder/{orderId}")
    public String deleteOrder(@PathParam("orderId") Long orderId) {
        return orderBean.deleteOrder(orderId);
    }

    @GET
    @Path("/getOrders")
    public List<Order> getOrders() {
        return orderBean.getAllOrders();
    }

//    @POST
//    @Path("/makeOrder/{username}")
//    public String makeOrder(@PathParam("username") String username) {
//        return orderBean.makeOrder(username);
//    }

//    @GET
//    @Path("/getOrdersByCustomer/{username}")
//    public List<Order> getOrdersByCustomer(@PathParam("username") String username) {
//        return orderBean.getOrdersByCustomer(username);
//    }
//
//    @GET
//    @Path("/getPendingOrders/{username}")
//    public List<Order> getPendingOrders(@PathParam("username") String username) {
//        return orderBean.getPendingOrdersByCustomer(username);
//    }

//    @GET
//    @Path("/getShippingOrders/{username}")
//    public List<Order> getShippingOrders(@PathParam("username") String username) {
//        return orderBean.getShippingOrdersByCustomer(username);
//    }


}
