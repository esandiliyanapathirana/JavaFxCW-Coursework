package com.example.javafxcw;

public class AppData {
    public static InventoryStore inventoryStore = new InventoryStore();
    public static DealerStore dealerStore = new DealerStore();
    public static Cart cart = new Cart();

    public static void loadAll() {
        inventoryStore.load();
        dealerStore.load();
    }
}
