package com.petfood.catalog.publisher;

public interface CatalogService {
    void addProduct(String productId, String productName, int initialStock);
    void updateStock(String productId, int quantity);
    void markOutOfStock(String productId);
	void displayProducts();
}