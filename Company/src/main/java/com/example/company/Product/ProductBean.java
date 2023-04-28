package com.example.company.Product;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

@Stateless
public class ProductBean {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("companyPU");
    private final EntityManager entityManager = emf.createEntityManager();


    // add product
    public String addProduct(Product product) {
        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();
        return "Product added successfully!";
    }

    // get all products
    public List<Product> getAllProducts() {
        return entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    // update product
    public String updateProduct(Product product) {
        Product productFromDB = entityManager.find(Product.class, product.getProductName());
        entityManager.getTransaction().begin();
        productFromDB.setProductName(product.getProductName());
        productFromDB.setProductDescription(product.getProductDescription());
        productFromDB.setProductImage(product.getProductImage());
        productFromDB.setProductPrice(product.getProductPrice());
        productFromDB.setProductQuantity(product.getProductQuantity());
        productFromDB.setSold(product.isSold());
        entityManager.getTransaction().commit();
        return "Product updated successfully!";
    }

    // delete product
    public String deleteProduct(String productName) {
        Product productFromDB = entityManager.find(Product.class, productName);
        entityManager.getTransaction().begin();
        entityManager.remove(productFromDB);
        entityManager.getTransaction().commit();
        return "Product deleted successfully!";
    }

}
