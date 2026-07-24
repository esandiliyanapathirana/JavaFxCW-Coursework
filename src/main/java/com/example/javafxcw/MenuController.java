package com.example.javafxcw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MenuController {

    @FXML
    void openCleanScreen(ActionEvent event) {
        SceneNavigator.goTo(event, "LegacyDataCleanerView.fxml");
    }

    @FXML
    void openInventoryScreen(ActionEvent event) {
        SceneNavigator.goTo(event, "InventoryView.fxml");
    }

    @FXML
    void openManagePartsScreen(ActionEvent event) {
        SceneNavigator.goTo(event, "ManagePartsView.fxml");
    }

    @FXML
    void openSearchScreen(ActionEvent event) {
        SceneNavigator.goTo(event, "SearchView.fxml");
    }

    @FXML
    void openDealersScreen(ActionEvent event) {
        SceneNavigator.goTo(event, "DealersView.fxml");
    }

    @FXML
    void openCartScreen(ActionEvent event) {
        SceneNavigator.goTo(event, "CartView.fxml");
    }
}