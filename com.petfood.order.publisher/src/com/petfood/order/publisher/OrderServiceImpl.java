package com.petfood.order.publisher;

import com.petfood.catalog.publisher.CatalogService;
import com.petfood.catalog.publisher.Product;
import com.petfood.order.util.OrderLogger;
import com.petfood.order.util.OrderRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    private final List<OrderRecord> orders = new ArrayList<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Random random = new Random();
    
    // Reference to the catalog service
    private CatalogService catalogService;
    
    public OrderServiceImpl() {
        // Default constructor
    }
    
    // Constructor with CatalogService dependency
    public OrderServiceImpl(CatalogService catalogService) {
        this.catalogService = catalogService;
    }
    
    // Method to set the catalog service reference
    public void setCatalogService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @Override
    public List<OrderRecord> getOrders() {
        return orders;
    }
    
    @Override
    public OrderRecord getOrderById(String orderId) {
        return orders.stream()
            .filter(order -> order.getOrderId().equals(orderId))
            .findFirst()
            .orElse(null);
    }

    @Override
    public boolean addOrder(String productId, int quantity) {
        // Check if catalog service is available
        if (catalogService == null) {
            OrderLogger.logError("Cannot place order - Catalog Service unavailable");
            return false;
        }
        
        // Get the product from catalog
        List<Product> products = catalogService.getAllProducts();
        Product product = products.stream()
            .filter(p -> p.getProductId().equals(productId))
            .findFirst()
            .orElse(null);
            
        // Check if product exists
        if (product == null) {
            OrderLogger.logError("Product with ID " + productId + " not found");
            return false;
        }
        
        // Check if there's enough stock
        if (product.getStockLevel() < quantity) {
            OrderLogger.logWarning("Product " + product.getProductName() + " (ID: " + productId + ") is out of stock or insufficient quantity");
            OrderLogger.logInfo("Available stock: " + product.getStockLevel() + ", Requested: " + quantity);
            return false;
        }
        
        // Create order ID
        String orderId = "ORDER" + UUID.randomUUID().toString().substring(0, 8);
        
        // Create the order record
        OrderRecord newOrder = new OrderRecord(orderId, productId, product.getProductName(), quantity);
        orders.add(newOrder);
        
        // Update stock in catalog
        int newStockLevel = product.getStockLevel() - quantity;
        catalogService.updateProduct(productId, null, null, newStockLevel, -1, null);
        
        OrderLogger.logSuccess("New order added: " + newOrder);
        return true;
    }

    @Override
    public void startGeneratingOrders() {
        OrderLogger.logInfo("Starting automatic order generation every 8 seconds...");
        scheduler.scheduleAtFixedRate(() -> {
            if (catalogService != null) {
                generateRandomOrder();
            } else {
                OrderLogger.logWarning("Cannot generate orders - Catalog Service unavailable");
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    private void generateRandomOrder() {
        // Get all available products
        List<Product> availableProducts = catalogService.getAllProducts();
        
        // If no products available, can't generate order
        if (availableProducts.isEmpty()) {
            OrderLogger.logWarning("No products available for ordering");
            return;
        }
        
        // Pick a random product
        Product selectedProduct = availableProducts.get(random.nextInt(availableProducts.size()));
        
        // Random quantity between 1-5
        int quantity = 1 + random.nextInt(5);
        
        // Place the order
        boolean orderPlaced = addOrder(selectedProduct.getProductId(), quantity);
        
        if (!orderPlaced) {
            OrderLogger.logWarning("Could not place an order for " + selectedProduct.getProductName());
        }
    }
}