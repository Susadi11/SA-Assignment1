package com.petshop.osgi.stockmanagement.consumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import com.petshop.osgi.stockmanagement.publisher.StockService;

public class Activator implements BundleActivator {
    private ServiceReference<StockService> serviceReference;
    private StockService stockService;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Stock Monitor started.");
        
        // Get the reference to the StockService
        serviceReference = context.getServiceReference(StockService.class);
        if (serviceReference != null) {
            stockService = context.getService(serviceReference);

            // Display updated stock data every 10 seconds
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(10000); // 10 seconds
                        System.out.println(stockService.getStockData());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            System.out.println("StockService not found. Make sure the Publisher bundle is running.");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Stock Monitor stopped.");
        if (serviceReference != null) {
            context.ungetService(serviceReference);
        }
    }
}