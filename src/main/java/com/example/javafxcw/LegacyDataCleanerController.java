package com.example.javafxcw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LegacyDataCleanerController {

    @FXML
    private Label statusLabel;

    @FXML
    void cleanDealers(ActionEvent event) {
        LegacyDataCleaner.dealersClean();
        AppData.dealerStore.load();
        statusLabel.setText("Dealers text file cleaned.");
    }

    @FXML
    void cleanInventory(ActionEvent event) {
        LegacyDataCleaner.inventoryClean();
        AppData.inventoryStore.load();
        statusLabel.setText("Inventory text file cleaned.");
    }

    @FXML
    void goBackToMenu(ActionEvent event) {
        SceneNavigator.goTo(event, "MenuView.fxml");
    }
}
