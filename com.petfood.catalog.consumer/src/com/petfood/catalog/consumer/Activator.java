package com.petfood.catalog.consumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.petfood.catalog.publisher.CatalogService;
import com.petfood.catalog.util.CatalogServiceLogger;

import java.util.Scanner;

public class Activator implements BundleActivator {
    private ServiceReference<CatalogService> serviceReference;

    @Override
    public void start(BundleContext context) throws Exception {
        CatalogServiceLogger.success("Consumer", "Catalog Service Consumer Started");

        // Get the reference to the published CatalogService
        serviceReference = context.getServiceReference(CatalogService.class);
        if (serviceReference != null) {
            CatalogService catalogService = context.getService(serviceReference);

            // Create a Scanner for user input
            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Display menu
                System.out.println("\n--- Pet Food Catalog ---");
                System.out.println("1. Add a new product");
                System.out.println("2. Display all products");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        // Add a new product
                        System.out.print("Enter product ID: ");
                        String productId = scanner.nextLine();

                        System.out.print("Enter product name: ");
                        String productName = scanner.nextLine();

                        System.out.print("Enter initial stock: ");
                        int initialStock = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        catalogService.addProduct(productId, productName, initialStock);
                        break;

                    case 2:
                        // Display all products
                        catalogService.displayProducts();
                        break;

                    case 3:
                        // Exit the program
                        CatalogServiceLogger.info("Consumer", "Exiting...");
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            CatalogServiceLogger.error("Consumer", "No Catalog Service available");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        CatalogServiceLogger.warning("Consumer", "Catalog Service Consumer Stopped");
        if (serviceReference != null) {
            context.ungetService(serviceReference);
        }
    }
}