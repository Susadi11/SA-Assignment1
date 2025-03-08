package com.petshop.osgi.stockmanagement.consumer;

import com.petshop.osgi.stockmanagement.publisher.StockManagementService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;



public class Activator implements BundleActivator {

    private ServiceReference<?> serviceReference;
    
    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("\n\n▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
        System.out.println("▓                                                                           ▓");
        System.out.println("▓           \u001B[35mPET SHOP STOCK MANAGEMENT SERVICE CONSUMER\u001B[0m                   ▓");
        System.out.println("▓                                                                           ▓");
        System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓\n\n");
        
        // Get service reference
        serviceReference = context.getServiceReference(StockManagementService.class.getName());
        
        if (serviceReference != null) {
            // Get the service
            StockManagementService stockService = (StockManagementService) context.getService(serviceReference);
            
            System.out.println("\u001B[32m✓ Stock Management Service Consumer connected to service.\u001B[0m");
            
            // Display initial inventory
            System.out.println("\n\u001B[33m>>> Initial Inventory State:\u001B[0m");
            stockService.displayColorfulStockTable();
            
            // Perform some operations
            System.out.println("\n\u001B[33m>>> Performing inventory operations...\u001B[0m");
            
            // Add a new item
            stockService.addItem("PT009", "Bird Perch Toy", "Accessories", 12, 9.99);
            
            // Update existing item
            stockService.updateStock("DF001", 40);
            
            // Check stock of an item
            String itemToCheck = "CL002";
            stockService.checkStock(itemToCheck);
            
            // Simulate random stock activity
            stockService.simulateStockActivity();
            
            // Display updated inventory
            System.out.println("\n\u001B[33m>>> Updated Inventory State:\u001B[0m");
            stockService.displayColorfulStockTable();
            
            // Generate a random stock report
            System.out.println("\n\u001B[33m>>> Generated Stock Report:\u001B[0m");
            System.out.println(stockService.generateRandomStockReport());
            
            System.out.println("\n\u001B[32m✓ Stock Management Consumer operations completed successfully!\u001B[0m");
            
        } else {
            System.out.println("\u001B[31m✗ Stock Management Service not available! Make sure to start the publisher first.\u001B[0m");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("\n\u001B[33m>>> Stock Management Service Consumer stopping...\u001B[0m");
        
        if (serviceReference != null) {
            context.ungetService(serviceReference);
            serviceReference = null;
            System.out.println("\u001B[31m✗ Disconnected from Stock Management Service.\u001B[0m");
        }
        
        System.out.println("\u001B[31m✗ Stock Management Service Consumer stopped.\u001B[0m");
    }
}