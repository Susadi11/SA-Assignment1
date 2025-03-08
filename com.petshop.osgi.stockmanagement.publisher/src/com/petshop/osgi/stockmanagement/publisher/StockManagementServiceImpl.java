package com.petshop.osgi.stockmanagement.publisher;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class StockManagementServiceImpl implements StockManagementService {
    
    private Map<String, StockItem> inventory = new HashMap<>();
    private Random random = new Random();

    // ANSI Color codes
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE = "\u001B[37m";
    
    private static final String BG_RED = "\u001B[41m";
    private static final String BG_GREEN = "\u001B[42m";
    private static final String BG_YELLOW = "\u001B[43m";
    private static final String BG_BLUE = "\u001B[44m";
    
    @Override
    public void addItem(String itemId, String name, String category, int quantity, double price) {
        inventory.put(itemId, new StockItem(itemId, name, category, quantity, price));
        System.out.println(GREEN + "✓ Added item: " + name + " (ID: " + itemId + "), Category: " + category + 
                          ", Quantity: " + quantity + ", Price: $" + price + RESET);
    }
    
    @Override
    public boolean removeItem(String itemId) {
        if (inventory.containsKey(itemId)) {
            StockItem removed = inventory.remove(itemId);
            System.out.println(RED + "✗ Removed item: " + removed.getName() + " from inventory" + RESET);
            return true;
        }
        System.out.println(YELLOW + "! Item with ID " + itemId + " not found in inventory" + RESET);
        return false;
    }
    
    @Override
    public int checkStock(String itemId) {
        if (inventory.containsKey(itemId)) {
            StockItem item = inventory.get(itemId);
            System.out.println(BLUE + "ℹ Stock check for " + item.getName() + ": " + item.getQuantity() + RESET);
            return item.getQuantity();
        }
        System.out.println(YELLOW + "! Item with ID " + itemId + " not found in inventory" + RESET);
        return -1; // Not found
    }
    
    @Override
    public void updateStock(String itemId, int newQuantity) {
        if (inventory.containsKey(itemId)) {
            StockItem item = inventory.get(itemId);
            int oldQuantity = item.getQuantity();
            item.setQuantity(newQuantity);
            
            String changeMsg = newQuantity > oldQuantity ? 
                GREEN + "↑ Increased" : RED + "↓ Decreased";
            
            System.out.println(changeMsg + " quantity for " + item.getName() + 
                              " from " + oldQuantity + " to " + newQuantity + RESET);
        } else {
            System.out.println(YELLOW + "! Item with ID " + itemId + " not found in inventory" + RESET);
        }
    }
    
    @Override
    public String getAllStockItems() {
        StringBuilder result = new StringBuilder();
        
        if (inventory.isEmpty()) {
            return YELLOW + "No items in stock." + RESET;
        }
        
        // We'll just return a simple text representation since we have the colorful display method
        result.append("Total items in inventory: ").append(inventory.size()).append("\n");
        for (StockItem item : inventory.values()) {
            result.append("- ").append(item.getName())
                  .append(" (").append(item.getId()).append("): ")
                  .append(item.getQuantity()).append(" units, $")
                  .append(String.format("%.2f", item.getPrice())).append("\n");
        }
        
        return result.toString();
    }
    
    @Override
    public void displayColorfulStockTable() {
        if (inventory.isEmpty()) {
            System.out.println(YELLOW + "No items in stock." + RESET);
            return;
        }
        
        // Header line
        String header = String.format(
            "┌───────┬─────────────────────┬──────────────┬─────────┬─────────┬─────────────┬─────────────────┐");
        System.out.println(CYAN + header + RESET);
        
        // Table header
        System.out.println(CYAN + "│" + WHITE + BG_BLUE + " ID    " + RESET + 
                          CYAN + "│" + WHITE + BG_BLUE + " Item Name           " + RESET + 
                          CYAN + "│" + WHITE + BG_BLUE + " Category    " + RESET + 
                          CYAN + "│" + WHITE + BG_BLUE + " Qty     " + RESET + 
                          CYAN + "│" + WHITE + BG_BLUE + " Price   " + RESET + 
                          CYAN + "│" + WHITE + BG_BLUE + " Status      " + RESET + 
                          CYAN + "│" + WHITE + BG_BLUE + " Last Updated    " + RESET + 
                          CYAN + "│" + RESET);
        
        // Middle line
        String midLine = String.format(
            "├───────┼─────────────────────┼──────────────┼─────────┼─────────┼─────────────┼─────────────────┤");
        System.out.println(CYAN + midLine + RESET);
        
        // Table content
        for (StockItem item : inventory.values()) {
            String status = item.getStockStatus();
            String statusColor;
            
            switch (status) {
                case "OUT OF STOCK":
                    statusColor = RED;
                    break;
                case "LOW STOCK":
                    statusColor = YELLOW;
                    break;
                case "OVERSTOCKED":
                    statusColor = PURPLE;
                    break;
                default:
                    statusColor = GREEN;
            }
            
            // Format values to fit in columns
            String id = String.format("%-5s", item.getId());
            String name = String.format("%-19s", truncate(item.getName(), 19));
            String category = String.format("%-12s", truncate(item.getCategory(), 12));
            String qty = String.format("%-7s", item.getQuantity());
            String price = String.format("$%-7.2f", item.getPrice());
            String formattedStatus = String.format("%-11s", status);
            String lastUpdated = String.format("%-17s", truncate(item.getLastUpdated(), 17));
            
            System.out.println(CYAN + "│" + WHITE + " " + id + 
                              CYAN + "│" + WHITE + " " + name + 
                              CYAN + "│" + WHITE + " " + category + 
                              CYAN + "│" + WHITE + " " + qty + 
                              CYAN + "│" + WHITE + " " + price + 
                              CYAN + "│" + statusColor + " " + formattedStatus + 
                              CYAN + "│" + WHITE + " " + lastUpdated + 
                              CYAN + "│" + RESET);
        }
        
        // Bottom line
        String bottomLine = String.format(
            "└───────┴─────────────────────┴──────────────┴─────────┴─────────┴─────────────┴─────────────────┘");
        System.out.println(CYAN + bottomLine + RESET);
    }
    
    private String truncate(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }
    
    @Override
    public String generateRandomStockReport() {
        String[] reportTypes = {"Low Stock Alert", "Overstocked Items", "Sales Performance", "Price Analysis"};
        int reportIndex = random.nextInt(reportTypes.length);
        
        StringBuilder report = new StringBuilder();
        report.append(PURPLE + "╔══════════════════════════════════════════════════════════╗\n");
        report.append("║              RANDOM STOCK REPORT: " + reportTypes[reportIndex] + "           ║\n");
        report.append("╚══════════════════════════════════════════════════════════╝\n" + RESET);
        
        // Filter items based on report type
        List<StockItem> relevantItems = new ArrayList<>();
        
        switch (reportIndex) {
            case 0: // Low Stock Alert
                for (StockItem item : inventory.values()) {
                    if (item.getQuantity() < item.getReorderLevel()) {
                        relevantItems.add(item);
                    }
                }
                break;
            case 1: // Overstocked Items
                for (StockItem item : inventory.values()) {
                    if (item.getQuantity() > 50) {
                        relevantItems.add(item);
                    }
                }
                break;
            case 2: // Sales Performance
            case 3: // Price Analysis
                relevantItems.addAll(inventory.values());
                break;
        }
        
        if (relevantItems.isEmpty()) {
            // If no items match the filter, include some random items
            List<StockItem> items = new ArrayList<>(inventory.values());
            int count = Math.min(3, items.size());
            for (int i = 0; i < count; i++) {
                relevantItems.add(items.get(random.nextInt(items.size())));
            }
        }
        
        // Generate report content based on type
        switch (reportIndex) {
            case 0: // Low Stock Alert
                report.append(RED + "Items requiring immediate reorder:\n" + RESET);
                for (StockItem item : relevantItems) {
                    report.append(YELLOW + "• " + item.getName() + RESET + 
                                 " (ID: " + item.getId() + "): " + 
                                 RED + "Only " + item.getQuantity() + " left" + RESET + 
                                 " - Reorder Level: " + item.getReorderLevel() + "\n");
                }
                break;
                
            case 1: // Overstocked Items
                report.append(PURPLE + "Items with excessive stock levels:\n" + RESET);
                for (StockItem item : relevantItems) {
                    report.append(CYAN + "• " + item.getName() + RESET + 
                                 " (ID: " + item.getId() + "): " + 
                                 PURPLE + item.getQuantity() + " units" + RESET + 
                                 " - Consider running promotions\n");
                }
                break;
                
            case 2: // Sales Performance
                report.append(BLUE + "Estimated sales performance:\n" + RESET);
                for (StockItem item : relevantItems) {
                    int salesRate = random.nextInt(10) + 1;
                    report.append(GREEN + "• " + item.getName() + RESET + 
                                 " (ID: " + item.getId() + "): " + 
                                 "Sales Rate: " + salesRate + " units/day, " +
                                 "Est. Depletion: " + (item.getQuantity() / Math.max(1, salesRate)) + " days\n");
                }
                break;
                
            case 3: // Price Analysis
                report.append(BLUE + "Market price comparison:\n" + RESET);
                for (StockItem item : relevantItems) {
                    double marketAvg = item.getPrice() * (0.8 + (random.nextDouble() * 0.4));
                    String priceStatus;
                    if (item.getPrice() < marketAvg * 0.9) {
                        priceStatus = GREEN + "UNDERPRICED" + RESET;
                    } else if (item.getPrice() > marketAvg * 1.1) {
                        priceStatus = RED + "OVERPRICED" + RESET;
                    } else {
                        priceStatus = BLUE + "COMPETITIVE" + RESET;
                    }
                    
                    report.append(CYAN + "• " + item.getName() + RESET + 
                                 " (ID: " + item.getId() + "): " + 
                                 "Our Price: $" + String.format("%.2f", item.getPrice()) + ", " +
                                 "Market Avg: $" + String.format("%.2f", marketAvg) + 
                                 " - Status: " + priceStatus + "\n");
                }
                break;
        }
        
        report.append(PURPLE + "\nReport generated on: " + RESET + java.time.LocalDateTime.now());
        return report.toString();
    }
    
    @Override
    public void simulateStockActivity() {
        System.out.println(YELLOW + "\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("          SIMULATING RANDOM STOCK ACTIVITY");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
        
        if (inventory.isEmpty()) {
            System.out.println(RED + "No inventory items to simulate activity on!" + RESET);
            return;
        }
        
        // Convert to list for easier random access
        List<StockItem> items = new ArrayList<>(inventory.values());
        
        // Perform 3-5 random activities
        int activities = random.nextInt(3) + 3;
        
        for (int i = 0; i < activities; i++) {
            int activityType = random.nextInt(4);
            
            switch (activityType) {
                case 0: // Restock
                    if (!items.isEmpty()) {
                        StockItem item = items.get(random.nextInt(items.size()));
                        int additionalStock = random.nextInt(20) + 5;
                        int oldQty = item.getQuantity();
                        item.setQuantity(oldQty + additionalStock);
                        
                        System.out.println(GREEN + "⬆ Restocked " + item.getName() + 
                                          " (+" + additionalStock + " units, total: " + item.getQuantity() + ")" + RESET);
                    }
                    break;
                    
                case 1: // Sales
                    if (!items.isEmpty()) {
                        StockItem item = items.get(random.nextInt(items.size()));
                        int soldUnits = Math.min(item.getQuantity(), random.nextInt(10) + 1);
                        if (soldUnits > 0) {
                            int oldQty = item.getQuantity();
                            item.setQuantity(oldQty - soldUnits);
                            
                            double saleValue = soldUnits * item.getPrice();
                            System.out.println(BLUE + "$ Sold " + soldUnits + " units of " + item.getName() + 
                                              " ($" + String.format("%.2f", saleValue) + " revenue, " +
                                              "remaining: " + item.getQuantity() + ")" + RESET);
                        }
                    }
                    break;
                    
                case 2: // Price adjustment
                    if (!items.isEmpty()) {
                        StockItem item = items.get(random.nextInt(items.size()));
                        double oldPrice = item.getPrice();
                        double adjustment = (random.nextDouble() * 0.2) - 0.1; // -10% to +10%
                        double newPrice = Math.max(0.99, oldPrice * (1 + adjustment));
                        item.setPrice(newPrice);
                        
                        String direction = adjustment > 0 ? "↑" : "↓";
                        String color = adjustment > 0 ? RED : GREEN;
                        
                        System.out.println(color + direction + " Price adjusted for " + item.getName() + 
                                          " from $" + String.format("%.2f", oldPrice) + 
                                          " to $" + String.format("%.2f", newPrice) + RESET);
                    }
                    break;
                    
                case 3: // Add new item
                    String[] categories = {"Food", "Toys", "Accessories", "Health", "Grooming"};
                    String[] petTypes = {"Dog", "Cat", "Fish", "Bird", "Small Animal"};
                    String[] itemTypes = {"Food", "Toy", "Bed", "Medicine", "Shampoo", "Treats", "Cage", "Tank"};
                    
                    String petType = petTypes[random.nextInt(petTypes.length)];
                    String itemType = itemTypes[random.nextInt(itemTypes.length)];
                    String category = categories[random.nextInt(categories.length)];
                    String name = petType + " " + itemType;
                    
                    // Generate an ID
                    String id = "" + petType.charAt(0) + itemType.charAt(0) + (100 + random.nextInt(900));
                    
                    int qty = random.nextInt(30) + 5;
                    double price = 4.99 + (random.nextDouble() * 45);
                    
                    addItem(id, name, category, qty, price);
                    break;
            }
        }
        
        System.out.println(YELLOW + "\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("          SIMULATION COMPLETE");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
    }
}