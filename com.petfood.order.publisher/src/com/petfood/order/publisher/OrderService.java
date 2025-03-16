package com.petfood.order.publisher;

import com.petfood.order.util.OrderRecord;
import java.util.List;

public interface OrderService {
    List<OrderRecord> getOrders();
    void addOrder(OrderRecord order);
    void startGeneratingOrders(); 
}
