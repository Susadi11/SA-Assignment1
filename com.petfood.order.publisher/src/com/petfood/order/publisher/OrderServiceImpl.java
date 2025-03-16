package com.petfood.order.publisher;

import com.petfood.order.util.OrderRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OrderServiceImpl implements OrderService {
    private final List<OrderRecord> orders = new ArrayList<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Random random = new Random();

    @Override
    public List<OrderRecord> getOrders() {
        return orders;
    }

    @Override
    public void addOrder(OrderRecord order) {
        orders.add(order);
        System.out.println("[New Order Added] " + order);
    }

    @Override
    public void startGeneratingOrders() {
        System.out.println("Starting automatic order generation every 8 seconds...");
        scheduler.scheduleAtFixedRate(() -> {
            OrderRecord newOrder = generateRandomOrder();
            addOrder(newOrder);
        }, 0, 8, TimeUnit.SECONDS);
    }

    private OrderRecord generateRandomOrder() {
        String[] items = {"Dog Food", "Cat Food", "Bird Seed", "Fish Pellets"};
        String orderId = "ORD" + (1000 + random.nextInt(9000)); // Random order ID
        String itemName = items[random.nextInt(items.length)];
        int quantity = 1 + random.nextInt(5); // Quantity between 1-5

        return new OrderRecord(orderId, itemName, quantity);
    }
}
