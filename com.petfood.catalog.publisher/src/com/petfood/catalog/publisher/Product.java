package com.petfood.catalog.publisher;

public class Product {
    private String productId;
    private String productName;
    private String category;
    private int stockLevel;
    private double unitPrice; // New attribute: Unit price
    private String description; // New attribute: Product description

    // Constructor to initialize all fields
    public Product(String productId, String productName, String category, int stockLevel, double unitPrice, String description) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.stockLevel = stockLevel;
        this.unitPrice = unitPrice;
        this.description = description;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}