package com.petfood.delivery.publisher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
    private ServiceRegistration<DeliveryService> serviceRegistration;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("✅ Delivery Service Publisher Started...");
        DeliveryService deliveryService = new DeliveryServiceImpl();
        serviceRegistration = context.registerService(DeliveryService.class, deliveryService, null);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("❌ Delivery Service Publisher Stopped...");
        serviceRegistration.unregister();
    }
}
