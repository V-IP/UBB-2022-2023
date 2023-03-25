package com.example.schelet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("loginView.fxml"));
        AnchorPane root = loader.load();

        stage.setScene(new Scene(root));
        stage.setTitle("Login!");

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}