package com.example.javafxcw;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import java.util.ArrayList;

public class DealersController {
    @FXML private ListView<String> dealerList;

    @FXML
    void selectDealers() {
        AppData.dealerStore.load();
        ArrayList<Dealer> selected = AppData.dealerStore.selectFourUniqueSortedByLocation();
        ArrayList<String> lines = new ArrayList<>();
        for (int i = 0; i < selected.size(); i++) {
            lines.add(selected.get(i).toDisplay());
        }
        dealerList.setItems(FXCollections.observableArrayList(lines));
    }

    @FXML
    void goBackToMenu(ActionEvent event) {
        SceneNavigator.goTo(event, "MenuView.fxml");
    }
}