package com.example.javafxcw;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneNavigator {

    public static void goTo(ActionEvent event, String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    SceneNavigator.class.getResource(fxmlFileName)
            );
            Parent root = loader.load();
            Scene newScene = new Scene(root);

            Stage currentStage = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException error) {
            System.err.println("Cannot open screen: " + fxmlFileName);
            error.printStackTrace();
        }
    }
}
