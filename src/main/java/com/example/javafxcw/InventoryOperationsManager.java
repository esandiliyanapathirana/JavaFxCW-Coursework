package com.example.javafxcw;

import java.io.*;
import java.util.Scanner;

public class InventoryOperationsManager {

    private static final String file_path = "inventory_cleand.txt";
    private static final String tempory_file_path = "tempory_file.txt";

    public static void addItem() {
        Scanner scanner = new Scanner(System.in);
        boolean exists = true;
        String code = "";

        while(exists) {
            System.out.println("Enter the item code:");
            code = scanner.nextLine().trim();
            exists = false;

            try (BufferedReader reader = new BufferedReader(new FileReader(file_path))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split("\\|");
                    if (data.length > 0 && data[0].trim().equals(code)){
                        System.out.println("Duplicate Found!");
                        exists = true;
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error!: Could not read the file!");
            }
            System.out.println();
        }

        String name = promptUser(scanner, "Enter the item Name; ");
        String brand = promptUser(scanner, "Enter the item Brand; ");
        String price = promptUser(scanner, "Enter the item Price; ");
        String quantity = promptUser(scanner, "Enter the item Quantity; ");
        String category = promptUser(scanner, "Enter the item Category; ");
        String date = promptUser(scanner, "Enter the Date (YYYY-MM_DD); ");
        String image = promptUser(scanner, "Enter the item image file Name; ");
        String threshold = promptUser(scanner, "Enter the item Threshold; ");

        String record = String.join("|", code, name, brand, price, quantity, category, date, image, threshold);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_path, true))) {
            writer.write(record);
            writer.newLine();
            System.out.println("Successfully added item:" + name);
        } catch (IOException e) {
            System.err.println("Error! Can't write to file" + e.getMessage());
        }
    }

    public static void deleteItems() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the code of the item that you want to delete: ");
        String targetCode = scanner.nextLine().trim();

        File originalFile = new File(file_path);
        File temporyFile = new File(tempory_file_path);
        boolean exists = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(temporyFile))) {

            String line;
            while((line = reader.readLine()) != null) {
                String [] data = line.split("\\|");

                if (data.length > 0 && data[0].trim().equals(targetCode)) {
                    exists = true;
                    continue;
                }

                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error! Can't process the file" + e.getMessage());
            return;
        }

        finalizeFileReplacement(originalFile, temporyFile, exists, "Successfully deleted the item" + targetCode, "Error! Deletion Failed");
    }

    private static void finalizeFileReplacement(File originalFile, File temporyFile, boolean exists, String sucessMessage, String failureMessage) {
        if(exists) {
            if(originalFile.delete()) {
                if(temporyFile.renameTo(originalFile)) {
                    System.out.println(sucessMessage);
                } else {
                    System.out.println("Error! Could not update the file system");
                }
            } else {
                System.out.println(failureMessage);
            }
        } else {
            System.out.println("Error! The Item code does not exists");
            temporyFile.delete();
        }
    }


    private static String promptUser(Scanner scanner, String prompt) {
        System.out.println(prompt);
        return scanner.nextLine().trim();

    }

}
