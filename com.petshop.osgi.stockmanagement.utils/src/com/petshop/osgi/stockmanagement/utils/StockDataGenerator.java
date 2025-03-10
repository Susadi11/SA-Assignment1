package com.petshop.osgi.stockmanagement.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;

public class StockDataGenerator {

    // ANSI color codes
    public static final String RESET = "\u001B[0m"; // Reset to default color
    public static final String RED = "\u001B[31m"; // Red
    public static final String GREEN = "\u001B[32m"; // Green
    public static final String YELLOW = "\u001B[33m"; // Yellow
    public static final String BLUE = "\u001B[34m"; // Blue
    public static final String PURPLE = "\u001B[35m"; // Purple
    public static final String CYAN = "\u001B[36m"; // Cyan
    public static final String WHITE = "\u001B[37m"; // White

    // Constants for random data generation
    private static final String[] FOOD_NAMES = {"Natural Blend", "Puppy Kibble", "Canary Mix", "Vitamin Formula", "Urinary Health"};
    private static final String[] BRANDS = {"Blue Buffalo", "Hills", "Purina", "Pedigree", "Royal Canin"};
    private static final String[] PET_TYPES = {"Dog", "Cat", "Bird", "Hamster"};
    private static final String[] FOOD_TYPES = {"Dry", "Wet", "Specialty"};
    private static final Random random = new Random();
    
    // Store item data for consistency
    private static final Map<String, ItemData> itemDataMap = new HashMap<>();
    
    // Class to store item data
    private static class ItemData {
        String name;
        String brand;
        String petType;
        String foodType;
        String price;
        String expiryDate;
        
        public ItemData(String name, String brand, String petType, String foodType, String price, String expiryDate) {
            this.name = name;
            this.brand = brand;
            this.petType = petType;
            this.foodType = foodType;
            this.price = price;
            this.expiryDate = expiryDate;
        }
    }

    // Generate a random Food ID
    public static String generateFoodId() {
        return "PF" + String.format("%08X", random.nextInt(0x1000000));
    }

    // Generate a random price between $5.00 and $30.00
    private static String generatePrice() {
        return String.format("$%.2f", 5 + random.nextDouble() * 25);
    }

    // Generate a random quantity between 10 and 100
    public static int generateQuantity() {
        return 10 + random.nextInt(90);
    }

    // Generate a random expiry date within the next 1-3 years
    private static String generateExpiryDate() {
        LocalDate today = LocalDate.now();
        LocalDate expiryDate = today.plusYears(1 + random.nextInt(2)).plusMonths(random.nextInt(12));
        return expiryDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    // Generate a single row of stock data with colors and specific foodId and quantity
    public static String generateStockData(String foodId, int quantity) {
        // Get or create item data
        ItemData itemData;
        if (itemDataMap.containsKey(foodId)) {
            itemData = itemDataMap.get(foodId);
        } else {
            String name = FOOD_NAMES[random.nextInt(FOOD_NAMES.length)];
            String brand = BRANDS[random.nextInt(BRANDS.length)];
            String petType = PET_TYPES[random.nextInt(PET_TYPES.length)];
            String foodType = FOOD_TYPES[random.nextInt(FOOD_TYPES.length)];
            String price = generatePrice();
            String expiryDate = generateExpiryDate();
            
            itemData = new ItemData(name, brand, petType, foodType, price, expiryDate);
            itemDataMap.put(foodId, itemData);
        }

        return String.format(
            "| %s%-10s%s | %s%-23s%s | %s%-13s%s | %s%-10s%s | %s%-10s%s | %s%-6s%s | %s%-8d%s | %s%-13s%s |",
            CYAN, foodId, RESET,       // Food ID (Cyan)
            GREEN, itemData.name, RESET,        // Name (Green)
            YELLOW, itemData.brand, RESET,      // Brand (Yellow)
            BLUE, itemData.petType, RESET,      // Pet Type (Blue)
            PURPLE, itemData.foodType, RESET,   // Food Type (Purple)
            RED, itemData.price, RESET,         // Price (Red)
            quantity < 20 ? RED : WHITE, quantity, RESET,    // Quantity (Red if low, otherwise White)
            CYAN, itemData.expiryDate, RESET   // Expiry Date (Cyan)
        );
    }
    
    // Generate a single row of stock data with random attributes
    public static String generateStockData() {
        String foodId = generateFoodId();
        int quantity = generateQuantity();
        return generateStockData(foodId, quantity);
    }

    // Generate the table header with colors
    public static String getTableHeader() {
        return String.format(
            "%s+----------+-------------------------+---------------+------------+------------+--------+----------+---------------+%s\n" +
            "| %sFood ID%s  | %sName%s                    | %sBrand%s         | %sPet Type%s   | %sFood Type%s  | %sPrice%s  | %sQuantity%s | %sExpiry Date%s   |\n" +
            "%s+----------+-------------------------+---------------+------------+------------+--------+----------+---------------+%s",
            CYAN, RESET,               // Table border (Cyan)
            CYAN, RESET,               // Food ID header (Cyan)
            GREEN, RESET,              // Name header (Green)
            YELLOW, RESET,             // Brand header (Yellow)
            BLUE, RESET,               // Pet Type header (Blue)
            PURPLE, RESET,             // Food Type header (Purple)
            RED, RESET,                // Price header (Red)
            WHITE, RESET,              // Quantity header (White)
            CYAN, RESET,               // Expiry Date header (Cyan)
            CYAN, RESET                // Table border (Cyan)
        );
    }

    // Generate the table footer with colors
    public static String getTableFooter() {
        return String.format(
            "%s+----------+-------------------------+---------------+------------+------------+--------+----------+---------------+%s",
            CYAN, RESET // Table border (Cyan)
        );
    }
}