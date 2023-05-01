package com.example.company.Admin;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Singleton
public class AdminBean {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("companyPU");
    private final EntityManager entityManager = emf.createEntityManager();


    public String registerAdmin(Admin admin) {
        entityManager.getTransaction().begin();
        entityManager.persist(admin);
        entityManager.getTransaction().commit();
        return "Admin registered successfully!";
    }

    public String loginAdmin(Admin admin) {
        Admin adminFromDB = entityManager.find(Admin.class, admin.getEmail());
        if (adminFromDB.getPassword().equals(admin.getPassword())) {
            return "Admin logged in successfully!";
        } else {
            return "Admin login failed!";
        }
    }


    public List<Admin> getAllAdmins() {
        return entityManager.createQuery("SELECT a FROM Admin a", Admin.class).getResultList();
    }


    public Admin getAdminByUsername(Long id) {
        return entityManager.find(Admin.class, id);

    }

    public String updateAdmin(Admin admin) {
        entityManager.getTransaction().begin();
        admin.setEmail(admin.getEmail());
        admin.setName(admin.getName());
        admin.setPassword(admin.getPassword());
        entityManager.merge(admin);
        entityManager.getTransaction().commit();
        return "Admin updated successfully!";
    }

    public String deleteAdmin(Long id) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Admin.class, id));
        entityManager.getTransaction().commit();
        return "Admin deleted successfully!";
    }


    public String callCreateSelling(String requestBody) throws Exception {


        String BASE_URL = "http://localhost:18072/Company-1.0-SNAPSHOT/api/sellingCompany";
        HttpResponse<String> response = HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/addSellingCompany"))
                .header("Content-Type", "application/json")
                .timeout(java.time.Duration.ofMinutes(1))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build(), HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String callCreateShipping(String requestBody) throws Exception {

        String BASE_URL = "http://localhost:18072/Company-1.0-SNAPSHOT/api/shippingCompany";
        HttpResponse<String> response = HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/addShippingCompany"))
                .header("Content-Type", "application/json")
                .timeout(java.time.Duration.ofMinutes(1))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build(), HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String getSellingCompanies() throws Exception {

        String BASE_URL = "http://localhost:18072/Company-1.0-SNAPSHOT/api/sellingCompany";
        HttpResponse<String> response = HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getAllSellingCompanies"))
                .header("Content-Type", "application/json")
                .timeout(java.time.Duration.ofMinutes(1))
                .GET()
                .build(), HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String getShippingCompanies() throws Exception {

        String BASE_URL = "http://localhost:18072/Company-1.0-SNAPSHOT/api/shippingCompany";
        HttpResponse<String> response = HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getAllShippingCompanies"))
                .header("Content-Type", "application/json")
                .timeout(java.time.Duration.ofMinutes(1))
                .GET()
                .build(), HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String getAllCustomers() throws Exception {

        String BASE_URL = "http://localhost:18072/Company-1.0-SNAPSHOT/api/customer";
        HttpResponse<String> response = HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getAllCustomers"))
                .header("Content-Type", "application/json")
                .timeout(java.time.Duration.ofMinutes(1))
                .GET()
                .build(), HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

}
