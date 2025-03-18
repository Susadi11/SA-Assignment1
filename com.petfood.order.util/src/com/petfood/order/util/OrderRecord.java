package com.petfood.order.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderRecord {
    private final String orderId;
    private final String productId;  // Added product ID
    private final String productName;
    private final int quantity;
    private final String timestamp;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public OrderRecord(String orderId, String productId, String productName, int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.timestamp = LocalDateTime.now().format(formatter);
    }

    public String getOrderId() { return orderId; }
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public String getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "[Order ID: " + orderId + 
               ", Product ID: " + productId +
               ", Product: " + productName + 
               ", Quantity: " + quantity + 
               ", Time: " + timestamp + "]";
    }
}