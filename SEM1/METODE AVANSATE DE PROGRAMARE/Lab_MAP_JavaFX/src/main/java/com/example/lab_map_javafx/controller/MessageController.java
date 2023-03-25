package com.example.lab_map_javafx.controller;

import com.example.lab_map_javafx.MainApp;
import com.example.lab_map_javafx.domain.Friendship;
import com.example.lab_map_javafx.domain.Message;
import com.example.lab_map_javafx.domain.User;
import com.example.lab_map_javafx.domainDTO.MessageDTO;
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
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MessageController {

    Service service;
    User user;
    ObservableList<UserDTO> model = FXCollections.observableArrayList();
    @FXML
    TableView tableViewName;
    @FXML
    TableColumn tableColumnName;
    @FXML
    TableColumn tableColumnDate;
    @FXML
    TextField searchTextField;
    @FXML
    Button selectButton;
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

    Set<UserDTO> getFriends() {
        Set<UserDTO> friends = new HashSet<>();
        for (Friendship friendship : service.getAllFriendships()) {
            if (friendship.getId().getE1() == this.user.getId() && friendship.getStatus() == 1) {
                User user2 = service.findOneUser(friendship.getId().getE2());
                friends.add(new UserDTO(user2.getFirstName() + " " + user2.getLastName(), friendship.getStringFriendsFrom()));
            }
            if (friendship.getId().getE2() == this.user.getId() && friendship.getStatus() == 1) {
                User user1 = service.findOneUser(friendship.getId().getE1());
                friends.add(new UserDTO(user1.getFirstName() + " " + user1.getLastName(), friendship.getStringFriendsFrom()));
            }
        }
        return friends;
    }

    private void initModel() {
        Set<UserDTO> friends = getFriends();
        List<UserDTO> friendsList = StreamSupport.stream(friends.spliterator(), false).collect(Collectors.toList());
        this.model.setAll(friendsList);
    }

    @FXML
    public void initialize() {
        this.tableColumnName.setCellValueFactory(new PropertyValueFactory<UserDTO, String>("name"));
        this.tableColumnDate.setCellValueFactory(new PropertyValueFactory<UserDTO, String>("date"));
        this.tableViewName.setItems(this.model);

        searchTextField.textProperty().addListener(o -> handleFilter());
    }

    @FXML
    public void tableViewNameMouseClicked() {
        selectButton.setDisable(false);
    }

    private void handleFilter() {
        Predicate<UserDTO> p = n -> n.getName().startsWith(searchTextField.getText());
        model.setAll(getFriends().stream().filter(p).collect(Collectors.toList()));
    }

    @FXML
    public void selectButtonClicked() throws IOException {
        UserDTO userDTO = (UserDTO) tableViewName.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("chatView.fxml"));
        AnchorPane root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Chat");
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);

        ChatController ctrl = loader.getController();
        ctrl.setService(this.user);
        ctrl.setUser2(service.findUserByName(userDTO.getName().split(" ")[1], userDTO.getName().split(" ")[0]));

        dialogStage.show();
        Stage thisStage = (Stage) closeButton.getScene().getWindow();
        thisStage.close();
    }

    @FXML
    public void closeButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("chatView.fxml"));
        AnchorPane root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Chat");
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);

        ChatController ctrl = loader.getController();
        User user = service.findUserByUsername(this.user.getUsername());
        ctrl.setService(user);

        dialogStage.show();
        Stage thisStage = (Stage) closeButton.getScene().getWindow();
        thisStage.close();
    }
}
