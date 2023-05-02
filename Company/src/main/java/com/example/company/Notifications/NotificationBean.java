package com.example.company.Notifications;

import com.example.company.Customer.Customer;
import jakarta.ejb.MessageDriven;
import jakarta.enterprise.context.SessionScoped;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.Serializable;

@MessageDriven(
        activationConfig = {
                @jakarta.ejb.ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
                @jakarta.ejb.ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/myOrders")
        },
        mappedName = "java:/jms/queue/myOrders", name = "OrderMessageBean")
@SessionScoped
public class NotificationBean implements MessageListener, Serializable{
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("companyPU");
    private final EntityManager entityManager = emf.createEntityManager();


    @Override
    public void onMessage(Message message) {
        try {
            String[] orderRequest = message.getBody(String.class).split(",");
            String customerName = orderRequest[0];
            String orderMessage = orderRequest[1];

            System.out.println("Customer name: " + customerName);
            System.out.println("Order message: " + orderMessage);

            // process the order request here
            // send notification to customer
            Customer customer = entityManager.find(Customer.class, customerName);
            Notifications notifications = new Notifications();
            notifications.setCustomer(customer);
            notifications.setMessage(orderMessage);
            customer.getNotifications().add(notifications);


        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
