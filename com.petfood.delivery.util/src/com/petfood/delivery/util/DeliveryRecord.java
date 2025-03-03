package com.petfood.delivery.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a delivery record in the pet food delivery system.
 */
public class DeliveryRecord {
    private final String orderId;
    private final String trackingNumber;
    private final String customerContact;
    private final String timestamp;
    private final String status;
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    /**
     * Creates a new delivery record
     * @param orderId The order ID
     * @param trackingNumber The tracking number
     * @param customerContact The customer contact information
     * @param status The delivery status
     */
    public DeliveryRecord(String orderId, String trackingNumber, String customerContact, String status) {
        this.orderId = orderId;
        this.trackingNumber = trackingNumber;
        this.customerContact = customerContact;
        this.status = status;
        this.timestamp = LocalDateTime.now().format(formatter);
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public String getTrackingNumber() {
        return trackingNumber;
    }
    
    public String getCustomerContact() {
        return customerContact;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public String getStatus() {
        return status;
    }
}