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
            System.out.println("Successfully added item: + name");
        } catch (IOException e) {
            System.err.println("Error! Can't write to file" + e.getMessage());
        }
    }
    private static String promptUser(Scanner scanner, String prompt) {
        System.out.println(prompt);
        return scanner.nextLine().trim();
    }


}
