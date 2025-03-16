package com.petfood.catalog.publisher;

import java.util.List;

public interface CatalogService {
    // Add a new product with unit price and description
    void addProduct(String productId, String productName, String category, int initialStock, double unitPrice, String description);

    // Update a product with optional fields (null or -1 means no change)
    void updateProduct(String productId, String productName, String category, int stockLevel, double unitPrice, String description);

    // Delete a product by ID
    void deleteProduct(String productId);

    // Display all products in a formatted table
    void displayProducts();

    // Search products by keyword (name or category)
    List<Product> searchProducts(String keyword);

    // Get all products in the catalog
    List<Product> getAllProducts();

    // Get products by category
    List<Product> getProductsByCategory(String category);
}