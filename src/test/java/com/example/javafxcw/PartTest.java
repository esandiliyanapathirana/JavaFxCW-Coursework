package com.example.javafxcw;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PartTest {

    @Test
    void lowStockWhenQuantityBelowThreshold() {
        Part part = new Part("P008", "Filter", "Piaggio", 1100, 0,
                "Engine", "2024-02-28", "filter_ape.jpeg", 10);
        assertTrue(part.isLowStock());
    }

    @Test
    void notLowStockWhenQuantityAtOrAboveThreshold() {
        Part part = new Part("P001", "Piston", "Bajaj", 4500, 15,
                "Engine", "2023-10-12", "piston4s.jpg", 10);
        assertFalse(part.isLowStock());
    }

    @Test
    void toFileLineUsesPipeSeparator() {
        Part part = new Part("P001", "Piston", "Bajaj", 4500, 15,
                "Engine", "2023-10-12", "piston4s.jpg", 10);
        String line = part.toFileLine();
        assertTrue(line.startsWith("P001|"));
        assertTrue(line.contains("|Engine|"));
        assertTrue(line.endsWith("|10"));
    }
}
