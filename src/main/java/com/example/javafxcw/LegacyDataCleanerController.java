package com.example.javafxcw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LegacyDataCleanerController {

    @FXML
    private Label ClickButtons;

    @FXML
    void CleanDealers(ActionEvent event) {
        LegacyDataCleaner.dealersClean();
        ClickButtons.setText("Dealers text file cleaned.");
    }

    @FXML
    void CleanInventory(ActionEvent event) {
        LegacyDataCleaner.inventoryClean();
        ClickButtons.setText("Inventory text file cleaned.");
    }
}
