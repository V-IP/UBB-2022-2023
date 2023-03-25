package com.example.schelet.controller;

import com.example.schelet.domain.Client;
import com.example.schelet.domain.Flight;
import com.example.schelet.domain.Ticket;
import com.example.schelet.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FlightController extends Observer {

    Observable observable;
    Service srv;
    Client client;
    ObservableList<Flight> model = FXCollections.observableArrayList();
    @FXML
    TableView table;
    @FXML
    TableColumn fromColumn;
    @FXML
    TableColumn toColumn;
    @FXML
    TableColumn departureTimeColumn;
    @FXML
    TableColumn landingTimeColumn;
    @FXML
    TableColumn seatsColumn;
    @FXML
    ComboBox fromComboBox;
    @FXML
    ComboBox toComboBox;
    @FXML
    Button searchButton;
    @FXML
    Button buyButton;
    @FXML
    Label labelName;
    @FXML
    DatePicker datePicker;

    public void welcomeClient(String name) {
        this.labelName.setText("Hello " + name + "!");
    }

    public void setService(Client client) {
        this.srv = new Service();
        this.client = client;
        initModel();
        initFromComboBox();
        initToComboBox();
    }

    Set<Flight> getData() {
        Set<Flight> list = new HashSet<>();
        for (Flight o : srv.getAllFlights())
            list.add(o);
        return list;
    }

    private void initModel() {
        Set<Flight> list = getData();
        List<Flight> list1 = StreamSupport.stream(list.spliterator(), false).collect(Collectors.toList());
        this.model.setAll(list1);
    }

    private void initFromComboBox() {
        Set<Flight> list = new HashSet<>();
        for (Flight o : srv.getAllFlights())
            list.add(o);
        List<String> listStream = list.stream().map(x -> x.getFrom()).collect(Collectors.toList());
        List<String> comboBoxItems = new ArrayList<>();
        comboBoxItems.add("All");
        for (String s : listStream) {
            if (!comboBoxItems.contains(s)) comboBoxItems.add(s);
        }
        fromComboBox.getItems().setAll(comboBoxItems);
    }

    private void initToComboBox() {
        Set<Flight> list = new HashSet<>();
        for (Flight o : srv.getAllFlights())
            list.add(o);
        List<String> listStream = list.stream().map(x -> x.getTo()).collect(Collectors.toList());
        List<String> comboBoxItems = new ArrayList<>();
        comboBoxItems.add("All");
        for (String s : listStream) {
            if (!comboBoxItems.contains(s)) comboBoxItems.add(s);
        }
        toComboBox.getItems().setAll(comboBoxItems);
    }

    private void handleFilter() {
        Predicate<Flight> p1 = n -> fromComboBox.getSelectionModel().getSelectedItem() == null || Objects.equals(fromComboBox.getSelectionModel().getSelectedItem(), "all") || n.getFrom().equals(fromComboBox.getSelectionModel().getSelectedItem());
        Predicate<Flight> p2 = n -> toComboBox.getSelectionModel().getSelectedItem() == null || Objects.equals(toComboBox.getSelectionModel().getSelectedItem(), "all") || n.getTo().equals(toComboBox.getSelectionModel().getSelectedItem());
        model.setAll(getData().stream().filter(p1.and(p2)).collect(Collectors.toList()));
    }

    @FXML
    public void initialize() {
        this.fromColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("from"));
        this.toColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("to"));
        this.departureTimeColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("departureTime"));
        this.landingTimeColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("landingTime"));
        this.seatsColumn.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("seats"));
        this.table.setItems(this.model);
        fromComboBox.getSelectionModel().selectedItemProperty().addListener(x -> handleFilter());
        toComboBox.getSelectionModel().selectedItemProperty().addListener(x -> handleFilter());
    }

    @FXML
    public void tableViewMouseClicked() {
        buyButton.setDisable(false);
        Flight o = (Flight) table.getSelectionModel().getSelectedItem();
        if (o.getSeats() == 0) buyButton.setDisable(true);
    }

    @FXML
    public void searchButtonMouseClicked() {
        LocalDate date = datePicker.getValue();
        Set<Flight> list = new HashSet<>();
        if (date == null) {
            initModel();
            return;
        } else if (date.isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Failed.");
            alert.setContentText("You can't buy ticket from old flights!");
            alert.show();
            return;
        } else {
            for (Flight o : srv.getAllFlights()) {
                LocalDate localDate = o.getDepartureTime().toLocalDate();
                if (localDate.equals(date)) {
                    list.add(o);
                }
            }
            List<Flight> list1 = StreamSupport.stream(list.spliterator(), false).collect(Collectors.toList());
            this.model.setAll(list1);
            this.buyButton.setDisable(false);
        }
    }

    @FXML
    public void buyButtonMouseClicked() {
        Flight o = (Flight) table.getSelectionModel().getSelectedItem();
        if (o == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Failed.");
            alert.setContentText("Please select a flight from table!");
            alert.show();
            return;
        } else {
            srv.addOneTicket(new Ticket(client.getUsername(), LocalDateTime.now()));
            srv.updateFlight(o);
            observable.updateAll(o);
        }
    }

    @Override
    public void update(Flight flight) {
        if (this.model.contains(flight)) {
            this.model.remove(flight);
            this.model.add(srv.getOneFlight(flight.getId()));
        }
        this.buyButton.setDisable(false);
    }

    public void setObservable(Observable observable) {
        this.observable = observable;
    }
}
