package uni.example.storehouse.controllers;

import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class userWorkingPressController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox vbPersonalInfo;

    @FXML
    private BarChart<?, ?> bchUsers;

    @FXML
    private Button btExit;

    @FXML
    void onExit(ActionEvent event) {

    }
    @FXML
    void initialize() {
        assert vbPersonalInfo != null : "fx:id=\"vbPersonalInfo\" was not injected: check your FXML file 'userWorkingPress.fxml'.";
        assert bchUsers != null : "fx:id=\"bchUsers\" was not injected: check your FXML file 'userWorkingPress.fxml'.";
        assert btExit != null : "fx:id=\"btExit\" was not injected: check your FXML file 'userWorkingPress.fxml'.";

    }
}
