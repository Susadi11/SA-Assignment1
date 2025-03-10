package com.petfood.catalog.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CatalogServiceLogger {
    // ANSI color codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";

    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static final String BOLD = null;

    public static void info(String source, String message) {
        String timestamp = getCurrentTimestamp();
        System.out.println(BLUE + "[INFO] " + timestamp + " [" + source + "] " + RESET + message);
    }

    public static void success(String source, String message) {
        String timestamp = getCurrentTimestamp();
        System.out.println(GREEN + "[SUCCESS] " + timestamp + " [" + source + "] " + RESET + message);
    }

    public static void warning(String source, String message) {
        String timestamp = getCurrentTimestamp();
        System.out.println(YELLOW + "[WARNING] " + timestamp + " [" + source + "] " + RESET + message);
    }

    public static void error(String source, String message) {
        String timestamp = getCurrentTimestamp();
        System.out.println(RED + "[ERROR] " + timestamp + " [" + source + "] " + RESET + message);
    }

    private static String getCurrentTimestamp() {
        return LocalDateTime.now().format(timeFormatter);
    }
}