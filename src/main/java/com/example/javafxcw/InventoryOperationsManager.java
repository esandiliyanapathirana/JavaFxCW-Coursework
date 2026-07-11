package com.example.javafxcw;

import java.io.*;

public class InventoryOperationsManager {

    private static final String file_path = "inventory_cleaned.txt";
    private static final String tempory_file_path = "tempory_file.txt";

    private static boolean isDuplicate(String code) {
        try(BufferedReader reader = new BufferedReader(new FileReader(file_path))) {
            String line;
            while((line = reader.readLine()) != null ) {
                if(line.split("\\|")[0].trim().equals(code.trim())) {
                    return true;
                }
            }
        } catch (IOException ignored){}
        return false;

}

public static String addItem(String  code, String name, String brand, String price, String quantity, String category, String date, String image, String threshold) {
    if (isDuplicate(code)) {
        return "Error: Item code already exists.";
    }

    String record = String.join("|", code, name, brand, price, quantity, category, date, image, threshold);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_path, true))) {
        writer.write(record);
        writer.newLine();
        return "Successfully added item: " + name;
    } catch (IOException e) {
        return " The changes were not save";
    }
}

public static String deleteItems (String targetCode) {
    File originalFile = new File(file_path);
    File temporyFile = new File(tempory_file_path);
    boolean exists = false;

    try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
         BufferedWriter writer = new BufferedWriter(new FileWriter(temporyFile))) {

        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split("\\|");
            if (data.length > 0 && data[0].trim().equals(targetCode.trim())) {
                exists = true;

            } else {
                writer.write(line);
                writer.newLine();
            }
        }
    } catch (IOException e) {
        return "Error! Can't process file";
    }

    if (exists && originalFile.delete() && temporyFile.renameTo(originalFile)) {
        return "Successfully deleted the item" + targetCode;
    }

    temporyFile.delete();
    return exists ? "Error! Could not update the file system." : "Error! The Item code does not exists";

}

public static String updateItems(String target, String name, String brand, String price, String quantity, String category, String date, String image, String threshold) {
    File originalFile = new File(file_path);
    File temporyFile = new File(tempory_file_path);
    boolean exists = false;

try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
     BufferedWriter writer = new BufferedWriter(new FileWriter(temporyFile))) {

    String line;
    while ((line = reader.readLine()) != null) {
        String[] data = line.split("\\|");
        if (data.length > 0 && data[0].trim().equals(target.trim())) {
            exists = true;

            String updatedRecord = String.join("|", target, name, brand, price, quantity, category, date, image, threshold);
            writer.write(updatedRecord);
        } else {
            writer.write(line);
        }
        writer.newLine();
    }
} catch (IOException e) {
    return "Error! Can't process the file.";
}

if (exists && originalFile.delete() && temporyFile.renameTo(originalFile)) {
    return "Successfully updated the item " + target;
}

temporyFile.delete();
return exists ? "Error! Could not update the file system " : "Error the item code does not exists.";

    }
}