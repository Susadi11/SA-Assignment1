package com.petfood.catalog.util;

public class CatalogRecord {
    private final String productId;
    private final String productName;
    private final String category;
    private final int stockLevel;
    private final double unitPrice; // New attribute: Unit price
    private final String description; // New attribute: Product description

    public CatalogRecord(String productId, String productName, String category, int stockLevel, double unitPrice, String description) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.stockLevel = stockLevel;
        this.unitPrice = unitPrice;
        this.description = description;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public String getDescription() {
        return description;
    }
}