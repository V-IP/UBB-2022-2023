package com.example.schelet.controller;

import com.example.schelet.HelloApplication;
import com.example.schelet.domain.Client;
import com.example.schelet.service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    Observable observable = null;

    Service service;
    @FXML
    TextField loginUsername;
    @FXML
    Button loginButton;

    public void init() {
        if (observable == null) this.observable = new Observable();
        this.service = new Service();
    }

    @FXML
    void loginButtonClicked() throws IOException {
        if (loginUsername.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Failed.");
            alert.setContentText("Invalid client name!");
            alert.show();
            return;
        }

        init();
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("flightView.fxml"));
        AnchorPane root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Flights!");
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);

        FlightController ctrl = loader.getController();
        Client client = service.findClientByUsername(loginUsername.getText());
        if (client == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Failed.");
            alert.setContentText("There is no account with this username!");
            alert.show();
            return;
        }
        ctrl.setService(client);
        observable.addObserver(ctrl);
        ctrl.setObservable(observable);
        ctrl.welcomeClient(client.getName());

        dialogStage.show();
    }
}