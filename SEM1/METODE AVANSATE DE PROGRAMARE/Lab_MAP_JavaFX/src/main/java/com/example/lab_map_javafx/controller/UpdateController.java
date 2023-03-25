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

public class UpdateController {

    Service service;
    User user;
    @FXML
    TextField updateUsername;
    @FXML
    TextField updateFirstName;
    @FXML
    TextField updateLastName;
    @FXML
    PasswordField updateOldPassword;
    @FXML
    PasswordField updateNewPassword;
    @FXML
    PasswordField updateConfirmNewPassword;
    @FXML
    Button updateNameButton;
    @FXML
    Button updatePasswordButton;
    @FXML
    Button closeButton;

    private void init() {
        UserValidator userValidator = new UserValidator();
        FriendshipValidator friendshipValidator = new FriendshipValidator();
        UserDatabaseRepository userDatabaseRepository = new UserDatabaseRepository("jdbc:postgresql://localhost:5432/Network", "postgres", "postgres", userValidator);
        FriendshipDatabaseRepository friendshipDatabaseRepository = new FriendshipDatabaseRepository("jdbc:postgresql://localhost:5432/Network", "postgres", "postgres", friendshipValidator);
        MessageDatabaseRepository messageDatabaseRepository = new MessageDatabaseRepository("jdbc:postgresql://localhost:5432/Network", "postgres", "postgres");
        this.service = new Service(userDatabaseRepository, userValidator, friendshipDatabaseRepository, friendshipValidator, messageDatabaseRepository);
    }

    public void setService(User user) {
        init();
        this.user = user;
    }

    private static byte[] getKey() {
        return new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
    }

    @FXML
    void updateNameButtonClicked() {
        String username, firstName, lastName, password = this.user.getPassword();
        if (updateUsername.getText().isEmpty() && updateFirstName.getText().isEmpty() && updateLastName.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Failed.");
            alert.setContentText("Invalid fields!");
            alert.show();
            return;
        }
        if (updateUsername.getText().isEmpty()) {
            username = this.user.getUsername();
        } else {
            username = updateUsername.getText();
            User user = service.findUserByUsername(updateUsername.getText());
            if (user != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!");
                alert.setHeaderText("Failed.");
                alert.setContentText("Username already used!");
                alert.show();
                return;
            }
        }
        if (updateFirstName.getText().isEmpty()) {
            firstName = this.user.getFirstName();
        } else firstName = updateFirstName.getText();
        if (updateLastName.getText().isEmpty()) {
            lastName = this.user.getLastName();
        } else lastName = updateLastName.getText();

        service.updateUser(user.getId(), lastName, firstName, username, password);
        this.user = service.findUserByName(lastName, firstName);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Update");
        alert.setContentText("User's name updated successfully.");
        alert.showAndWait();
    }

    @FXML
    void updatePasswordButtonClicked() throws IOException {
        if (updateOldPassword.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Failed.");
            alert.setContentText("Invalid old password!");
            alert.show();
            return;
        }
        if (updateNewPassword.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Failed.");
            alert.setContentText("Invalid new password!");
            alert.show();
            return;
        }
        if (updateConfirmNewPassword.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Failed.");
            alert.setContentText("Invalid new confirmed password!");
            alert.show();
            return;
        }
        if (!Objects.equals(updateNewPassword.getText(), updateConfirmNewPassword.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Failed.");
            alert.setContentText("Passwords are not matching!");
            alert.show();
            return;
        }

        User user = service.findUserByUsername(this.user.getUsername());
        //encrypting the password
        try {
            // Get the key to use for encrypting the data
            byte[] key = getKey();

            // Create a Cipher instance for encrypting the data
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            // Encrypt the data
            String originalOldString = updateOldPassword.getText();
            byte[] encryptedOldBytes = cipher.doFinal(originalOldString.getBytes());
            String originalNewString = updateNewPassword.getText();
            byte[] encryptedNewBytes = cipher.doFinal(originalNewString.getBytes());

            if (!Objects.equals(user.getPassword(), new String(encryptedOldBytes))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!");
                alert.setHeaderText("Failed.");
                alert.setContentText("Wrong password!");
                alert.show();
                return;
            }

            service.updateUser(user.getId(), user.getLastName(), user.getFirstName(), user.getUsername(), new String(encryptedNewBytes));
            this.user = service.findUserByName(user.getLastName(), user.getFirstName());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Update");
            alert.setContentText("Password updated successfully.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void closeButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("mainView.fxml"));
        AnchorPane root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Main");
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);

        MainController ctrl = loader.getController();
        User user = service.findUserByUsername(this.user.getUsername());
        ctrl.setService(user);
        ctrl.welcomeUser(user.getFirstName() + " " + user.getLastName());

        dialogStage.show();
        Stage thisStage = (Stage) closeButton.getScene().getWindow();
        thisStage.close();
    }
}
