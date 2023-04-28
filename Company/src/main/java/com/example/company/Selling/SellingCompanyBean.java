package com.example.company.Selling;

import com.example.company.Product.Product;
import jakarta.ejb.Stateful;
import jakarta.enterprise.context.SessionScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.List;

@Stateful
@SessionScoped
public class SellingCompanyBean implements Serializable {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("companyPU");
    private final EntityManager entityManager = emf.createEntityManager();


    public String generateRandomPassword(int length) {
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()_+-=[]{}|;,./<>?";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }
        return password.toString();
    }

    // add selling company
    public String addSellingCompany(SellingCompany sellingCompany) {
        entityManager.getTransaction().begin();
        sellingCompany.setPassword(generateRandomPassword(8));
        entityManager.persist(sellingCompany);
        entityManager.getTransaction().commit();
        return "Selling company added successfully!";
    }

    // login selling company
    public String loginSellingCompany(SellingCompany sellingCompany) {
        SellingCompany sellingCompanyFromDB = entityManager.find(SellingCompany.class, sellingCompany.getCompanyName());
        if (sellingCompanyFromDB.getPassword().equals(sellingCompany.getPassword())) {
            return "Selling company logged in successfully!";
        } else {
            return "Selling company login failed!";
        }
    }

    // get all selling companies
    public List<SellingCompany> getAllSellingCompanies() {
        return entityManager.createQuery("SELECT s FROM SellingCompany s", SellingCompany.class).getResultList();
    }

    // get company by id
    public SellingCompany getCompanyById(String id) {
        return entityManager.find(SellingCompany.class, id);
    }

    // update selling company
    public String updateSellingCompany(SellingCompany sellingCompany) {

        SellingCompany sellingCompanyFromDB = entityManager.find(SellingCompany.class, sellingCompany.getCompanyName());
        entityManager.getTransaction().begin();
        sellingCompanyFromDB.setCompanyName(sellingCompany.getCompanyName());
        sellingCompanyFromDB.setUsername(sellingCompany.getUsername());
        sellingCompanyFromDB.setPassword(sellingCompany.getPassword());
        entityManager.getTransaction().commit();
        return "Selling company updated successfully!";
    }

    // delete selling company
    public String deleteSellingCompany(String id) {
        SellingCompany sellingCompany = entityManager.find(SellingCompany.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(sellingCompany);
        entityManager.getTransaction().commit();
        return "Selling company deleted successfully!";
    }

    // add product to selling company
    public String addProduct(String username, Product product) {
        SellingCompany company = entityManager.find(SellingCompany.class, username);

        product.setSellingCompany(company);
        company.getProducts().add(product);

        entityManager.getTransaction().begin();
        entityManager.merge(company);
        entityManager.getTransaction().commit();
        return "Product added successfully!";
    }

    // update product by id
    public String updateProduct(Long id, Product product) {

        Product product1 = entityManager.find(Product.class, id);
        product1.setProductName(product.getProductName());
        product1.setProductPrice(product.getProductPrice());
        product1.setProductQuantity(product.getProductQuantity());
        entityManager.getTransaction().begin();
        entityManager.merge(product1);
        entityManager.getTransaction().commit();
        return "Product updated successfully!";
    }

    // delete product
    public String deleteProduct(Long id) {

        Product product = entityManager.find(Product.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(product);
        entityManager.getTransaction().commit();
        return "Product deleted successfully!";
    }

}
