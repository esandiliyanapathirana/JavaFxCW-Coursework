package com.example.javafxcw;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public static class CartItem {
        private Part part;
        private int quantity;

        public CartItem(Part part, int quantity) {
            this.part = part;
            this.quantity = quantity;
        }

        public Part getPart() {
            return part;
        }

        public int getQuantity() {
            return quantity;
        }

        public void addQuantity(int amount) {
            this.quantity = this.quantity + amount;
        }
    }

    public void clear() {
        items.clear();
    }

    public int size() {
        return items.size();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public String addToCart(Part partToAdd, int quantityWanted) {
        if (partToAdd == null) {
            return "Error: The part is not found.";
        }
        if (quantityWanted <= 0) {
            return "Error: The Quantity must be greater than 0.";
        }
        if (quantityWanted > partToAdd.getQuantity()) {
            return "Error: Not enough stock. The available stock is : " + partToAdd.getQuantity();
        }

        for (CartItem item : items) {
            if (item.getPart().getCode().equals(partToAdd.getCode())) {
                int newQuantity = item.getQuantity() + quantityWanted;

                if (newQuantity > partToAdd.getQuantity()) {
                    return "Error: Not enough stock for this cart total.";
                }

                item.addQuantity(quantityWanted);
                return "Updated the cart quantity for " + partToAdd.getCode();
            }
        }

        items.add(new CartItem(partToAdd, quantityWanted));
        return "Added to the cart: " + partToAdd.getCode();
    }

    public double calculateFinalTotal() {
        double finalTotal = 0;
        boolean hasEnginePart = false;
        boolean hasElectricalPart = false;

        for (CartItem item : items) {
            Part currentPart = item.getPart();
            int currentQuantity = item.getQuantity();

            double lineTotal = currentPart.getPrice() * currentQuantity;

            if (currentQuantity >= 3) {
                lineTotal = lineTotal * 0.95;
            }

            finalTotal = finalTotal + lineTotal;

            if (currentPart.getCategory().equalsIgnoreCase("Engine")) {
                hasEnginePart = true;
            }
            if (currentPart.getCategory().equalsIgnoreCase("Electrical")) {
                hasElectricalPart = true;
            }
        }

        if (hasEnginePart && hasElectricalPart) {
            finalTotal = finalTotal * 0.90;
        }

        return finalTotal;
    }

    public String checkout(InventoryStore store) {
        if (items.isEmpty()) {
            return "Error: Cart is empty.";
        }

        for (CartItem item : items) {
            Part inventoryPart = store.findByCode(item.getPart().getCode());
            int quantityInCart = item.getQuantity();

            if (inventoryPart == null) {
                return "Error: Missing part in inventory.";
            }
            if (quantityInCart <= 0) {
                return "Error: Invalid quantity.";
            }
            if (quantityInCart > inventoryPart.getQuantity()) {
                return "Error: Not enough stock for " + inventoryPart.getCode();
            }
        }

        double finalTotal = calculateFinalTotal();

        for (CartItem item : items) {
            Part inventoryPart = store.findByCode(item.getPart().getCode());
            int quantityToDeduct = item.getQuantity();

            int updatedStock = inventoryPart.getQuantity() - quantityToDeduct;
            inventoryPart.setQuantity(updatedStock);

            AuditLogger.log("Checkout", inventoryPart.getCode(), quantityToDeduct);
        }

        store.save();
        clear();

        return "Checkout successful. Final total: Rs. " + String.format("%.2f", finalTotal);
    }
}