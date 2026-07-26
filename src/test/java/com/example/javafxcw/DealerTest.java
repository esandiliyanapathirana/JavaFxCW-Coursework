package com.example.javafxcw;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DealerTest {

    @Test
    void dealerStoresAllFields() {
        Dealer dealer = new Dealer("D01", "AutoMart", "0771234567", "Malabe");
        assertEquals("D01", dealer.getId());
        assertEquals("AutoMart", dealer.getName());
        assertEquals("0771234567", dealer.getPhone());
        assertEquals("Malabe", dealer.getLocation());
    }

    @Test
    void toDisplayContainsLocation() {
        Dealer dealer = new Dealer("D01", "AutoMart", "0771234567", "Malabe");
        String text = dealer.toDisplay();
        assertTrue(text.contains("Malabe"));
        assertTrue(text.contains("AutoMart"));
    }
}
