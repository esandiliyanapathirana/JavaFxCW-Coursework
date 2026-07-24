package com.example.javafxcw;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.ArrayList;
import java.util.List;

public class CartController {
    @FXML private TextField codeField, qtyField;
    @FXML private ListView<String> cartList;
    @FXML private Label totalLabel, messageLabel;

    @FXML
    public void initialize() { showCart(); }

    @FXML
    void addToCart() {
        try {
            AppData.inventoryStore.load();
            Part part = AppData.inventoryStore.findByCode(codeField.getText().trim());
            int quantityWanted = Integer.parseInt(qtyField.getText().trim());
            messageLabel.setText(AppData.cart.addToCart(part, quantityWanted));
            showCart();
        } catch (Exception e) {
            messageLabel.setText("Error: Invalid cart input.");
        }
    }

    @FXML
    void checkout() {
        messageLabel.setText(AppData.cart.checkout(AppData.inventoryStore));
        showCart();
    }

    @FXML
    void clearCart() {
        AppData.cart.clear();
        showCart();
        messageLabel.setText("Cart cleared.");
    }

    private void showCart() {
        List<Cart.CartItem> items = AppData.cart.getItems();
        ArrayList<String> lines = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            Cart.CartItem item = items.get(i);
            Part p = item.getPart();
            lines.add(p.getCode() + " x " + item.getQuantity()
                    + " | " + p.getCategory() + " | Rs." + p.getPrice());
        }
        cartList.setItems(FXCollections.observableArrayList(lines));
        totalLabel.setText("Total after discounts: Rs. "
                + String.format("%.2f", AppData.cart.calculateFinalTotal()));
    }

    @FXML
    void goBackToMenu(ActionEvent event) {
        SceneNavigator.goTo(event, "MenuView.fxml");
    }
}