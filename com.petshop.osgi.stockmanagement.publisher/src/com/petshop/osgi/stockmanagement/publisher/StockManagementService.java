package com.petshop.osgi.stockmanagement.publisher;

public interface StockManagementService {
    void addItem(String itemId, String name, String category, int quantity, double price);
    boolean removeItem(String itemId);
    int checkStock(String itemId);
    void updateStock(String itemId, int newQuantity);
    String getAllStockItems();
    String generateRandomStockReport();
    void displayColorfulStockTable();
    void simulateStockActivity(); // For random changes in stock
}