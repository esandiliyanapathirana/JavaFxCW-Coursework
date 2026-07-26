package com.example.javafxcw;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class DealerStoreTest {

    @Test
    void selectFourUniqueDealers() {
        DealerStore store = new DealerStore();
        store.load();

        ArrayList<Dealer> selected = store.selectFourUniqueSortedByLocation();

        assertEquals(4, selected.size());

        for (int i = 0; i < selected.size(); i++) {
            for (int j = i + 1; j < selected.size(); j++) {
                assertNotEquals(selected.get(i).getId(), selected.get(j).getId());
            }
        }
    }

    @Test
    void selectedDealersAreSortedByLocation() {
        DealerStore store = new DealerStore();
        store.load();

        ArrayList<Dealer> selected = store.selectFourUniqueSortedByLocation();
        assertEquals(4, selected.size());

        for (int i = 0; i < selected.size() - 1; i++) {
            String left = selected.get(i).getLocation();
            String right = selected.get(i + 1).getLocation();
            assertTrue(left.compareToIgnoreCase(right) <= 0);
        }
    }
}
