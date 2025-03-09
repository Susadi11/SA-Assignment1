package com.petshop.osgi.stockmanagement.publisher;


import com.petshop.osgi.stockmanagement.utils.StockDataGenerator;
import java.util.LinkedList;
import java.util.Queue;

public class StockServiceImpl implements StockService {
    private final Queue<String> stockDataQueue = new LinkedList<>();
    private static final int MAX_ENTRIES = 10;

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
            if (stockDataQueue.size() == MAX_ENTRIES) {
                stockDataQueue.poll(); // Remove the oldest entry
            }
            stockDataQueue.offer(StockDataGenerator.generateStockData()); // Add new entry
        }
    }
}