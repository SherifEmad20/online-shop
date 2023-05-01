package com.example.company.Admin;

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
    public String loginAdmin(@Context HttpServletRequest request, Admin admin) {
        return adminBean.loginAdmin(request, admin);
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
    public String updateAdmin(@Context HttpServletRequest request, Admin admin) {

        return adminBean.updateAdmin(request, admin);

    }

    @DELETE
    @Path("/deleteAdmin/{id}")
    public String deleteAdmin(@PathParam("id") Long id) {

        return adminBean.deleteAdmin(id);
    }


    @POST
    @Path("/createSellingCompany")
    public String callCreateSelling(@Context HttpServletRequest request, String requestBody) throws Exception {

        return adminBean.callCreateSelling(request, requestBody);

    }

    @POST
    @Path("/createShippingCompany")
    public String callCreateShipping(@Context HttpServletRequest request, String requestBody) throws Exception {

        return adminBean.callCreateShipping(request, requestBody);

    }

    @GET
    @Path("/getSellingCompanies")
    public String getSellingCompanies(@Context HttpServletRequest request) throws Exception {

        return adminBean.getSellingCompanies(request);
    }

    @GET
    @Path("/getShippingCompanies")
    public String getShippingCompanies(@Context HttpServletRequest request) throws Exception {

        return adminBean.getShippingCompanies(request);
    }

    @GET
    @Path("/getAllCustomers")
    public String getAllCustomers(@Context HttpServletRequest request) throws Exception {

        return adminBean.getAllCustomers(request);
    }
}
