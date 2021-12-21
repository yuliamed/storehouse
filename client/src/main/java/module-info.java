module uni.example.storehouse {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.naming;

    exports uni.example.storehouse.controllers;
    opens uni.example.storehouse.controllers to javafx.fxml;

    exports uni.example.storehouse.models;
    opens uni.example.storehouse.models to javafx.fxml;

    exports uni.example.storehouse.rmi;
    opens uni.example.storehouse.rmi to java.rmi;

    exports uni.example.storehouse.entities;
    opens uni.example.storehouse.entities to javafx.fxml;
}