package com.petfood.catalog.consumer;

import java.util.List;
import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.petfood.catalog.publisher.CatalogService;
import com.petfood.catalog.publisher.Product;
import com.petfood.catalog.util.CatalogServiceLogger;
import com.petfood.order.publisher.OrderService;
import com.petfood.order.util.OrderRecord;

public class Activator implements BundleActivator {
    private ServiceReference<CatalogService> catalogServiceReference;
    private ServiceReference<OrderService> orderServiceReference;

    @Override
    public void start(BundleContext context) throws Exception {
        CatalogServiceLogger.success("Consumer", "Catalog Service Consumer Started");

        // Get the reference to the published CatalogService
        catalogServiceReference = context.getServiceReference(CatalogService.class);
        
        // Get the reference to the OrderService
        orderServiceReference = context.getServiceReference(OrderService.class);
        
        if (catalogServiceReference != null) {
            CatalogService catalogService = context.getService(catalogServiceReference);
            
            // Check if OrderService is available
            OrderService orderService = null;
            if (orderServiceReference != null) {
                orderService = context.getService(orderServiceReference);
                CatalogServiceLogger.success("Consumer", "Successfully connected to Order Service");
            } else {
                CatalogServiceLogger.warning("Consumer", "Order Service unavailable - ordering functionality disabled");
            }

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
                System.out.println("6. Place an order");  // New option
                System.out.println("7. View orders");      // New option
                System.out.println("8. Exit");
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
                        // Place an order
                        if (orderService != null) {
                            // Display available products first
                            System.out.println("\n----- Available Products -----");
                            catalogService.displayProducts();
                            
                            System.out.print("\nEnter product ID to order: ");
                            String orderProductId = scanner.nextLine();
                            
                            // Check if product exists
                            Product product = catalogService.getAllProducts().stream()
                                .filter(p -> p.getProductId().equals(orderProductId))
                                .findFirst()
                                .orElse(null);
                                
                            if (product != null) {
                                System.out.println("Selected product: " + product.getProductName() + 
                                                  " (Available: " + product.getStockLevel() + ")");
                                
                                System.out.print("Enter quantity to order: ");
                                int orderQuantity = scanner.nextInt();
                                scanner.nextLine(); // Consume the newline character
                                
                                // Place the order
                                boolean orderPlaced = orderService.addOrder(orderProductId, orderQuantity);
                                
                                if (orderPlaced) {
                                    System.out.println(CatalogServiceLogger.GREEN + "Order placed successfully!" + CatalogServiceLogger.RESET);
                                } else {
                                    System.out.println(CatalogServiceLogger.RED + "Failed to place order. " +
                                                      "The product might be out of stock or insufficient quantity." + CatalogServiceLogger.RESET);
                                }
                            } else {
                                System.out.println("Product not found with ID: " + orderProductId);
                            }
                        } else {
                            System.out.println(CatalogServiceLogger.RED + "Order Service unavailable. Cannot place orders." + CatalogServiceLogger.RESET);
                        }
                        break;
                        
                    case 7:
                        // View orders
                        if (orderService != null) {
                            List<OrderRecord> orders = orderService.getOrders();
                            
                            if (orders.isEmpty()) {
                                System.out.println("No orders found.");
                            } else {
                                System.out.println("\n----- Order History -----");
                                for (OrderRecord order : orders) {
                                    System.out.println(order);
                                }
                            }
                        } else {
                            System.out.println(CatalogServiceLogger.RED + "Order Service unavailable. Cannot view orders." + CatalogServiceLogger.RESET);
                        }
                        break;

                    case 8:
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
        
        if (catalogServiceReference != null) {
            context.ungetService(catalogServiceReference);
        }
        
        if (orderServiceReference != null) {
            context.ungetService(orderServiceReference);
        }
    }
}