package com.example.javafxcw;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartTest {

    private Part makePart(String code, String category, double price, int stock) {
        return new Part(code, "Test Part", "Brand", price, stock, category,
                "2024-01-01", "spark.jpg", 10);
    }

    @Test
    void rejectNullPart() {
        Cart cart = new Cart();
        String message = cart.addToCart(null, 1);
        assertTrue(message.startsWith("Error"));
    }

    @Test
    void rejectQuantityZero() {
        Cart cart = new Cart();
        Part part = makePart("P001", "Engine", 1000, 10);
        String message = cart.addToCart(part, 0);
        assertTrue(message.startsWith("Error"));
    }

    @Test
    void rejectMoreThanStock() {
        Cart cart = new Cart();
        Part part = makePart("P001", "Engine", 1000, 2);
        String message = cart.addToCart(part, 5);
        assertTrue(message.startsWith("Error"));
    }

    @Test
    void bulkDiscountFivePercent() {
        Cart cart = new Cart();
        Part part = makePart("P001", "Engine", 100.0, 20);
        cart.addToCart(part, 3);
        assertEquals(285.0, cart.calculateFinalTotal(), 0.001);
    }

    @Test
    void synergyDiscountTenPercent() {
        Cart cart = new Cart();
        Part engine = makePart("P001", "Engine", 100.0, 20);
        Part electrical = makePart("P004", "Electrical", 100.0, 20);
        cart.addToCart(engine, 1);
        cart.addToCart(electrical, 1);
        assertEquals(180.0, cart.calculateFinalTotal(), 0.001);
    }
}
