package com.petfood.delivery.publisher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.petfood.delivery.util.DeliveryServiceLogger;

public class Activator implements BundleActivator {
    private ServiceRegistration<DeliveryService> serviceRegistration;

    @Override
    public void start(BundleContext context) throws Exception {
        DeliveryServiceLogger.success("Publisher", "Delivery Service Publisher Started");
        // Pass the BundleContext to DeliveryServiceImpl
        DeliveryService deliveryService = new DeliveryServiceImpl(context);
        serviceRegistration = context.registerService(DeliveryService.class, deliveryService, null);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        DeliveryServiceLogger.warning("Publisher", "Delivery Service Publisher Stopped");
        serviceRegistration.unregister();
    }
}