package com.example.company.Admin;

import jakarta.annotation.ManagedBean;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ManagedBean
@RequestScoped
public class AdminController {
    @EJB
    private AdminBean adminBean;


    @POST
    @Path("/register")
    public String registerAdmin(Admin admin) {
        return adminBean.registerAdmin(admin);
    }

    @POST
    @Path("/login")
    public String loginAdmin(Admin admin) {
        return adminBean.loginAdmin(admin);
    }

    @GET
    @Path("/getAllAdmins")
    public List<Admin> getAllAdmins() {

        return adminBean.getAllAdmins();
    }


    @GET
    @Path("/{id}")
    public Admin getAdminByUsername(@PathParam("id") Long id) {

        return adminBean.getAdminByUsername(id);

    }

    @PUT
    @Path("/updateAdmin")
    public String updateAdmin(Admin admin) {

        return adminBean.updateAdmin(admin);

    }

    @DELETE
    @Path("/deleteAdmin/{id}")
    public String deleteAdmin(@PathParam("id") Long id) {

        return adminBean.deleteAdmin(id);
    }


    @POST
    @Path("/createSellingCompany")
    public String callCreateSelling(String requestBody) throws Exception {

        return adminBean.callCreateSelling(requestBody);

    }

    @POST
    @Path("/createShippingCompany")
    public String callCreateShipping(String requestBody) throws Exception {

        return adminBean.callCreateShipping(requestBody);

    }

    @GET
    @Path("/getSellingCompanies")
    public String getSellingCompanies() throws Exception {

        return adminBean.getSellingCompanies();
    }

    @GET
    @Path("/getShippingCompanies")
    public String getShippingCompanies() throws Exception {

        return adminBean.getShippingCompanies();
    }

    @GET
    @Path("/getAllCustomers")
    public String getAllCustomers(@Context HttpServletRequest request) throws Exception {

        return adminBean.getAllCustomers(request);
    }
}
