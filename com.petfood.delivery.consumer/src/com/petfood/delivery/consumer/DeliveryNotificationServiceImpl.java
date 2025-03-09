package com.petfood.delivery.consumer;

import com.petfood.delivery.publisher.DeliveryService;
import com.petfood.delivery.util.DeliveryServiceLogger;

public class DeliveryNotificationServiceImpl implements DeliveryNotificationService {
    private DeliveryService deliveryService;
    
    public DeliveryNotificationServiceImpl(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
        DeliveryServiceLogger.info("DeliveryNotificationServiceImpl", "Initializing notification service...");
    }
    
    @Override
    public void notifyCustomer(String orderId, String trackingNumber, String customerContact) {
        // Simulate sending notification to customer
        String message = "Your order " + orderId + " has been shipped. Tracking number: " + trackingNumber;
        DeliveryServiceLogger.success("NotificationService", "Sent notification to " + customerContact + ": " + message);
        
        // Log the notification
        logNotification(orderId, customerContact);
    }
    
    private void logNotification(String orderId, String customerContact) {
        DeliveryServiceLogger.info("NotificationService", "Notification logged for order: " + orderId + " to " + customerContact);
    }
}