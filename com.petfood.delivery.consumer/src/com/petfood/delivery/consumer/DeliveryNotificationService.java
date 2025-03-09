package com.petfood.delivery.consumer;

public interface DeliveryNotificationService {
    void notifyCustomer(String orderId, String trackingNumber, String customerContact);
}