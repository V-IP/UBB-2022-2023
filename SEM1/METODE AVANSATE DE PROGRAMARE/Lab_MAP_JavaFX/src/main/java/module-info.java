module com.example.lab_map_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.lab_map_javafx to javafx.fxml;
    exports com.example.lab_map_javafx;

    opens com.example.lab_map_javafx.controller to javafx.fxml;
    exports com.example.lab_map_javafx.controller;

    opens com.example.lab_map_javafx.domain to javafx.fxml;
    exports com.example.lab_map_javafx.domain;

    opens com.example.lab_map_javafx.domainDTO to javafx.fxml;
    exports com.example.lab_map_javafx.domainDTO;
}