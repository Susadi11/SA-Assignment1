package com.petshop.osgi.stockmanagement.publisher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
    private ServiceRegistration<StockService> registration;
    private StockServiceImpl stockService;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Stock Publisher started.");
        stockService = new StockServiceImpl();
        registration = context.registerService(StockService.class, stockService, null);

        // Simulate stock data generation every 10 seconds
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10000); // 10 seconds
                    stockService.addStockData(); // Add new stock data
                    System.out.println("New stock data added.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Stock Publisher stopped.");
        registration.unregister();
    }
}