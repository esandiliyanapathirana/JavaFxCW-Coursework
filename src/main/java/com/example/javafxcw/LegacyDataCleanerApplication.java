package com.example.javafxcw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LegacyDataCleanerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                LegacyDataCleanerApplication.class.getResource("LegacyDataCleanerView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Legacy Data Cleaner");
        stage.setScene(scene);
        stage.show();
    }
}
