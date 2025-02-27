package com.petfood.delivery.consumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import com.petfood.delivery.publisher.DeliveryService;

public class Activator implements BundleActivator {
    private ServiceReference<DeliveryService> serviceReference;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("✅ Delivery Service Consumer Started...");

        // Get the reference to the published DeliveryService
        serviceReference = context.getServiceReference(DeliveryService.class);
        if (serviceReference != null) {
            DeliveryService deliveryService = context.getService(serviceReference);
            deliveryService.processDelivery("ORDER123", "customer@email.com");
        } else {
            System.out.println("⚠️ No Delivery Service available.");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("❌ Delivery Service Consumer Stopped...");
        context.ungetService(serviceReference);
    }
}
