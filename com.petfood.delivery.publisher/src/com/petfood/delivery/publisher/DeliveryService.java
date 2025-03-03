package com.petfood.delivery.publisher;

public interface DeliveryService {
    void processDelivery(String orderId, String customerContact);
}