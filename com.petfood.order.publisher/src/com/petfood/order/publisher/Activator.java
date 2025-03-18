package com.petfood.order.publisher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import com.petfood.catalog.publisher.CatalogService;
import com.petfood.order.util.OrderLogger;

public class Activator implements BundleActivator {
    private ServiceRegistration<?> serviceRegistration;
    private ServiceReference<CatalogService> catalogServiceReference;
    private OrderServiceImpl orderService;

    public void start(BundleContext context) {
        OrderLogger.logInfo("Order Publisher starting...");
        
        // First, get a reference to the CatalogService
        try {
            catalogServiceReference = context.getServiceReference(CatalogService.class);
            if (catalogServiceReference != null) {
                CatalogService catalogService = context.getService(catalogServiceReference);
                
                // Create OrderService with CatalogService dependency
                orderService = new OrderServiceImpl(catalogService);
                OrderLogger.logSuccess("Connected to Catalog Service");
                
                // Register OrderService
                serviceRegistration = context.registerService(OrderService.class.getName(), orderService, null);
                OrderLogger.logSuccess("Order Service registered");
                
                // Start automatic order generation
                orderService.startGeneratingOrders();
            } else {
                // If CatalogService is not available, create OrderService without it
                orderService = new OrderServiceImpl();
                serviceRegistration = context.registerService(OrderService.class.getName(), orderService, null);
                OrderLogger.logWarning("Catalog Service unavailable, registration with limited functionality");
            }
        } catch (Exception e) {
            OrderLogger.logError("Error starting Order Publisher: " + e.getMessage());
        }
    }

    public void stop(BundleContext context) {
        if (serviceRegistration != null) {
            serviceRegistration.unregister();
        }
        
        if (catalogServiceReference != null) {
            context.ungetService(catalogServiceReference);
        }
        
        OrderLogger.logWarning("Order Publisher stopped");
    }
}