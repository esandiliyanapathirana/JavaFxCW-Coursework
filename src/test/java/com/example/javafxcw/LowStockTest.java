package com.example.javafxcw;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LowStockTest {

    @Test
    void partIsLowStockWhenQuantityBelowThreshold() {
        Part part = new Part("P008", "Filter", "Piaggio", 1100, 0,
                "Engine", "2024-02-28", "filter_ape.jpeg", 10);
        assertTrue(part.isLowStock());
    }

    @Test
    void partIsNotLowStockWhenQuantityAtOrAboveThreshold() {
        Part part = new Part("P001", "Piston", "Bajaj", 4500, 15,
                "Engine", "2023-10-12", "piston4s.jpg", 10);
        assertFalse(part.isLowStock());
    }

    @Test
    void userCanSetDifferentThresholdPerPart() {
        Part special = new Part("P099", "Special", "Local", 500, 7,
                "Brakes", "2024-01-01", "brakepad.png", 5);
        // quantity 7, threshold 5 => NOT low stock
        assertFalse(special.isLowStock());

        Part another = new Part("P098", "Other", "Local", 500, 4,
                "Brakes", "2024-01-01", "brakepad.png", 5);
        // quantity 4, threshold 5 => IS low stock
        assertTrue(another.isLowStock());
    }
}
