package com.petfood.order.publisher;

import com.petfood.order.util.OrderRecord;
import java.util.List;

public interface OrderService {
    // Get all orders
    List<OrderRecord> getOrders();
    
    // Add an order with stock validation
    // Returns true if order was successfully added, false if out of stock
    boolean addOrder(String productId, int quantity);
    
    // Start generating random orders
    void startGeneratingOrders();
    
    // Get specific order by ID
    OrderRecord getOrderById(String orderId);
}