package com.petfood.catalog.consumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.petfood.catalog.publisher.CatalogService;
import com.petfood.catalog.publisher.Product;
import com.petfood.catalog.util.CatalogServiceLogger;

import java.util.List;
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
                System.out.println("\n-----PET SHOP CATALOG ------");
                System.out.println("\n");
                System.out.println("1. Add a new product");
                System.out.println("2. Update a product");
                System.out.println("3. Delete a product");
                System.out.println("4. Display all products");
                System.out.println("5. Search products by category");
                System.out.println("6. Exit");
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

                        System.out.print("Enter product category: ");
                        String category = scanner.nextLine();

                        System.out.print("Enter initial stock: ");
                        int initialStock = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        System.out.print("Enter unit price: ");
                        double unitPrice = scanner.nextDouble();
                        scanner.nextLine(); // Consume the newline character

                        System.out.print("Enter product description: ");
                        String description = scanner.nextLine();

                        catalogService.addProduct(productId, productName, category, initialStock, unitPrice, description);
                        break;

                    case 2:
                        // Update a product
                        System.out.print("Enter product ID to update: ");
                        String updateId = scanner.nextLine();

                        // Check if the product exists
                        if (catalogService.getAllProducts().stream().anyMatch(p -> p.getProductId().equals(updateId))) {
                            // Display update options
                            System.out.println("\nWhat do you want to update?");
                            System.out.println("1. Product Name");
                            System.out.println("2. Product Category");
                            System.out.println("3. Stock Level");
                            System.out.println("4. Unit Price");
                            System.out.println("5. Product Description");
                            System.out.println("6. Cancel");
                            System.out.print("Enter your choice: ");

                            int updateChoice = scanner.nextInt();
                            scanner.nextLine(); // Consume the newline character

                            switch (updateChoice) {
                                case 1:
                                    // Update product name
                                    System.out.print("Enter new product name: ");
                                    String newName = scanner.nextLine();
                                    catalogService.updateProduct(updateId, newName, null, -1, -1, null); // -1 means no change to stock or price
                                    break;

                                case 2:
                                    // Update product category
                                    System.out.print("Enter new product category: ");
                                    String newCategory = scanner.nextLine();
                                    catalogService.updateProduct(updateId, null, newCategory, -1, -1, null); // -1 means no change to stock or price
                                    break;

                                case 3:
                                    // Update stock level
                                    System.out.print("Enter new stock level: ");
                                    int newStock = scanner.nextInt();
                                    scanner.nextLine(); // Consume the newline character
                                    catalogService.updateProduct(updateId, null, null, newStock, -1, null); // -1 means no change to price
                                    break;

                                case 4:
                                    // Update unit price
                                    System.out.print("Enter new unit price: ");
                                    double newPrice = scanner.nextDouble();
                                    scanner.nextLine(); // Consume the newline character
                                    catalogService.updateProduct(updateId, null, null, -1, newPrice, null); // -1 means no change to stock
                                    break;

                                case 5:
                                    // Update product description
                                    System.out.print("Enter new product description: ");
                                    String newDescription = scanner.nextLine();
                                    catalogService.updateProduct(updateId, null, null, -1, -1, newDescription); // -1 means no change to stock or price
                                    break;

                                case 6:
                                    // Cancel update
                                    System.out.println("Update cancelled.");
                                    break;

                                default:
                                    System.out.println("Invalid choice. Update cancelled.");
                            }
                        } else {
                            System.out.println("Product not found with ID: " + updateId);
                        }
                        break;

                    case 3:
                        // Delete a product
                        System.out.print("Enter product ID to delete: ");
                        String deleteId = scanner.nextLine();
                        catalogService.deleteProduct(deleteId);
                        break;

                    case 4:
                        // Display all products
                        catalogService.displayProducts();
                        break;

                    case 5:
                        // Search products by category
                        System.out.print("Enter category to search: ");
                        String searchCategory = scanner.nextLine();
                        List<Product> products = catalogService.getProductsByCategory(searchCategory);
                        if (products.isEmpty()) {
                            System.out.println("No products found in category: " + searchCategory);
                        } 
                        break;

                    case 6:
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