package com.petfood.delivery.consumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.petfood.delivery.publisher.DeliveryService;
import com.petfood.delivery.util.DeliveryServiceLogger;

public class Activator implements BundleActivator {
    private ServiceReference<DeliveryService> serviceReference;
    
    @Override
    public void start(BundleContext context) throws Exception {
        DeliveryServiceLogger.success("Consumer", "Delivery Service Consumer Started");
        
        // Get the reference to the published DeliveryService
        serviceReference = context.getServiceReference(DeliveryService.class);
        if (serviceReference != null) {
            DeliveryService deliveryService = context.getService(serviceReference);
            deliveryService.processDelivery("ORDER123", "customer@email.com");
        } else {
            DeliveryServiceLogger.error("Consumer", "No Delivery Service available");
        }
    }
    
    @Override
    public void stop(BundleContext context) throws Exception {
        DeliveryServiceLogger.warning("Consumer", "Delivery Service Consumer Stopped");
        if (serviceReference != null) {
            context.ungetService(serviceReference);
        }
    }
}