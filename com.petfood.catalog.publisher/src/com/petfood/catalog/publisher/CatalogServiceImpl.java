package com.petfood.catalog.publisher;

import java.util.HashMap;
import java.util.Map;
import org.osgi.service.component.annotations.Component;
import com.petfood.catalog.util.CatalogServiceLogger;

@Component(service = CatalogService.class)
public class CatalogServiceImpl implements CatalogService {
    private Map<String, Integer> stockLevels = new HashMap<>();
    private Map<String, String> productNames = new HashMap<>();

    @Override
    public void addProduct(String productId, String productName, int initialStock) {
        stockLevels.put(productId, initialStock);
        productNames.put(productId, productName);
        CatalogServiceLogger.success("CatalogService", "Added product: " + productName + " (ID: " + productId + ")");
    }

    @Override
    public void updateStock(String productId, int quantity) {
        int currentStock = stockLevels.getOrDefault(productId, 0);
        int newStock = currentStock + quantity;
        stockLevels.put(productId, newStock);

        CatalogServiceLogger.info("CatalogService", "Updated stock for product: " + productNames.get(productId) + ", New stock: " + newStock);

        if (newStock <= 0) {
            markOutOfStock(productId);
        }
    }

    @Override
    public void markOutOfStock(String productId) {
        CatalogServiceLogger.warning("CatalogService", "Product out of stock: " + productNames.get(productId));
    }

    @Override
    public void displayProducts() {
        if (productNames.isEmpty()) {
            CatalogServiceLogger.info("CatalogService", "No products available.");
        } else {
            // Define the format for the header and each row
            String headerFormat = "%-10s %-20s %-10s%n";
            String rowFormat = "%-10s %-20s %-10d%n";

            // Print the header
            System.out.printf(headerFormat, "ID", "Name", "Stock");
            System.out.println("-----------------------------------------");

            // Print each product
            for (Map.Entry<String, String> entry : productNames.entrySet()) {
                String productId = entry.getKey();
                String productName = entry.getValue();
                int stock = stockLevels.getOrDefault(productId, 0);
                System.out.printf(rowFormat, productId, productName, stock);
            }
        }
    }
}