package com.petfood.delivery.consumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import com.petfood.delivery.publisher.DeliveryService;
import com.petfood.delivery.util.DeliveryServiceLogger;

public class Activator implements BundleActivator {
    private ServiceRegistration<DeliveryNotificationService> serviceRegistration;
    private ServiceReference<DeliveryService> deliveryServiceReference;
    
    @Override
    public void start(BundleContext context) throws Exception {
        DeliveryServiceLogger.success("Subscriber", "Delivery Notification Service Started");
        
        // Get the DeliveryService
        deliveryServiceReference = context.getServiceReference(DeliveryService.class);
        if (deliveryServiceReference != null) {
            DeliveryService deliveryService = context.getService(deliveryServiceReference);
            
            // Create and register our notification service
            DeliveryNotificationService notificationService = new DeliveryNotificationServiceImpl(deliveryService);
            serviceRegistration = context.registerService(DeliveryNotificationService.class, notificationService, null);
        } else {
            DeliveryServiceLogger.error("Subscriber", "DeliveryService not available");
        }
    }
    
    @Override
    public void stop(BundleContext context) throws Exception {
        DeliveryServiceLogger.warning("Subscriber", "Delivery Notification Service Stopped");
        
        if (serviceRegistration != null) {
            serviceRegistration.unregister();
        }
        
        if (deliveryServiceReference != null) {
            context.ungetService(deliveryServiceReference);
        }
    }
}