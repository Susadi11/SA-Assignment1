package com.petfood.order.consumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import om.petfood.order.publisher.OrderService;
import com.petfood.order.util.OrderRecord;

public class Activator implements BundleActivator {
    ServiceReference<?> serviceReference;

    public void start(BundleContext context) {
        System.out.println("Order Consumer starting...");

        serviceReference = context.getServiceReference(OrderService.class.getName());
        OrderService orderService = (OrderService) context.getService(serviceReference);

        if(orderService != null) {
            OrderRecord newOrder = new OrderRecord("ORD001", "Cat Food", 2);
            orderService.addOrder(newOrder);

            for(OrderRecord order : orderService.getOrders()) {
                System.out.println("Order: " + order.getOrderId() + ", Item: " + order.getItemName() + ", Quantity: " + order.getQuantity());
            }
        } else {
            System.out.println("Order Service unavailable!");
        }
    }

    public void stop(BundleContext context) {
        System.out.println("Order Consumer stopped...");
    }
}
