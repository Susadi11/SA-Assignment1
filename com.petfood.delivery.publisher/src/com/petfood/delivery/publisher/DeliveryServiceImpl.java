package com.petfood.delivery.publisher;

public class DeliveryServiceImpl implements DeliveryService {
    @Override
    public void processDelivery(String orderId, String customerContact) {
        System.out.println("🚚 Delivery for Order ID: " + orderId + " is being processed.");
        System.out.println("📩 Sending delivery update to: " + customerContact);
    }
}
