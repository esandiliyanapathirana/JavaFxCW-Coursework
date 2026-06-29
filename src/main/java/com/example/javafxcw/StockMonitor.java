package com.example.javafxcw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StockMonitor {
    public static void checkLowStock() {
        String inputFile = "inventory_cleaned.txt";
        System.out.println("Low Stock Warning Area");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {

            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] items = line.split("\\|", -1);

                String itemCode = items[0].trim();
                String itemName = items[1].trim();

                int currentQuantity = Integer.parseInt(items[4].trim());
                int thresholdLimit = Integer.parseInt(items[8].trim());

                if (currentQuantity < thresholdLimit) {
                    System.out.println("Warning!: " + itemName + " [" + itemCode + "] is LOW! Stock:" + currentQuantity + " ( Threshold: " + thresholdLimit + ")");

                }
            }
            System.out.println();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
