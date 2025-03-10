package com.petfood.order.consumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import com.petfood.order.publisher.OrderService;
import com.petfood.order.util.OrderRecord;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Activator implements BundleActivator {
    ServiceReference<?> serviceReference;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void start(BundleContext context) {
        System.out.println("Order Consumer starting...");

        serviceReference = context.getServiceReference(OrderService.class.getName());
        OrderService orderService = (OrderService) context.getService(serviceReference);

        if (orderService != null) {
            System.out.println("Connected to Order Service.");

            // Fetch orders every 8 seconds
            scheduler.scheduleAtFixedRate(() -> {
                List<OrderRecord> orders = orderService.getOrders();
                System.out.println("Fetching orders...");
                for (OrderRecord order : orders) {
                    System.out.println(order);
                }
            }, 0, 8, TimeUnit.SECONDS);

        } else {
            System.out.println("Order Service unavailable!");
        }
    }

    public void stop(BundleContext context) {
        System.out.println("Order Consumer stopped...");
        scheduler.shutdown();
    }
}
