package com.example.javafxcw;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.time.LocalDate;

public class LegacyDataCleaner {
    public static void inventoryClean() {
        String inputFile = "inventory_legacy.txt";
        String outputFile = "inventory_cleaned.txt";
        int lowStockLevel = 10;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {

                    String[] pieces = line.split("[,;|]", -1);
                    ProductInfo product = new ProductInfo(pieces);

                    double cleanPrice = CleanUpPrice(product.messyPrice);
                    int quantity = product.messyQuantity.isEmpty() ? 0 : Integer.parseInt(product.messyQuantity);

                    String cleanCategory = product.category;
                    if (!cleanCategory.isEmpty()) {
                        cleanCategory = cleanCategory.substring(0, 1).toUpperCase()
                                + cleanCategory.substring(1).toLowerCase();
                    }

                    String cleanDate = CleanUpDate(product.messyDate);

                    String newLineToWrite = String.format("%s|%s|%s|%.2f|%d|%s|%s|%s|%d",
                            product.id, product.name, product.brand, cleanPrice,
                            quantity, cleanCategory, cleanDate, product.image, lowStockLevel);
                    writer.write(newLineToWrite);
                    writer.newLine();
                }
                System.out.println("Data cleaning completed. New inventory file is saved as: " + outputFile);
            }
        } catch (IOException error) {
            System.err.println("Error occred. Can't process the file:" + error.getMessage());
        }
    }

    private static double CleanUpPrice(String messyPrice) {
        String numberOnly = messyPrice.replace("Rs.", "").replace("Rs", "").trim();
        return numberOnly.isEmpty() ? 0.00 : Double.parseDouble(numberOnly);
    }

    private static String CleanUpDate(String messyDate) {
        DateTimeFormatter perfectFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        DateTimeFormatter[] expectedFormats = {
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("MMM dd,yyyy", Locale.ENGLISH),
                DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH),
        };

        String defaultDate = "Undated";
        if (!messyDate.isEmpty()) {
            for (DateTimeFormatter format : expectedFormats) {
                try {
                    LocalDate tempDate = LocalDate.parse(messyDate, format);
                    return tempDate.format(perfectFormat);
                } catch (Exception ignoredError) {

                }
            }
        }
        return defaultDate;
    }

    private static class ProductInfo {
        String id;
        String name;
        String brand;
        String messyPrice;
        String messyQuantity;
        String category;
        String messyDate;
        String image;

        ProductInfo(String[] dataPieces) {
            this.id = dataPieces.length > 0 ? dataPieces[0].trim() : "";
            this.name = dataPieces.length > 1 ? dataPieces[1].trim() : "unknown";
            this.brand = dataPieces.length > 2 && !dataPieces[2].trim().isEmpty() ? dataPieces[2].trim() : "Unknown";
            this.messyPrice = dataPieces.length > 3 ? dataPieces[3].trim() : "Rs.0.00";
            this.messyQuantity = dataPieces.length > 4 ? dataPieces[4].trim() : "0";
            this.category = dataPieces.length > 5 ? dataPieces[5].trim() : "General";
            this.messyDate = dataPieces.length > 6 ? dataPieces[6].trim() : "";
            this.image = dataPieces.length > 7 && !dataPieces[7].trim().isEmpty() ? dataPieces[7].trim() : "No Image";
        }
    }

    public static void dealersClean() {
        String inputFile = "dealers_legacy.txt";
        String outputFile = "dealers_cleaned.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] pieces = line.split("[,;|]", -1);

                    DealerInfo dealer = new DealerInfo(pieces);

                    String newLineToWrite = String.format("%s|%s|%s|%s",
                            dealer.id, dealer.name, dealer.phone, dealer.location);

                    writer.write(newLineToWrite);
                    writer.newLine();
                }
            }
            System.out.println("Data cleaning is completed. New dealers file is saved as: " + outputFile);

        } catch (IOException error) {
            System.err.println("Error occured.Can't process the file: " + error.getMessage());
        }
    }

    private static class DealerInfo {
        String id;
        String name;
        String phone;
        String location;

        DealerInfo(String[] dataPieces) {
            this.id = dataPieces.length > 0 ? dataPieces[0].trim() : "";
            this.name = dataPieces.length > 1 ? dataPieces[1].trim() : "Unknown";
            this.phone = dataPieces.length > 2 && !dataPieces[2].trim().isEmpty() ? dataPieces[2].trim() : "No Contact";
            this.location = dataPieces.length > 3 ? dataPieces[3].trim() : "Unknown";
        }
    }
}
