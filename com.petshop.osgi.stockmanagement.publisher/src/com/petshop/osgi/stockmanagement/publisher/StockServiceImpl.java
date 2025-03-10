package com.petshop.osgi.stockmanagement.publisher;

import com.petshop.osgi.stockmanagement.utils.StockDataGenerator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;

public class StockServiceImpl implements StockService {
    private final Queue<String> stockDataQueue = new LinkedList<>();
    private static final int MAX_ENTRIES = 10;
    private final Map<String, Integer> stockLevels = new HashMap<>();
    private final Random random = new Random();
    
    // Constructor to initialize with some stock data
    public StockServiceImpl() {
        // Initialize with some stock data
        for (int i = 0; i < 5; i++) {
            addStockData();
        }
    }

    @Override
    public String getStockData() {
        StringBuilder stockData = new StringBuilder();
        stockData.append(StockDataGenerator.getTableHeader()).append("\n");

        synchronized (stockDataQueue) {
            for (String data : stockDataQueue) {
                stockData.append(data).append("\n");
            }
        }

        stockData.append(StockDataGenerator.getTableFooter());
        return stockData.toString();
    }

    // Method to add new stock data to the queue
    public void addStockData() {
        synchronized (stockDataQueue) {
            if (stockDataQueue.size() >= MAX_ENTRIES) {
                stockDataQueue.poll(); // Remove the oldest entry
            }
            
            // Generate a new stock entry and store it
            String foodId = StockDataGenerator.generateFoodId();
            int quantity = StockDataGenerator.generateQuantity();
            stockLevels.put(foodId, quantity);
            
            String stockEntry = StockDataGenerator.generateStockData(foodId, quantity);
            stockDataQueue.offer(stockEntry);
            
            System.out.println("Added new stock item: " + foodId + " with quantity: " + quantity);
        }
    }
    
    // Method to decrease stock for an order
    public void decreaseStockForOrder(String orderId) {
        synchronized (stockDataQueue) {
            if (stockLevels.isEmpty()) {
                System.out.println("No stock items available to decrease.");
                return;
            }
            
            // Select a random item from stock to decrease
            String[] foodIds = stockLevels.keySet().toArray(new String[0]);
            if (foodIds.length == 0) {
                return;
            }
            
            String selectedFoodId = foodIds[random.nextInt(foodIds.length)];
            int currentQuantity = stockLevels.get(selectedFoodId);
            
            // Calculate a random amount to decrease (between 10% and 50% of current)
            int decreaseAmount = Math.max(1, (int)(currentQuantity * (0.1 + 0.4 * random.nextDouble())));
            int newQuantity = Math.max(0, currentQuantity - decreaseAmount);
            
            // Update the stock level
            stockLevels.put(selectedFoodId, newQuantity);
            
            // Remove the old entry for this item and add a new one
            Queue<String> updatedQueue = new LinkedList<>();
            for (String entry : stockDataQueue) {
                if (!entry.contains(selectedFoodId)) {
                    updatedQueue.offer(entry);
                }
            }
            
            // Add the updated entry
            updatedQueue.offer(StockDataGenerator.generateStockData(selectedFoodId, newQuantity));
            
            // Replace the queue
            stockDataQueue.clear();
            stockDataQueue.addAll(updatedQueue);
            
            System.out.println("For order " + orderId + ": Decreased stock of " + selectedFoodId + 
                               " from " + currentQuantity + " to " + newQuantity + 
                               " (reduced by " + decreaseAmount + " units)");
        }
    }
}
