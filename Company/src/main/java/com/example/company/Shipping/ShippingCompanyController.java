package com.example.company.Shipping;

import jakarta.annotation.ManagedBean;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

import java.io.Serializable;
import java.util.List;

@Path("/shippingCompany")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ManagedBean
@SessionScoped
public class ShippingCompanyController implements Serializable {

    @EJB
    private ShippingCompanyBean shippingCompanyBean;


    // add shipping company
    @POST
    @Path("/addShippingCompany")
    public String addShippingCompany(ShippingCompany shippingCompany) {
        return shippingCompanyBean.addShippingCompany(shippingCompany);
    }

    // login shipping company
    @POST
    @Path("/loginShippingCompany")
    public String loginShippingCompany(ShippingCompany shippingCompany) {
        return shippingCompanyBean.loginShippingCompany(shippingCompany);
    }

    // get all shipping companies
    @GET
    @Path("/getAllShippingCompanies")
    public List<ShippingCompany> getAllShippingCompanies() {
        return shippingCompanyBean.getAllShippingCompanies();
    }

    // update shipping company
    @PUT
    @Path("/updateShippingCompany")
    public String updateShippingCompany(ShippingCompany shippingCompany) {
        return shippingCompanyBean.updateShippingCompany(shippingCompany);
    }

    // delete shipping company
    @DELETE
    @Path("/deleteShippingCompany/{companyName}")
    public String deleteShippingCompany(@PathParam("companyName") String companyName) {
        return shippingCompanyBean.deleteShippingCompany(companyName);
    }

    @PUT
    @Path("/addLocation/{username}")
    public String addLocation(@PathParam("username") String username, Location location) {
        return shippingCompanyBean.addLocation(username, location);
    }
}
