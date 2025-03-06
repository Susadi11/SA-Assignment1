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
        
        // Define column widths
        final int idWidth = 10;
        final int trackingWidth = 16;
        final int contactWidth = 25;
        final int timestampWidth = 16;
        final int statusWidth = 15;
        
        // Table header
        String headerLine = "┌" + "─".repeat(idWidth + 2) + 
                           "┬" + "─".repeat(trackingWidth + 2) + 
                           "┬" + "─".repeat(contactWidth + 2) + 
                           "┬" + "─".repeat(timestampWidth + 2) + 
                           "┬" + "─".repeat(statusWidth + 2) + "┐";
        
        String titleLine = "│" + centerString(" 📦 DELIVERY HISTORY ", headerLine.length() - 2) + "│";
        
        String columnHeaderLine = "│" + centerString(" Order ID ", idWidth + 2) + 
                                  "│" + centerString(" Tracking Number ", trackingWidth + 2) + 
                                  "│" + centerString(" Customer Contact ", contactWidth + 2) + 
                                  "│" + centerString(" Timestamp ", timestampWidth + 2) + 
                                  "│" + centerString(" Status ", statusWidth + 2) + "│";
        
        String separatorLine = "├" + "─".repeat(idWidth + 2) + 
                              "┼" + "─".repeat(trackingWidth + 2) + 
                              "┼" + "─".repeat(contactWidth + 2) + 
                              "┼" + "─".repeat(timestampWidth + 2) + 
                              "┼" + "─".repeat(statusWidth + 2) + "┤";
        
        String footerLine = "└" + "─".repeat(idWidth + 2) + 
                            "┴" + "─".repeat(trackingWidth + 2) + 
                            "┴" + "─".repeat(contactWidth + 2) + 
                            "┴" + "─".repeat(timestampWidth + 2) + 
                            "┴" + "─".repeat(statusWidth + 2) + "┘";
        
        System.out.println("\n" + BOLD + CYAN + headerLine + RESET);
        System.out.println(BOLD + CYAN + titleLine + RESET);
        System.out.println(BOLD + CYAN + separatorLine + RESET);
        System.out.println(BOLD + CYAN + columnHeaderLine + RESET);
        System.out.println(BOLD + CYAN + separatorLine + RESET);
        
        // Table content
        for (DeliveryRecord record : records) {
            String statusColor = getStatusColor(record.getStatus());
            
            String row = CYAN + "│" + RESET + " " + 
                        padRight(record.getOrderId(), idWidth) + " " + 
                        CYAN + "│" + RESET + " " + 
                        padRight(record.getTrackingNumber(), trackingWidth) + " " + 
                        CYAN + "│" + RESET + " " + 
                        padRight(record.getCustomerContact(), contactWidth) + " " + 
                        CYAN + "│" + RESET + " " + 
                        padRight(record.getTimestamp(), timestampWidth) + " " + 
                        CYAN + "│" + RESET + " " + 
                        statusColor + padRight(record.getStatus(), statusWidth) + RESET + " " + 
                        CYAN + "│" + RESET;
            
            System.out.println(row);
        }
        
        // Table footer
        System.out.println(BOLD + CYAN + footerLine + RESET);
    }

    /**
     * Helper method to center a string in a fixed width field
     * @param text Text to center
     * @param width Width of the field
     * @return Centered string
     */
    private static String centerString(String text, int width) {
        if (text.length() >= width) {
            return text.substring(0, width);
        }
        int padding = width - text.length();
        int paddingLeft = padding / 2;
        int paddingRight = padding - paddingLeft;
        return " ".repeat(paddingLeft) + text + " ".repeat(paddingRight);
    }

    /**
     * Helper method to right pad a string to a fixed width
     * @param text Text to pad
     * @param width Width to pad to
     * @return Padded string
     */
    private static String padRight(String text, int width) {
        if (text == null) {
            text = "";
        }
        if (text.length() >= width) {
            return text.substring(0, width);
        }
        return text + " ".repeat(width - text.length());
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