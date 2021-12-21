package uni.example.storehouse.controllers;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uni.example.storehouse.entities.Storehouse;
import uni.example.storehouse.rmi.Connector;

public class AddStorehouseController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfCountRack;

    @FXML
    private VBox vbPersonalInfo;

    @FXML
    private Button btSave;

    @FXML
    private TextField tfAdress;

    @FXML
    private TextField tfCountPlaces;

    @FXML
    private Button btExit;


    @FXML
    void onExit(ActionEvent event) {
        Stage stage1 = (Stage) btExit.getScene().getWindow();
        stage1.close();
    }


    @FXML
    void onSave(ActionEvent event) {
        if (checkFields()) {
            try {
                Connector connector = new Connector();
                if (editableStorehouse != null) {
                    editableStorehouse.setName(tfAdress.getText());
                    editableStorehouse.setMaxCells(Integer.parseInt(tfCountPlaces.getText()));
                    editableStorehouse.setMaxRows(Integer.parseInt(tfCountRack.getText()));
                    connector.updateStorehouse(editableStorehouse);
                    this.onExit(event);
                } else {
                    Storehouse storehouse = new Storehouse();
                    storehouse.setName(tfAdress.getText());
                    storehouse.setMaxCells(Integer.parseInt(tfCountPlaces.getText()));
                    storehouse.setMaxRows(Integer.parseInt(tfCountRack.getText()));
                    connector.addStorehouse(storehouse);
                    this.onExit(event);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        }
        return;

    }

    private boolean checkFields() {
        if (tfAdress.getText().length() == 0) {
            showErrorAlert("Неверное заполнение полей", "Введите адресс");
            return false;
        }
        try {
            Integer.parseInt(tfCountPlaces.getText());
        } catch (NumberFormatException e) {
            showErrorAlert("Неверное заполнение полей", "Введите корректное количество мест");
            return false;
        }
        try {
            Integer.parseInt(tfCountRack.getText());
        } catch (NumberFormatException e) {
            showErrorAlert("Неверное заполнение полей", "Введите корректное количество рядов");
            return false;
        }
        return true;
    }

    private void showErrorAlert(String str1, String str2) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(str1);
        alert.setContentText(str2);
        alert.showAndWait();
    }


    @FXML
    void initialize() {
        assert tfCountRack != null : "fx:id=\"tfCountRack\" was not injected: check your FXML file 'addStorehouse.fxml'.";
        assert vbPersonalInfo != null : "fx:id=\"vbPersonalInfo\" was not injected: check your FXML file 'addStorehouse.fxml'.";
        assert btSave != null : "fx:id=\"btSave\" was not injected: check your FXML file 'addStorehouse.fxml'.";
        assert tfAdress != null : "fx:id=\"tfAdress\" was not injected: check your FXML file 'addStorehouse.fxml'.";
        assert tfCountPlaces != null : "fx:id=\"tfCountPlaces\" was not injected: check your FXML file 'addStorehouse.fxml'.";
        assert btExit != null : "fx:id=\"btExit\" was not injected: check your FXML file 'addStorehouse.fxml'.";

    }

    Storehouse editableStorehouse = null;

    public void initData(Storehouse storehouse) {
        editableStorehouse = storehouse;
        if (storehouse != null) {
            tfAdress.setText(storehouse.getName());
            tfCountPlaces.setText(String.valueOf(storehouse.getMaxCells()));
            tfCountRack.setText(String.valueOf(storehouse.getMaxRows()));
        }
    }
}
