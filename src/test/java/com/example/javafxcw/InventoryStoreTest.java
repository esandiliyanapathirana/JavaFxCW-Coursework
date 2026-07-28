package com.example.javafxcw;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryStoreTest {

    private static final Path INVENTORY_FILE = Path.of("inventory_cleaned.txt");
    private byte[] inventoryBackup;

    @BeforeEach
    void backupInventoryFile() throws Exception {
        if (Files.exists(INVENTORY_FILE)) {
            inventoryBackup = Files.readAllBytes(INVENTORY_FILE);
        } else {
            inventoryBackup = new byte[0];
        }
    }

    @AfterEach
    void restoreInventoryFile() throws Exception {
        Files.write(INVENTORY_FILE, inventoryBackup);
    }

    private Part samplePart(String code, String category, double price, int quantity) {
        return new Part(code, "Name", "Brand", price, quantity, category,
                "2024-01-01", "spark.jpg", 10);
    }

    @Test
    void addPartRejectsDuplicateCode() {
        InventoryStore store = new InventoryStore();
        store.getParts().add(samplePart("P001", "Engine", 1000, 5));

        Part duplicate = samplePart("P001", "Engine", 2000, 3);
        String message = store.addPart(duplicate);

        assertTrue(message.startsWith("Error"));
        assertEquals(1, store.getParts().size());
    }

    @Test
    void sortByCategoryThenCodeWorks() {
        InventoryStore store = new InventoryStore();
        store.getParts().add(samplePart("P002", "Engine", 1000, 5));
        store.getParts().add(samplePart("P001", "Brakes", 500, 5));
        store.getParts().add(samplePart("P003", "Brakes", 700, 5));

        store.sortByCategoryThenCode();

        assertEquals("P001", store.getParts().get(0).getCode());
        assertEquals("P003", store.getParts().get(1).getCode());
        assertEquals("P002", store.getParts().get(2).getCode());
    }

    @Test
    void totalsCountAndValue() {
        InventoryStore store = new InventoryStore();
        store.getParts().add(samplePart("P001", "Engine", 100.0, 2)); // 200
        store.getParts().add(samplePart("P002", "Brakes", 50.0, 4));  // 200

        assertEquals(2, store.getTotalCount());
        assertEquals(400.0, store.getTotalValue(), 0.001);
    }

    @Test
    void deletePartRemovesExistingCode() {
        InventoryStore store = new InventoryStore();
        store.getParts().add(samplePart("P001", "Engine", 1000, 5));

        String message = store.deletePart("P001");

        assertTrue(message.toLowerCase().contains("success")
                || message.toLowerCase().contains("deleted"));
        assertEquals(0, store.getParts().size());
    }
}
