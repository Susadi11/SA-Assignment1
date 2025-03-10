package com.petfood.order.util;

public class OrderRecord {
    private String orderId;
    private String itemName;
    private int quantity;

    public OrderRecord(String orderId, String itemName, int quantity) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public String getOrderId() { return orderId; }
    public String getItemName() { return itemName; }
    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
        return "[Order ID: " + orderId + ", Item: " + itemName + ", Quantity: " + quantity + "]";
    }
}
