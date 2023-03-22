module com.example.modelexamen {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.modelexamen to javafx.fxml;
    exports com.example.modelexamen;

    opens com.example.modelexamen.domain to javafx.fxml;
    exports com.example.modelexamen.domain;

    opens com.example.modelexamen.domainDTO to javafx.fxml;
    exports com.example.modelexamen.domainDTO;

    opens com.example.modelexamen.controller to javafx.fxml;
    exports com.example.modelexamen.controller;
}