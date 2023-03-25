package com.example.lab_map_javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("loginView.fxml"));
        AnchorPane root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Login");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

