package com.petfood.order.util;

public class OrderRecord {
    private String orderId;
    private String itemName;
    private int quantity;

    // Constructor
    public OrderRecord(String orderId, String itemName, int quantity) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    // Getters
    public String getOrderId() {
        return orderId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters (optional, if you want to modify values later)
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
