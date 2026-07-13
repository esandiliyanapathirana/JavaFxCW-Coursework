package com.example.javafxcw;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your Choice: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "clean Inventory":
                LegacyDataCleaner.inventoryClean();
                break;

            case "clean Dealers":
                LegacyDataCleaner.dealersClean();
                break;

            case "low stock monitoring":
                StockMonitor.checkLowStock();
                break;



            case "Selecting Random Dealers":
                RandomDealers.selectDealers();
                break;

            default:
                System.out.println("Invalid choice. Exiting application.");
                break;
        }

        scanner.close();
    }
}
