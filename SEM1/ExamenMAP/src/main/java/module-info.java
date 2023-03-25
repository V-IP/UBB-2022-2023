module com.example.schelet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.schelet to javafx.fxml;
    exports com.example.schelet;

    opens com.example.schelet.domain to javafx.fxml;
    exports com.example.schelet.domain;

    opens com.example.schelet.repository to javafx.fxml;
    exports com.example.schelet.repository;

    opens com.example.schelet.service to javafx.fxml;
    exports com.example.schelet.service;

    opens com.example.schelet.controller to javafx.fxml;
    exports com.example.schelet.controller;
}