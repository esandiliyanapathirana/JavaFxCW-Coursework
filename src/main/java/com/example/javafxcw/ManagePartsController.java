package com.example.javafxcw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ManagePartsController {
    @FXML private TextField codeField, nameField, brandField, priceField, qtyField;
    @FXML private TextField categoryField, dateField, imageField, thresholdField;
    @FXML private Label messageLabel;

    @FXML
    void addPart() {
        try {
            Part part = new Part(
                    codeField.getText().trim(), nameField.getText().trim(), brandField.getText().trim(),
                    Double.parseDouble(priceField.getText().trim()),
                    Integer.parseInt(qtyField.getText().trim()),
                    categoryField.getText().trim(), dateField.getText().trim(),
                    imageField.getText().trim(), Integer.parseInt(thresholdField.getText().trim()));
            messageLabel.setText(AppData.inventoryStore.addPart(part));
        } catch (Exception e) {
            messageLabel.setText("Error: Check your inputs.");
        }
    }

    @FXML
    void updatePart() {
        try {
            messageLabel.setText(AppData.inventoryStore.updatePart(
                    codeField.getText().trim(), nameField.getText().trim(), brandField.getText().trim(),
                    Double.parseDouble(priceField.getText().trim()),
                    Integer.parseInt(qtyField.getText().trim()),
                    categoryField.getText().trim(), dateField.getText().trim(),
                    imageField.getText().trim(), Integer.parseInt(thresholdField.getText().trim())));
        } catch (Exception e) {
            messageLabel.setText("Error: Check your inputs.");
        }
    }

    @FXML
    void deletePart() {
        messageLabel.setText(AppData.inventoryStore.deletePart(codeField.getText().trim()));
    }

    @FXML
    void goBackToMenu(ActionEvent event) {
        SceneNavigator.goTo(event, "MenuView.fxml");
    }
}