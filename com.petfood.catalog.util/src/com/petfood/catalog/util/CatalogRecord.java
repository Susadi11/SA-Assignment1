package com.petfood.catalog.util;

public class CatalogRecord {
    private final String productId;
    private final String productName;
    private final int stockLevel;

    public CatalogRecord(String productId, String productName, int stockLevel) {
        this.productId = productId;
        this.productName = productName;
        this.stockLevel = stockLevel;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getStockLevel() {
        return stockLevel;
    }
}