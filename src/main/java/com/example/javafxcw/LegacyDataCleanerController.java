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
        AppData.dealerStore.load();
        ClickButtons.setText("Dealers text file cleaned.");
    }

    @FXML
    void CleanInventory(ActionEvent event) {
        LegacyDataCleaner.inventoryClean();
        AppData.inventoryStore.load();
        ClickButtons.setText("Inventory text file cleaned.");
    }

    @FXML
    void goBackToMenu(ActionEvent event) {
        SceneNavigator.goTo(event, "MenuView.fxml");
    }
}
