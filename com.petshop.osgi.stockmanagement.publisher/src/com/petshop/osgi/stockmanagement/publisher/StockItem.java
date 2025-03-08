package com.petshop.osgi.stockmanagement.publisher;

public class StockItem {
    private String id;
    private String name;
    private String category;
    private int quantity;
    private double price;
    private int reorderLevel;
    private String lastUpdated;

    public StockItem(String id, String name, String category, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.reorderLevel = 10; // Default reorder level
        this.lastUpdated = java.time.LocalDateTime.now().toString();
    }

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
        this.lastUpdated = java.time.LocalDateTime.now().toString();
    }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(int reorderLevel) { this.reorderLevel = reorderLevel; }
    public String getLastUpdated() { return lastUpdated; }

    public String getStockStatus() {
        if (quantity <= 0) {
            return "OUT OF STOCK";
        } else if (quantity < reorderLevel) {
            return "LOW STOCK";
        } else if (quantity > 50) {
            return "OVERSTOCKED";
        } else {
            return "IN STOCK";
        }
    }
}