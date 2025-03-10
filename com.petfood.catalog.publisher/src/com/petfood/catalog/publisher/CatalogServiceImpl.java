package com.petfood.catalog.publisher;

import com.petfood.catalog.util.CatalogServiceLogger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class CatalogServiceImpl implements CatalogService {
    private Map<String, Product> products = new HashMap<>(); // Store products with ID as key
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public void addProduct(String productId, String productName, String category, int initialStock, double unitPrice, String description) {
        lock.lock();
        try {
            Product product = new Product(productId, productName, category, initialStock, unitPrice, description);
            products.put(productId, product);
            CatalogServiceLogger.success("CatalogService", "Added product: " + productName + " (ID: " + productId + ")");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void updateProduct(String productId, String productName, String category, int stockLevel, double unitPrice, String description) {
        lock.lock();
        try {
            if (products.containsKey(productId)) {
                Product product = products.get(productId);

                // Update only the fields that are provided (not null or -1)
                if (productName != null) {
                    product.setProductName(productName);
                }
                if (category != null) {
                    product.setCategory(category);
                }
                if (stockLevel != -1) {
                    product.setStockLevel(stockLevel);
                }
                if (unitPrice != -1) {
                    product.setUnitPrice(unitPrice);
                }
                if (description != null) {
                    product.setDescription(description);
                }

                CatalogServiceLogger.info("CatalogService", "Updated product: " + product.getProductName() + " (ID: " + productId + ")");
            } else {
                CatalogServiceLogger.error("CatalogService", "Product not found: " + productId);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void deleteProduct(String productId) {
        lock.lock();
        try {
            if (products.containsKey(productId)) {
                Product product = products.remove(productId);
                CatalogServiceLogger.info("CatalogService", "Deleted product: " + product.getProductName() + " (ID: " + productId + ")");
            } else {
                CatalogServiceLogger.error("CatalogService", "Product not found: " + productId);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void displayProducts() {
        if (products.isEmpty()) {
            CatalogServiceLogger.info("CatalogService", "No products available.");
        } else {
            // ANSI color codes
            String reset = "\u001B[0m";
            String red = "\u001B[31m";
            String green = "\u001B[32m";
            String yellow = "\u001B[33m";
            String blue = "\u001B[34m";
            String purple = "\u001B[35m";
            String cyan = "\u001B[36m";

            // Unicode box-drawing characters for table borders
            String verticalLine = "│";
            String horizontalLine = "─";
            String topLeftCorner = "┌";
            String topRightCorner = "┐";
            String bottomLeftCorner = "└";
            String bottomRightCorner = "┘";
            String crossJoint = "┼";
            String leftJoint = "├";
            String rightJoint = "┤";
            String topJoint = "┬";
            String bottomJoint = "┴";

            // Table dimensions
            int idWidth = 10;
            int nameWidth = 20;
            int categoryWidth = 15;
            int stockWidth = 10;
            int priceWidth = 10;
            int descWidth = 30;

            // Create the table border
            String border = topLeftCorner +
                    horizontalLine.repeat(idWidth + 2) + topJoint +
                    horizontalLine.repeat(nameWidth + 2) + topJoint +
                    horizontalLine.repeat(categoryWidth + 2) + topJoint +
                    horizontalLine.repeat(stockWidth + 2) + topJoint +
                    horizontalLine.repeat(priceWidth + 2) + topJoint +
                    horizontalLine.repeat(descWidth + 2) + topRightCorner;

            String rowSeparator = leftJoint +
                    horizontalLine.repeat(idWidth + 2) + crossJoint +
                    horizontalLine.repeat(nameWidth + 2) + crossJoint +
                    horizontalLine.repeat(categoryWidth + 2) + crossJoint +
                    horizontalLine.repeat(stockWidth + 2) + crossJoint +
                    horizontalLine.repeat(priceWidth + 2) + crossJoint +
                    horizontalLine.repeat(descWidth + 2) + rightJoint;

            String footer = bottomLeftCorner +
                    horizontalLine.repeat(idWidth + 2) + bottomJoint +
                    horizontalLine.repeat(nameWidth + 2) + bottomJoint +
                    horizontalLine.repeat(categoryWidth + 2) + bottomJoint +
                    horizontalLine.repeat(stockWidth + 2) + bottomJoint +
                    horizontalLine.repeat(priceWidth + 2) + bottomJoint +
                    horizontalLine.repeat(descWidth + 2) + bottomRightCorner;

            // Print the colorful title
            System.out.println(cyan + "        -------- PET STORE CATALOG --------     " + reset);

            // Print the table header
            System.out.println(cyan + border + reset);
            System.out.printf(cyan + verticalLine + reset + " " +
                            blue + "%-" + idWidth + "s" + reset + " " +
                            cyan + verticalLine + reset + " " +
                            blue + "%-" + nameWidth + "s" + reset + " " +
                            cyan + verticalLine + reset + " " +
                            blue + "%-" + categoryWidth + "s" + reset + " " +
                            cyan + verticalLine + reset + " " +
                            blue + "%-" + stockWidth + "s" + reset + " " +
                            cyan + verticalLine + reset + " " +
                            blue + "%-" + priceWidth + "s" + reset + " " +
                            cyan + verticalLine + reset + " " +
                            blue + "%-" + descWidth + "s" + reset + " " +
                            cyan + verticalLine + reset + "%n",
                    "ID", "Name", "Category", "Stock", "Price", "Description");
            System.out.println(cyan + rowSeparator + reset);

            // Print the table rows
            for (Product product : products.values()) {
                System.out.printf(cyan + verticalLine + reset + " " +
                                yellow + "%-" + idWidth + "s" + reset + " " +
                                cyan + verticalLine + reset + " " +
                                green + "%-" + nameWidth + "s" + reset + " " +
                                cyan + verticalLine + reset + " " +
                                purple + "%-" + categoryWidth + "s" + reset + " " +
                                cyan + verticalLine + reset + " " +
                                red + "%-" + stockWidth + "d" + reset + " " +
                                cyan + verticalLine + reset + " " +
                                green + "$%-" + (priceWidth - 1) + ".2f" + reset + " " +
                                cyan + verticalLine + reset + " " +
                                blue + "%-" + descWidth + "s" + reset + " " +
                                cyan + verticalLine + reset + "%n",
                        product.getProductId(),
                        product.getProductName(),
                        product.getCategory(),
                        product.getStockLevel(),
                        product.getUnitPrice(),
                        product.getDescription());
            }

            // Print the table footer
            System.out.println(cyan + footer + reset);
        }
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        return products.values().stream()
                .filter(product -> product.getProductName().contains(keyword) || product.getCategory().contains(keyword))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getAllProducts() {
        return List.copyOf(products.values());
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        List<Product> filteredProducts = products.values().stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());

        if (filteredProducts.isEmpty()) {
            CatalogServiceLogger.info("CatalogService", "No products found in category: " + category);
        } else {
            // ANSI color codes
            String reset = "\u001B[0m";
            String red = "\u001B[31m";
            String green = "\u001B[32m";
            String yellow = "\u001B[33m";
            String blue = "\u001B[34m";
            String purple = "\u001B[35m";
            String cyan = "\u001B[36m";

            // Unicode box-drawing characters for table borders
            String verticalLine = "│";
            String horizontalLine = "─";
            String topLeftCorner = "┌";
            String topRightCorner = "┐";
            String bottomLeftCorner = "└";
            String bottomRightCorner = "┘";
            String crossJoint = "┼";
            String leftJoint = "├";
            String rightJoint = "┤";
            String topJoint = "┬";
            String bottomJoint = "┴";

            // Table dimensions
            int idWidth = 10;
            int nameWidth = 20;
            int categoryWidth = 15;
            int stockWidth = 10;
            int priceWidth = 10;
            int descWidth = 30;

            // Create the table border
            String border = topLeftCorner +
                    horizontalLine.repeat(idWidth + 2) + topJoint +
                    horizontalLine.repeat(nameWidth + 2) + topJoint +
                    horizontalLine.repeat(categoryWidth + 2) + topJoint +
                    horizontalLine.repeat(stockWidth + 2) + topJoint +
                    horizontalLine.repeat(priceWidth + 2) + topJoint +
                    horizontalLine.repeat(descWidth + 2) + topRightCorner;

            String rowSeparator = leftJoint +
                    horizontalLine.repeat(idWidth + 2) + crossJoint +
                    horizontalLine.repeat(nameWidth + 2) + crossJoint +
                    horizontalLine.repeat(categoryWidth + 2) + crossJoint +
                    horizontalLine.repeat(stockWidth + 2) + crossJoint +
                    horizontalLine.repeat(priceWidth + 2) + crossJoint +
                    horizontalLine.repeat(descWidth + 2) + rightJoint;

            String footer = bottomLeftCorner +
                    horizontalLine.repeat(idWidth + 2) + bottomJoint +
                    horizontalLine.repeat(nameWidth + 2) + bottomJoint +
                    horizontalLine.repeat(categoryWidth + 2) + bottomJoint +
                    horizontalLine.repeat(stockWidth + 2) + bottomJoint +
                    horizontalLine.repeat(priceWidth + 2) + bottomJoint +
                    horizontalLine.repeat(descWidth + 2) + bottomRightCorner;

            // Print the colorful title
            System.out.println(cyan + "           -------- PRODUCTS IN CATEGORY: " + category.toUpperCase() + " --------     " + reset);

            // Print the table header
            System.out.println(cyan + border + reset);
            System.out.printf(cyan + verticalLine + reset + " " +
                            blue + "%-" + idWidth + "s" + reset + " " +
                            cyan + verticalLine + reset + " " +
                            blue + "%-" + nameWidth + "s" + reset + " " +
                            cyan + verticalLine + reset + " " +
                            blue + "%-" + categoryWidth + "s" + reset + " " +
                            cyan + verticalLine + reset + " " +
                            blue + "%-" + stockWidth + "s" + reset + " " +
                            cyan + verticalLine + reset + " " +
                            blue + "%-" + priceWidth + "s" + reset + " " +
                            cyan + verticalLine + reset + " " +
                            blue + "%-" + descWidth + "s" + reset + " " +
                            cyan + verticalLine + reset + "%n",
                    "ID", "Name", "Category", "Stock", "Price", "Description");
            System.out.println(cyan + rowSeparator + reset);

            // Print the table rows
            for (Product product : filteredProducts) {
                System.out.printf(cyan + verticalLine + reset + " " +
                                yellow + "%-" + idWidth + "s" + reset + " " +
                                cyan + verticalLine + reset + " " +
                                green + "%-" + nameWidth + "s" + reset + " " +
                                cyan + verticalLine + reset + " " +
                                purple + "%-" + categoryWidth + "s" + reset + " " +
                                cyan + verticalLine + reset + " " +
                                red + "%-" + stockWidth + "d" + reset + " " +
                                cyan + verticalLine + reset + " " +
                                green + "$%-" + (priceWidth - 1) + ".2f" + reset + " " +
                                cyan + verticalLine + reset + " " +
                                blue + "%-" + descWidth + "s" + reset + " " +
                                cyan + verticalLine + reset + "%n",
                        product.getProductId(),
                        product.getProductName(),
                        product.getCategory(),
                        product.getStockLevel(),
                        product.getUnitPrice(),
                        product.getDescription());
            }

            // Print the table footer
            System.out.println(cyan + footer + reset);
        }

        return filteredProducts;
    }
}