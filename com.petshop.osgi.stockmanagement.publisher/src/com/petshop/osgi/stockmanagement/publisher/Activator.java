package com.petshop.osgi.stockmanagement.publisher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

    private ServiceRegistration<?> serviceRegistration;
    
    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("\n\n▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
        System.out.println("▓                                                                           ▓");
        System.out.println("▓           \u001B[36mPET SHOP STOCK MANAGEMENT SERVICE PUBLISHER\u001B[0m                   ▓");
        System.out.println("▓                                                                           ▓");
        System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓\n\n");
        
        // Create service implementation
        StockManagementService service = new StockManagementServiceImpl();
        
        // Register the service
        serviceRegistration = context.registerService(
                StockManagementService.class.getName(), service, null);
        
        System.out.println("\u001B[32m✓ Stock Management Service has been registered.\u001B[0m");
        
        System.out.println("\n\u001B[33m>>> Initializing inventory with sample data...\u001B[0m");
        service.addItem("DF001", "Premium Dog Food", "Dog Food", 45, 29.99);
        service.addItem("CF002", "Gourmet Cat Food", "Cat Food", 30, 15.99);
        service.addItem("BF003", "Bird Seed Mix", "Bird Food", 20, 12.99);
        service.addItem("FF004", "Tropical Fish Flakes", "Fish Food", 25, 8.99);
        service.addItem("RF005", "Reptile Pellets", "Reptile Food", 15, 19.99);
        service.addItem("SF006", "Small Animal Pellets", "Small Animal Food", 20, 10.50);
        service.addItem("WF007", "Wet Dog Food Cans", "Dog Food", 40, 3.99);
        service.addItem("TF008", "Pet Food Treats Assortment", "Pet Treats", 35, 7.50);
        
        // Display the stock table
        System.out.println("\n\u001B[33m>>> Current Inventory:\u001B[0m");
        service.displayColorfulStockTable();
        
        // Display a random report
        System.out.println("\n\u001B[33m>>> Generated Stock Report:\u001B[0m");
        System.out.println(service.generateRandomStockReport());
        
        System.out.println("\n\u001B[32m✓ Stock Management Publisher initialization complete and ready for service!\u001B[0m");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("\n\u001B[33m>>> Stock Management Service Publisher stopping...\u001B[0m");
        
        if (serviceRegistration != null) {
            serviceRegistration.unregister();
            serviceRegistration = null;
            System.out.println("\u001B[31m✗ Stock Management Service has been unregistered.\u001B[0m");
        }
        
        System.out.println("\u001B[31m✗ Stock Management Service Publisher stopped.\u001B[0m");
    }
}