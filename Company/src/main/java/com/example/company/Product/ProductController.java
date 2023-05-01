package com.example.company.Product;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ProductController {


    @EJB
    private ProductBean productBean;

    // add product
    @POST
    @Path("/addProduct")
    public String addProduct(Product product) {
        return productBean.addProduct(product);

    }

    // get all products
    @GET
    @Path("/getAllProducts")
    public List<Product> getAllProducts() {
        return productBean.getAllProducts();
    }

    // update product
    @PUT
    @Path("/updateProduct")
    public String updateProduct(Product product) {
        return productBean.updateProduct(product);
    }

    // delete product
    @DELETE
    @Path("/deleteProduct/{productName}")
    public String deleteProduct(@PathParam("productName") String productName) {
        return productBean.deleteProduct(productName);
    }
}
