package com.example.javafxcw;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;

public class SearchController {
    @FXML private TextField categoryField, minPriceField, maxPriceField, keywordField;
    @FXML private TableView<Part> resultTable;

    @FXML
    public void initialize() {
        TableColumn<Part, String> codeCol = new TableColumn<>("Code");
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        TableColumn<Part, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Part, String> brandCol = new TableColumn<>("Brand");
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        TableColumn<Part, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Part, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        resultTable.getColumns().addAll(codeCol, nameCol, brandCol, priceCol, categoryCol);
    }

    @FXML
    void searchParts() {
        double minPrice = -1, maxPrice = -1;
        try {
            if (!minPriceField.getText().trim().isEmpty())
                minPrice = Double.parseDouble(minPriceField.getText().trim());
            if (!maxPriceField.getText().trim().isEmpty())
                maxPrice = Double.parseDouble(maxPriceField.getText().trim());
        } catch (Exception e) { return; }

        AppData.inventoryStore.load();
        ArrayList<Part> found = AppData.inventoryStore.search(
                categoryField.getText(), minPrice, maxPrice, keywordField.getText());
        resultTable.setItems(FXCollections.observableArrayList(found));
    }

    @FXML
    void goBackToMenu(ActionEvent event) {
        SceneNavigator.goTo(event, "MenuView.fxml");
    }
}
