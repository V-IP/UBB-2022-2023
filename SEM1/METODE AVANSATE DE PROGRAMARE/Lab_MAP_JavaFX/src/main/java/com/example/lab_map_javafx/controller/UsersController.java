package com.example.lab_map_javafx.controller;

import com.example.lab_map_javafx.MainApp;
import com.example.lab_map_javafx.domain.Friendship;
import com.example.lab_map_javafx.domain.User;
import com.example.lab_map_javafx.domainDTO.UserDTO;
import com.example.lab_map_javafx.repository.FriendshipDatabaseRepository;
import com.example.lab_map_javafx.repository.MessageDatabaseRepository;
import com.example.lab_map_javafx.repository.UserDatabaseRepository;
import com.example.lab_map_javafx.service.Service;
import com.example.lab_map_javafx.validators.FriendshipValidator;
import com.example.lab_map_javafx.validators.UserValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UsersController {

    Service service;
    User user;
    ObservableList<UserDTO> model = FXCollections.observableArrayList();

    @FXML
    TableView tableViewName;
    @FXML
    TableColumn tableColumnName;
    @FXML
    Label welcomeLabel;
    @FXML
    Label searchLabel;
    @FXML
    TextField searchTextField;
    @FXML
    Button requestButton;
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
        initModel();
    }

    public void welcomeUser(String name) {
        this.welcomeLabel.setText("Hello " + name + "!");
    }

    Set<UserDTO> getUsers() {
        Set<UserDTO> users = new HashSet<>();
        for (User user2 : service.getAllUsers()) {
            if (service.findOneFriendship(this.user.getId(), user2.getId(), this.user.getId()) == null && !Objects.equals(this.user.getId(), user2.getId())) {
                users.add(new UserDTO(user2.getFirstName() + " " + user2.getLastName(), ""));
            }
        }
        return users;
    }

    private void handleFilter() {
        Predicate<UserDTO> p = n -> n.getName().startsWith(searchTextField.getText());
        model.setAll(getUsers().stream().filter(p).collect(Collectors.toList()));
    }

    private void initModel() {
        Set<UserDTO> users = getUsers();
        List<UserDTO> usersList = StreamSupport.stream(users.spliterator(), false).collect(Collectors.toList());
        this.model.setAll(usersList);
    }

    @FXML
    public void initialize() {
        this.tableColumnName.setCellValueFactory(new PropertyValueFactory<UserDTO, String>("name"));
        this.tableViewName.setItems(this.model);

        searchTextField.textProperty().addListener(o -> handleFilter());
    }

    @FXML
    public void tableViewNameMouseClicked() {
        requestButton.setDisable(false);
    }

    @FXML
    public void requestButtonClicked() {
        UserDTO userDTO = (UserDTO) tableViewName.getSelectionModel().getSelectedItem();
        model.remove(userDTO);
        User user2 = service.findUserByName(userDTO.getName().split(" ")[1], userDTO.getName().split(" ")[0]);
        service.addFriendship(this.user.getId(), user2.getId(), this.user.getId());

        requestButton.setDisable(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Add");
        alert.setContentText("Request sent.");
        alert.showAndWait();
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
