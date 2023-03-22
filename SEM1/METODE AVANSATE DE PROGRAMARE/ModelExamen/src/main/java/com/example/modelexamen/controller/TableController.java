package com.example.modelexamen.controller;

import com.example.modelexamen.HelloApplication;
import com.example.modelexamen.domain.MenuItem;
import com.example.modelexamen.domainDTO.MenuItemDTO;
import com.example.modelexamen.service.Service;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TableController {
    Service srv;
    ObservableList<MenuItemDTO> model = FXCollections.observableArrayList();
    @FXML
    TableView table;
    @FXML
    TableColumn categoryColumn;
    @FXML
    TableColumn itemColumn;
    @FXML
    TableColumn quantityColumn;
    @FXML
    TableColumn priceColumn;
    @FXML
    TextField textField;

    Set<MenuItemDTO> getData() {
        Set<MenuItemDTO> list = new HashSet<>();
        for (MenuItem obj : srv.getAllMenuItems()) {
            {
                list.add(new MenuItemDTO(obj.getCategory(), obj.getItem(), obj.getPrice() + " " + obj.getCurrency(),0));
            }
        }
        return list;
    }

    private void initModel() {
        Set<MenuItemDTO> list= getData();
        List<MenuItemDTO> list1 = StreamSupport.stream(list.spliterator(), false).collect(Collectors.toList());
        this.model.setAll(list1);
    }

    @FXML
    public void initialize() {
        this.srv=new Service();
        this.categoryColumn.setCellValueFactory(new PropertyValueFactory<MenuItemDTO, String>("category"));
        this.itemColumn.setCellValueFactory(new PropertyValueFactory<MenuItemDTO, String>("item"));
        this.priceColumn.setCellValueFactory(new PropertyValueFactory<MenuItemDTO, String>("price"));
        this.quantityColumn.setCellValueFactory(new PropertyValueFactory<MenuItemDTO, Integer>("quantity"));

        initModel();
        this.table.setItems(this.model);
    }

    @FXML
    public void addButtonClicked() throws IOException {
        MenuItemDTO o = (MenuItemDTO) table.getSelectionModel().getSelectedItem();
        if (textField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Failed.");
            alert.setContentText("Invalid quantity!");
            alert.show();
            return;
        }
        //
    }
}
