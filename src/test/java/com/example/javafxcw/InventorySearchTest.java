package com.example.javafxcw;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class InventorySearchTest {

    @Test
    void searchByCategoryAndKeyword() {
        InventoryStore store = new InventoryStore();
        store.getParts().add(new Part("P001", "Piston", "Bajaj", 4500, 15,
                "Engine", "2023-10-12", "piston4s.jpg", 10));
        store.getParts().add(new Part("P004", "Spark Plug", "NGK", 850, 50,
                "Electrical", "2024-01-05", "spark.jpg", 10));

        ArrayList<Part> found = store.search("Engine", -1, -1, "Piston");
        assertEquals(1, found.size());
        assertEquals("P001", found.get(0).getCode());
    }

    @Test
    void searchByPriceRange() {
        InventoryStore store = new InventoryStore();
        store.getParts().add(new Part("P001", "Piston", "Bajaj", 4500, 15,
                "Engine", "2023-10-12", "piston4s.jpg", 10));
        store.getParts().add(new Part("P006", "Bulb", "Unknown", 450, 30,
                "Electrical", "2023-11-20", "hl_bulb.jpg", 10));

        ArrayList<Part> found = store.search(null, 400, 500, null);
        assertEquals(1, found.size());
        assertEquals("P006", found.get(0).getCode());
    }
}
