package com.petshop.osgi.stockmanagement.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

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

    // Generate a random Food ID
    private static String generateFoodId() {
        return "PF" + String.format("%08X", random.nextInt(0x1000000));
    }

    // Generate a random price between $5.00 and $30.00
    private static String generatePrice() {
        return String.format("$%.2f", 5 + random.nextDouble() * 25);
    }

    // Generate a random quantity between 10 and 100
    private static int generateQuantity() {
        return 10 + random.nextInt(90);
    }

    // Generate a random expiry date within the next 1-3 years
    private static String generateExpiryDate() {
        LocalDate today = LocalDate.now();
        LocalDate expiryDate = today.plusYears(1 + random.nextInt(2)).plusMonths(random.nextInt(12));
        return expiryDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    // Generate a single row of stock data with colors
    public static String generateStockData() {
        String foodId = generateFoodId();
        String name = FOOD_NAMES[random.nextInt(FOOD_NAMES.length)];
        String brand = BRANDS[random.nextInt(BRANDS.length)];
        String petType = PET_TYPES[random.nextInt(PET_TYPES.length)];
        String foodType = FOOD_TYPES[random.nextInt(FOOD_TYPES.length)];
        String price = generatePrice();
        int quantity = generateQuantity();
        String expiryDate = generateExpiryDate();

        return String.format(
            "| %s%-10s%s | %s%-23s%s | %s%-13s%s | %s%-10s%s | %s%-10s%s | %s%-6s%s | %s%-8d%s | %s%-13s%s |",
            CYAN, foodId, RESET,       // Food ID (Cyan)
            GREEN, name, RESET,        // Name (Green)
            YELLOW, brand, RESET,      // Brand (Yellow)
            BLUE, petType, RESET,      // Pet Type (Blue)
            PURPLE, foodType, RESET,   // Food Type (Purple)
            RED, price, RESET,         // Price (Red)
            WHITE, quantity, RESET,    // Quantity (White)
            CYAN, expiryDate, RESET   // Expiry Date (Cyan)
        );
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

    // Main method for testing
    public static void main(String[] args) {
        System.out.println(getTableHeader());
        for (int i = 0; i < 5; i++) {
            System.out.println(generateStockData());
        }
        System.out.println(getTableFooter());
    }
}