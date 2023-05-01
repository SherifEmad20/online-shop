package com.example.company.Selling;

import com.example.company.Product.Product;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.io.Serializable;
import java.util.List;

@Path("/sellingCompany")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class SellingCompanyController  {

    @EJB
    private SellingCompanyBean sellingCompanyBean;

    // add selling company
    @POST
    @Path("/addSellingCompany")
    public String addSellingCompany(SellingCompany sellingCompany) {
        return sellingCompanyBean.addSellingCompany(sellingCompany);
    }

    // login selling company
    @POST
    @Path("/loginSellingCompany")
    public String loginSellingCompany(SellingCompany sellingCompany) {
        return sellingCompanyBean.loginSellingCompany(sellingCompany);
    }

    // get all selling companies
    @GET
    @Path("/getAllSellingCompanies")
    public List<SellingCompany> getAllSellingCompanies() {
        return sellingCompanyBean.getAllSellingCompanies();
    }

    // get by id
    @GET
    @Path("/getSellingCompanyById/{id}")
    public SellingCompany getSellingCompanyById(@PathParam("id") String id) {
        return sellingCompanyBean.getCompanyById(id);
    }

    // update selling company
    @PUT
    @Path("/updateSellingCompany")
    public String updateSellingCompany(SellingCompany sellingCompany) {
        return sellingCompanyBean.updateSellingCompany(sellingCompany);
    }

    // delete selling company
    @DELETE
    @Path("/deleteSellingCompany/{id}")
    public String deleteSellingCompany(@PathParam("id") String id) {
        return sellingCompanyBean.deleteSellingCompany(id);
    }

    // add product to selling company
    @PUT
    @Path("/addProduct/{username}")
    public String addProduct(@PathParam("username") String username, Product product) {
        return sellingCompanyBean.addProduct(username, product);
    }

    // update product by id
    @PUT
    @Path("/updateProduct/{id}")
    public String updateProduct(@PathParam("id") Long id, Product product) {
        return sellingCompanyBean.updateProduct(id, product);
    }

    // delete product
    @DELETE
    @Path("/deleteProduct/{id}")
    public String deleteProduct(@PathParam("id") Long id) {
        return sellingCompanyBean.deleteProduct(id);
    }
}
