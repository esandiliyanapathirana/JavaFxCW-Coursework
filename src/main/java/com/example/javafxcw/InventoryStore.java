package com.example.javafxcw;

import java.io.*;
import java.util.ArrayList;

public class InventoryStore {
    private static final String file_path = "inventory_cleaned.txt";
    private ArrayList<Part> parts = new ArrayList<Part>();

    public ArrayList<Part> getParts() {
        return parts;
    }


    public void load() {
        parts.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file_path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] data = line.split("\\|", -1);
                if (data.length < 9) {
                    continue;
                }
                try {
                    Part part = new Part(
                            data[0].trim(),
                            data[1].trim(),
                            data[2].trim(),
                            Double.parseDouble(data[3].trim()),
                            Integer.parseInt(data[4].trim()),
                            data[5].trim(),
                            data[6].trim(),
                            data[7].trim(),
                            Integer.parseInt(data[8].trim())
                    );
                    parts.add(part);
                } catch (Exception e) {
                    System.err.println(" Skipping the invalid entry: " + line);

                }
            }
        } catch (IOException e) {
            System.err.println("Error occurred when loading inventory: " + e.getMessage());
        }
    }


    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_path))) {
            for (int i = 0; i < parts.size(); i++) {
                writer.write(parts.get(i).toFileLine());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error occurred when saving inventory: " + e.getMessage());
        }
    }

    public boolean codeExists(String code) {
        for (int i = 0; i < parts.size(); i++) {
            if (parts.get(i).getCode().equalsIgnoreCase(code.trim())) {
                return true;
            }
        }
        return false;
    }

    public String addPart(Part part) {
        if (part.getCode() == null || part.getCode().trim().isEmpty()) {
            return "Error: The Code is required.";
        }
        if (codeExists(part.getCode())) {
            return "Error: The Item code already exists.";
        }
        if (part.getPrice() < 0 || part.getQuantity() < 0) {
            return "Error: The Price and quantity cannot be negative.";
        }
        parts.add(part);
        save();
        AuditLogger.log("Add Part", part.getCode(), part.getQuantity());
        return "Successfully added the item: " + part.getName();
    }

    public String deletePart(String code) {
        for (int i = 0; i < parts.size(); i++) {
            if (parts.get(i).getCode().equalsIgnoreCase(code.trim())) {
                Part removed = parts.remove(i);
                save();
                AuditLogger.log("Delete Part", removed.getCode(), removed.getQuantity());
                return "Successfully deleted the item: " + code;
            }
        }
        return "Error: The Item code does not exist.";
    }

    public String updatePart(String code, String name, String brand, double price,
                             int quantity, String category, String date,
                             String image, int threshold) {
        for (int i = 0; i < parts.size(); i++) {
            Part p = parts.get(i);
            if (p.getCode().equalsIgnoreCase(code.trim())) {
                if (price < 0 || quantity < 0) {
                    return "Error: The Price and quantity cannot be negative.";
                }
                p.setName(name);
                p.setBrand(brand);
                p.setPrice(price);
                p.setQuantity(quantity);
                p.setCategory(category);
                p.setDate(date);
                p.setImage(image);
                p.setThreshold(threshold);
                save();
                AuditLogger.log("Update Part", code, quantity);
                return "Successfully updated the item: " + code;
            }
        }
        return "Error: The Item code does not exist.";
    }

    public Part findByCode(String code) {
        for (int i = 0; i < parts.size(); i++) {
            if (parts.get(i).getCode().equalsIgnoreCase(code.trim())) {
                return parts.get(i);
            }
        }
        return null;
    }


    public void sortByCategoryThenCode() {
        for (int i = 0; i < parts.size() - 1; i++) {
            for (int j = 0; j < parts.size() - 1 - i; j++) {
                Part a = parts.get(j);
                Part b = parts.get(j + 1);

                int categoryCompare = a.getCategory().compareToIgnoreCase(b.getCategory());
                boolean needSwap = false;

                if (categoryCompare > 0) {
                    needSwap = true;
                } else if (categoryCompare == 0) {
                    if (a.getCode().compareToIgnoreCase(b.getCode()) > 0) {
                        needSwap = true;
                    }
                }

                if (needSwap) {
                    parts.set(j, b);
                    parts.set(j + 1, a);
                }
            }
        }
    }

    public int getTotalCount() {
        return parts.size();
    }

    public double getTotalValue() {
        double total = 0;
        for (int i = 0; i < parts.size(); i++) {
            Part p = parts.get(i);
            total = total + (p.getPrice() * p.getQuantity());
        }
        return total;
    }


    public ArrayList<Part> search(String category, double minPrice, double maxPrice, String keyword) {
        ArrayList<Part> result = new ArrayList<Part>();

        for (int i = 0; i < parts.size(); i++) {
            Part p = parts.get(i);
            boolean ok = true;

            if (category != null && !category.trim().isEmpty()) {
                if (!p.getCategory().equalsIgnoreCase(category.trim())) {
                    ok = false;
                }
            }

            if (ok && minPrice >= 0) {
                if (p.getPrice() < minPrice) {
                    ok = false;
                }
            }

            if (ok && maxPrice >= 0) {
                if (p.getPrice() > maxPrice) {
                    ok = false;
                }
            }

            if (ok && keyword != null && !keyword.trim().isEmpty()) {
                String key = keyword.trim().toLowerCase();
                String name = p.getName().toLowerCase();
                String code = p.getCode().toLowerCase();
                String brand = p.getBrand().toLowerCase();
                if (!name.contains(key) && !code.contains(key) && !brand.contains(key)) {
                    ok = false;
                }
            }

            if (ok) {
                result.add(p);
            }
        }
        return result;
    }

    public ArrayList<Part> getLowStockParts() {
        ArrayList<Part> low = new ArrayList<Part>();
        for (int i = 0; i < parts.size(); i++) {
            if (parts.get(i).isLowStock()) {
                low.add(parts.get(i));
            }
        }
        return low;
    }
}

