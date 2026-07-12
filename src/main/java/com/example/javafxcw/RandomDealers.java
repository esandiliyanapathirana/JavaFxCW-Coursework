package com.example.javafxcw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RandomDealers {

    private static final String dealersFile = "dealers_cleaned.txt";

    public static String[] selectDealers() {
        String [] allDealers = new String[100];
        int totalCount = 0;

        try(BufferedReader reader = new BufferedReader(new FileReader(dealersFile))){

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    allDealers[totalCount] = line.trim();
                    totalCount++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the text file." + e.getMessage());
            return new String[0];
        }

        if (totalCount < 4 ) {
            System.out.println("Not enough dealers in the text file.");
            return  new String[0];
        }

        String[] selectedDealers = new String[4];
        int selectedCount = 0;

        while(selectedCount < 4) {
            int randomNumber = (int) (Math.random() * totalCount);
            String candidate = allDealers[randomNumber];

            boolean isDuplicate = false;
            for (int i = 0; i < selectedCount; i++) {
                if (selectedDealers[i].equals(candidate)) {
                    isDuplicate = true;
                    break;
                }
            }

            if (!isDuplicate) {
                selectedDealers[selectedCount] = candidate;
                selectedCount++;
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3 - i; j++) {

                String firstLocation = selectedDealers[j].split("\\|")[3];
                String nextLocation = selectedDealers[ j + 1 ].split("\\|")[3];
                if (firstLocation.compareTo(nextLocation) > 0 ) {
                    String savedDealer = selectedDealers[j];
                    selectedDealers[j] = selectedDealers [j+1];
                    selectedDealers[j+1] = savedDealer;
                }
            }
        }
        return selectedDealers;

    }
}
