package com.example.lab_map_javafx.controller;

import com.example.lab_map_javafx.MainApp;
import com.example.lab_map_javafx.domain.User;
import com.example.lab_map_javafx.repository.FriendshipDatabaseRepository;
import com.example.lab_map_javafx.repository.MessageDatabaseRepository;
import com.example.lab_map_javafx.repository.UserDatabaseRepository;
import com.example.lab_map_javafx.service.Service;
import com.example.lab_map_javafx.validators.FriendshipValidator;
import com.example.lab_map_javafx.validators.UserValidator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Objects;

public class RegisterController {

    Service service;
    @FXML
    TextField registerUsername;
    @FXML
    TextField registerFirstName;
    @FXML
    TextField registerLastName;
    @FXML
    PasswordField registerPassword;
    @FXML
    PasswordField registerConfirmPassword;
    @FXML
    Button registerButton;
    @FXML
    Hyperlink hyperlink;

    private void init() {
        UserValidator userValidator = new UserValidator();
        FriendshipValidator friendshipValidator = new FriendshipValidator();
        UserDatabaseRepository userDatabaseRepository = new UserDatabaseRepository("jdbc:postgresql://localhost:5432/Network", "postgres", "postgres", userValidator);
        FriendshipDatabaseRepository friendshipDatabaseRepository = new FriendshipDatabaseRepository("jdbc:postgresql://localhost:5432/Network", "postgres", "postgres", friendshipValidator);
        MessageDatabaseRepository messageDatabaseRepository = new MessageDatabaseRepository("jdbc:postgresql://localhost:5432/Network", "postgres", "postgres");
        this.service = new Service(userDatabaseRepository, userValidator, friendshipDatabaseRepository, friendshipValidator, messageDatabaseRepository);
    }

    @FXML
    void hiperlinkClicked() throws IOException {
        init();
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("loginView.fxml"));
        AnchorPane root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Login");
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
        Stage thisStage = (Stage) registerButton.getScene().getWindow();
        thisStage.close();
    }

    private static byte[] getKey() {
        return new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
    }

    @FXML
    void registerButtonClicked() throws IOException {
        if (registerUsername.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Failed.");
            alert.setContentText("Invalid username!");
            alert.show();
            return;
        }
        if (registerFirstName.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Failed.");
            alert.setContentText("Invalid first name!");
            alert.show();
            return;
        }
        if (registerLastName.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Failed.");
            alert.setContentText("Invalid last name!");
            alert.show();
            return;
        }
        if (registerPassword.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Failed.");
            alert.setContentText("Invalid password!");
            alert.show();
            return;
        }
        if (registerConfirmPassword.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Failed.");
            alert.setContentText("Invalid confirmed password!");
            alert.show();
            return;
        }
        if (!Objects.equals(registerPassword.getText(), registerConfirmPassword.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Failed.");
            alert.setContentText("Passwords are not matching!");
            alert.show();
            return;
        }

        init();
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("mainView.fxml"));
        AnchorPane root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Main");
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);

        MainController ctrl = loader.getController();
        User user = service.findUserByUsername(registerUsername.getText());
        if (user != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Failed.");
            alert.setContentText("Username already used!");
            alert.show();
            return;
        }
        //encrypting the password
        try {
            // Get the key to use for encrypting the data
            byte[] key = getKey();

            // Create a Cipher instance for encrypting the data
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            // Encrypt the data
            String originalString = registerPassword.getText();
            byte[] encryptedBytes = cipher.doFinal(originalString.getBytes());

            service.addUser(registerLastName.getText(), registerFirstName.getText(), registerUsername.getText(), new String(encryptedBytes));
            user = service.findUserByUsername(registerUsername.getText());
            ctrl.setService(user);
            ctrl.welcomeUser(user.getFirstName() + " " + user.getLastName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        dialogStage.show();
        Stage thisStage = (Stage) registerButton.getScene().getWindow();
        thisStage.close();
    }
}
