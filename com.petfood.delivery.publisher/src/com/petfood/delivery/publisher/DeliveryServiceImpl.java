package com.petfood.delivery.publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.petfood.delivery.util.DeliveryRecord;
import com.petfood.delivery.util.DeliveryServiceLogger;

public class DeliveryServiceImpl implements DeliveryService {
    private static final List<DeliveryRecord> deliveryRecords = new ArrayList<>();
    private static final Random random = new Random();
    private static int orderIdCounter = 1000;

    public DeliveryServiceImpl() {
        DeliveryServiceLogger.info("DeliveryServiceImpl", "Initializing delivery service...");
        startGeneratingDeliveries();
    }

    private void startGeneratingDeliveries() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> generateRandomDelivery(), 0, 20, TimeUnit.SECONDS);
        DeliveryServiceLogger.info("DeliveryServiceImpl", "Started scheduled delivery generation (every 10 seconds)");
    }

    private void generateRandomDelivery() {
        String orderId = "ORDER" + orderIdCounter++;
        String trackingNumber = "TRK" + (1000 + random.nextInt(9000));
        String customerContact = "customer" + random.nextInt(100) + "@email.com";

        // Process and store delivery
        processDelivery(orderId, customerContact);
        DeliveryServiceLogger.info("DeliveryServiceImpl", "Generated random delivery: " + orderId);
    }

    @Override
    public void processDelivery(String orderId, String customerContact) {
        String trackingNumber = "TRK" + (1000 + random.nextInt(9000));
        DeliveryRecord record = new DeliveryRecord(orderId, trackingNumber, customerContact, "Shipped");
        
        deliveryRecords.add(record);
        DeliveryServiceLogger.success("DeliveryService", "Processed delivery for order: " + orderId);
        displayDeliveryHistory();
    }

    private void displayDeliveryHistory() {
        DeliveryServiceLogger.displayDeliveryTable(deliveryRecords);
    }
}