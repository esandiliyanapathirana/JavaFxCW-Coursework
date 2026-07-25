package com.example.javafxcw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DepotApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        AppData.loadAll();

        FXMLLoader loader = new FXMLLoader(
                DepotApplication.class.getResource("MenuView.fxml")
        );
        Scene scene = new Scene(loader.load());
        stage.setTitle("Malabe Spares Depot");
        stage.setScene(scene);
        stage.show();
    }
}