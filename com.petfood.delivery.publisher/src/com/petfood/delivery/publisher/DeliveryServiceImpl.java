package com.petfood.delivery.publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.petfood.delivery.util.DeliveryRecord;
import com.petfood.delivery.util.DeliveryServiceLogger;
import com.petshop.osgi.stockmanagement.publisher.StockService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class DeliveryServiceImpl implements DeliveryService {
    private static final List<DeliveryRecord> deliveryRecords = new ArrayList<>();
    private static final Random random = new Random();
    private static int orderIdCounter = 1000;
    
    // Add stock service reference
    private StockService stockService;
    private boolean stockServiceAvailable = false;
    private BundleContext context;

    public DeliveryServiceImpl(BundleContext context) {
        this.context = context;
        DeliveryServiceLogger.info("DeliveryServiceImpl", "Initializing delivery service...");
        
        // Try to get the StockService
        try {
            ServiceReference<StockService> stockServiceReference = context.getServiceReference(StockService.class);
            if (stockServiceReference != null) {
                this.stockService = context.getService(stockServiceReference);
                stockServiceAvailable = true;
                DeliveryServiceLogger.info("DeliveryServiceImpl", "Successfully connected to Stock Management System");
            } else {
                DeliveryServiceLogger.warning("DeliveryServiceImpl", "Stock Management System not available. Will try to connect later.");
                
                // Schedule periodic attempts to connect to the stock service
                scheduleStockServiceConnection();
            }
        } catch (Exception e) {
            DeliveryServiceLogger.error("DeliveryServiceImpl", "Error connecting to Stock Management System: " + e.getMessage());
        }
        
        startGeneratingDeliveries();
    }

    private void scheduleStockServiceConnection() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            if (!stockServiceAvailable && context != null) {
                try {
                    ServiceReference<StockService> stockServiceReference = context.getServiceReference(StockService.class);
                    if (stockServiceReference != null) {
                        this.stockService = context.getService(stockServiceReference);
                        stockServiceAvailable = true;
                        DeliveryServiceLogger.success("DeliveryServiceImpl", "Successfully connected to Stock Management System");
                    }
                } catch (Exception e) {
                    // Silent retry
                }
            }
        }, 10, 30, TimeUnit.SECONDS);
    }

    private void startGeneratingDeliveries() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> generateRandomDelivery(), 0, 20, TimeUnit.SECONDS);
        DeliveryServiceLogger.info("DeliveryServiceImpl", "Started scheduled delivery generation (every 20 seconds)");
    }

    private void generateRandomDelivery() {
        String orderId = "ORDER" + orderIdCounter++;
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
        
        // Update stock management system
        updateStockManagement(orderId);
        
        displayDeliveryHistory();
    }
    
    private void updateStockManagement(String orderId) {
        if (stockServiceAvailable && stockService != null) {
            try {
                if (stockService instanceof com.petshop.osgi.stockmanagement.publisher.StockServiceImpl) {
                    com.petshop.osgi.stockmanagement.publisher.StockServiceImpl impl = 
                        (com.petshop.osgi.stockmanagement.publisher.StockServiceImpl) stockService;
                    
                    // Decrease stock for this order
                    impl.decreaseStockForOrder(orderId);
                    
                    DeliveryServiceLogger.success("DeliveryService", "Updated stock levels for order: " + orderId);
                }
            } catch (Exception e) {
                DeliveryServiceLogger.error("DeliveryService", "Failed to update stock levels: " + e.getMessage());
            }
        } else {
            DeliveryServiceLogger.warning("DeliveryService", "Stock Management System not available for order: " + orderId);
        }
    }

    private void displayDeliveryHistory() {
        DeliveryServiceLogger.displayDeliveryTable(deliveryRecords);
    }
}