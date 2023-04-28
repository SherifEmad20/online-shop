package com.example.company.Shipping;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/shippingCompany")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShippingCompanyController {

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

    // make a shipping request
    @POST
    @Path("/makeShippingRequest/{shippingCompanyName}/{username}/{orderId}")
    public String makeShippingRequest(@PathParam("shippingCompanyName") String shippingCompanyName,
                                      @PathParam("username") String username,
                                      @PathParam("orderId") Long orderId) {
        return shippingCompanyBean.requestShipping(shippingCompanyName, username, orderId);
    }

}
