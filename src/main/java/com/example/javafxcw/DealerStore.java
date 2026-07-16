package com.example.javafxcw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DealerStore {
    private static final String file_path = "dealers_cleaned.txt";
    private ArrayList<Dealer> dealers = new ArrayList<Dealer>();

    public void load() {
        dealers.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file_path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] data = line.split("\\|", -1);
                if (data.length < 4) {
                    continue;
                }
                dealers.add(new Dealer(
                        data[0].trim(),
                        data[1].trim(),
                        data[2].trim(),
                        data[3].trim()
                ));
            }
        } catch (IOException e) {
            System.err.println("Error loading dealers: " + e.getMessage());
        }
    }

    public ArrayList<Dealer> selectFourUniqueSortedByLocation() {
        ArrayList<Dealer> selected = new ArrayList<Dealer>();

        if (dealers.size() < 4) {
            return selected;
        }

        while (selected.size() < 4) {
            int randomIndex = (int) (Math.random() * dealers.size());
            Dealer candidate = dealers.get(randomIndex);

            boolean duplicate = false;
            for (int i = 0; i < selected.size(); i++) {
                if (selected.get(i).getId().equals(candidate.getId())) {
                    duplicate = true;
                    break;
                }
            }

            if (!duplicate) {
                selected.add(candidate);
            }
        }

        for (int i = 0; i < selected.size() - 1; i++) {
            for (int j = 0; j < selected.size() - 1 - i; j++) {
                String loc1 = selected.get(j).getLocation();
                String loc2 = selected.get(j + 1).getLocation();
                if (loc1.compareToIgnoreCase(loc2) > 0) {
                    Dealer temp = selected.get(j);
                    selected.set(j, selected.get(j + 1));
                    selected.set(j + 1, temp);
                }
            }
        }

        return selected;
    }
}
