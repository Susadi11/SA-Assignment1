package com.petfood.delivery.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * A utility logger for the Pet Food Delivery service that formats output with colors and tables.
 */
public class DeliveryServiceLogger {
    // ANSI color codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String BOLD = "\u001B[1m";
    
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Logs an informational message
     * @param source The source of the log
     * @param message The message to log
     */
    public static void info(String source, String message) {
        String timestamp = getCurrentTimestamp();
        System.out.println(BLUE + "[INFO] " + timestamp + " [" + source + "] " + RESET + message);
    }
    
    /**
     * Logs a success message
     * @param source The source of the log
     * @param message The message to log
     */
    public static void success(String source, String message) {
        String timestamp = getCurrentTimestamp();
        System.out.println(GREEN + "[SUCCESS] " + timestamp + " [" + source + "] " + RESET + message);
    }
    
    /**
     * Logs a warning message
     * @param source The source of the log
     * @param message The message to log
     */
    public static void warning(String source, String message) {
        String timestamp = getCurrentTimestamp();
        System.out.println(YELLOW + "[WARNING] " + timestamp + " [" + source + "] " + RESET + message);
    }
    
    /**
     * Logs an error message
     * @param source The source of the log
     * @param message The message to log
     */
    public static void error(String source, String message) {
        String timestamp = getCurrentTimestamp();
        System.out.println(RED + "[ERROR] " + timestamp + " [" + source + "] " + RESET + message);
    }
    
    /**
     * Displays a table of delivery records
     * @param records The list of delivery records to display
     */
    public static void displayDeliveryTable(List<DeliveryRecord> records) {
        if (records.isEmpty()) {
            info("DeliveryTable", "No delivery records to display.");
            return;
        }
        
        // Table header
        System.out.println("\n" + BOLD + CYAN + "┌─────────────────────────────────────────────────────────────────────────────────────────────────────┐" + RESET);
        System.out.println(BOLD + CYAN + "│                                       📦 DELIVERY HISTORY                                         │" + RESET);
        System.out.println(BOLD + CYAN + "├──────────────┬──────────────────┬───────────────────────────┬──────────────────┬─────────────────┤" + RESET);
        System.out.println(BOLD + CYAN + "│ Order ID     │ Tracking Number  │ Customer Contact          │ Timestamp        │ Status          │" + RESET);
        System.out.println(BOLD + CYAN + "├──────────────┼──────────────────┼───────────────────────────┼──────────────────┼─────────────────┤" + RESET);
        
        // Table content
        for (DeliveryRecord record : records) {
            String statusColor = getStatusColor(record.getStatus());
            System.out.println(CYAN + "│" + RESET + " " + BOLD + record.getOrderId() + "   " + 
                               CYAN + "│" + RESET + " " + record.getTrackingNumber() + "    " + 
                               CYAN + "│" + RESET + " " + record.getCustomerContact() + "      " + 
                               CYAN + "│" + RESET + " " + record.getTimestamp() + " " + 
                               CYAN + "│" + RESET + " " + statusColor + record.getStatus() + RESET + "        " + 
                               CYAN + "│" + RESET);
        }
        
        // Table footer
        System.out.println(BOLD + CYAN + "└──────────────┴──────────────────┴───────────────────────────┴──────────────────┴─────────────────┘" + RESET);
    }
    
    /**
     * Gets the color for status based on the status value
     * @param status The status string
     * @return The color code for the status
     */
    private static String getStatusColor(String status) {
        switch (status.toLowerCase()) {
            case "shipped":
                return GREEN;
            case "processing":
                return YELLOW;
            case "delivered":
                return BLUE;
            case "cancelled":
                return RED;
            default:
                return WHITE;
        }
    }
    
    /**
     * Gets the current timestamp formatted as a string
     * @return The current timestamp
     */
    private static String getCurrentTimestamp() {
        return LocalDateTime.now().format(timeFormatter);
    }
}