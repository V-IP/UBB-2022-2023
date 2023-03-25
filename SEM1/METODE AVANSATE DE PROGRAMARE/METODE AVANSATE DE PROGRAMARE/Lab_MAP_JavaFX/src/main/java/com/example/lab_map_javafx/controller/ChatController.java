package com.example.lab_map_javafx.controller;

import com.example.lab_map_javafx.MainApp;
import com.example.lab_map_javafx.domain.Message;
import com.example.lab_map_javafx.domain.User;
import com.example.lab_map_javafx.domainDTO.MessageDTO;
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
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ChatController {

    Service service;
    User user1;
    User user2;
    boolean newM = false;
    boolean editM = false;
    ObservableList<MessageDTO> model1 = FXCollections.observableArrayList();
    ObservableList<MessageDTO> model2 = FXCollections.observableArrayList();

    @FXML
    TableView tableViewInbox;
    @FXML
    TableColumn tableColumnInbox;
    @FXML
    TableView tableViewMessage;
    @FXML
    TableColumn tableColumnReceived;
    @FXML
    TableColumn tableColumnSent;
    @FXML
    Button newChatButton;
    @FXML
    Button editButton;
    @FXML
    Button deleteButton;
    @FXML
    Button closeButton;
    @FXML
    Button sendButton;
    @FXML
    TextField textField;

    private void init() {
        UserValidator userValidator = new UserValidator();
        FriendshipValidator friendshipValidator = new FriendshipValidator();
        UserDatabaseRepository userDatabaseRepository = new UserDatabaseRepository("jdbc:postgresql://localhost:5432/Network", "postgres", "postgres", userValidator);
        FriendshipDatabaseRepository friendshipDatabaseRepository = new FriendshipDatabaseRepository("jdbc:postgresql://localhost:5432/Network", "postgres", "postgres", friendshipValidator);
        MessageDatabaseRepository messageDatabaseRepository = new MessageDatabaseRepository("jdbc:postgresql://localhost:5432/Network", "postgres", "postgres");
        this.service = new Service(userDatabaseRepository, userValidator, friendshipDatabaseRepository, friendshipValidator, messageDatabaseRepository);
    }

    public void setService(User user1) {
        init();
        this.user1 = user1;
        this.user2 = null;
        initModel1();
    }

    public void setUser2(User user2) {
        this.user2 = user2;
        this.newM = true;
        tableViewInboxMouseClicked();
    }

    Set<MessageDTO> getInboxMessages() {
        Set<MessageDTO> messages = new HashSet<>();
        for (User user : service.getFriends(this.user1.getId())) {
            this.user2 = user;
            Set<MessageDTO> messageList = getMessages();
            List<MessageDTO> messageDTOList = messageList.stream().sorted(comparator).collect(Collectors.toList());
            if (messageDTOList.size() != 0)
                messages.add(new MessageDTO(user.getId(), user.getId(), messageDTOList.get(messageDTOList.size() - 1).getDate(), service.findOneUser(user.getId()).getFirstName() + " " + service.findOneUser(user.getId()).getLastName(), "", ""));
        }
        this.user2 = null;
        return messages;
    }

    Set<MessageDTO> getMessages() {
        Set<MessageDTO> messageDTO = new HashSet<>();
        for (Message message : service.findConversation(this.user1.getId(), this.user2.getId()))
            if (Objects.equals(message.getId().getFrom(), this.user1.getId()))
                messageDTO.add(new MessageDTO(message.getId().getFrom(), message.getId().getTo(), message.getId().getDate(), "", message.getInfo(), ""));
            else
                messageDTO.add(new MessageDTO(message.getId().getFrom(), message.getId().getTo(), message.getId().getDate(), "", "", message.getInfo()));
        return messageDTO;
    }

    @FXML
    public void tableViewMessageMouseClicked() {
        MessageDTO message = (MessageDTO) tableViewMessage.getSelectionModel().getSelectedItem();
        if (!Objects.equals(message.getSent(), "")) {
            editButton.setDisable(false);
            deleteButton.setDisable(false);
        }
    }

    Comparator<MessageDTO> comparator = Comparator.comparing(m -> m.getDate().toString());

    private void initModel1() {
        Set<MessageDTO> messages = getInboxMessages();
        List<MessageDTO> messagesList = messages.stream().sorted((comparator).reversed()).collect(Collectors.toList());
        this.model1.setAll(messagesList);
    }

    private void initModel2() {
        Set<MessageDTO> messages = getMessages();
        List<MessageDTO> messagesList = messages.stream().sorted(comparator).collect(Collectors.toList());
        this.model2.setAll(messagesList);
    }

    @FXML
    public void initialize() {
        this.tableColumnInbox.setCellValueFactory(new PropertyValueFactory<MessageDTO, String>("inbox"));
        this.tableViewInbox.setItems(this.model1);
    }

    @FXML
    public void tableViewInboxMouseClicked() {
        this.tableColumnReceived.setCellValueFactory(new PropertyValueFactory<MessageDTO, String>("received"));
        this.tableColumnSent.setCellValueFactory(new PropertyValueFactory<MessageDTO, String>("sent"));
        this.tableColumnSent.setStyle("-fx-alignment: CENTER-RIGHT;");
        if (!this.newM) {
            MessageDTO message = (MessageDTO) tableViewInbox.getSelectionModel().getSelectedItem();
            this.user2 = service.findOneUser(message.getFrom());
            if (this.user1.getId() == this.user2.getId()) this.user2 = service.findOneUser(message.getTo());
        }
        initModel2();
        this.tableViewMessage.setItems(this.model2);
        textField.setDisable(false);
    }

    @FXML
    public void newChatButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("messageView.fxml"));
        AnchorPane root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Message");
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);

        MessageController ctrl = loader.getController();
        User user = service.findUserByUsername(this.user1.getUsername());
        ctrl.setService(user);

        dialogStage.show();
        Stage thisStage = (Stage) closeButton.getScene().getWindow();
        thisStage.close();
    }


    @FXML
    public void editButtonClicked() throws IOException {
        MessageDTO message = (MessageDTO) tableViewMessage.getSelectionModel().getSelectedItem();
        textField.setText(message.getSent());
        this.editM = true;
    }

    @FXML
    public void deleteButtonClicked() {
        MessageDTO message = (MessageDTO) tableViewMessage.getSelectionModel().getSelectedItem();
        service.removeMessage(user1.getId(), message.getTo(), message.getDate());
        editButton.setDisable(true);
        deleteButton.setDisable(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Remove");
        alert.setContentText("Conversation removed successfully.");
        alert.showAndWait();
        initModel2();
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
        User user = service.findUserByUsername(this.user1.getUsername());
        ctrl.setService(user);
        ctrl.welcomeUser(user.getFirstName() + " " + user.getLastName());

        dialogStage.show();
        Stage thisStage = (Stage) closeButton.getScene().getWindow();
        thisStage.close();
    }

    @FXML
    public void textFieldWritten() {
        sendButton.setDisable(false);
    }

    @FXML
    public void sendButtonClicked() throws IOException {
        if (textField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Failed.");
            alert.setContentText("Invalid message!");
            alert.show();
            return;
        }
        if (editM) {
            MessageDTO messageDTO = (MessageDTO) tableViewMessage.getSelectionModel().getSelectedItem();
            Message message = service.findOneMessage(this.user1.getId(), this.user2.getId(), messageDTO.getDate());
            service.updateMessage(this.user1.getId(), this.user2.getId(), message.getId().getDate(), textField.getText());
            this.editM = false;
            initModel2();
            textField.clear();
            return;
        }
        service.addMessage(this.user1.getId(), this.user2.getId(), LocalDateTime.now(), textField.getText());
        sendButton.setDisable(true);
        textField.clear();
        initModel1();
        initModel2();
    }
}