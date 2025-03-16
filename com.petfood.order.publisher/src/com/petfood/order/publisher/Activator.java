package com.petfood.order.publisher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
    ServiceRegistration<?> serviceRegistration;

    public void start(BundleContext context) {
        System.out.println("Order Publisher started...");
        OrderService service = new OrderServiceImpl();
        serviceRegistration = context.registerService(OrderService.class.getName(), service, null);
        System.out.println("Order Service registered.");

        // Start automatic order generation every 8 seconds
        service.startGeneratingOrders();
    }

    public void stop(BundleContext context) {
        serviceRegistration.unregister();
        System.out.println("Order Publisher stopped...");
    }
}
