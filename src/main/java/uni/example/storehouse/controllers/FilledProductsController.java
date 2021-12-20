package uni.example.storehouse.controllers;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uni.example.storehouse.entities.ProductPlacement;
import uni.example.storehouse.entities.User;
import uni.example.storehouse.rmi.Connector;

public class FilledProductsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox vbUsers;

    @FXML
    private TextField tfStorehouse;

    @FXML
    private TableColumn<ProductPlacement, Integer> tcPlace;

    @FXML
    private TextField tfProductId;

    @FXML
    private TextField tfProductName;

    @FXML
    private TableColumn<ProductPlacement, Integer> tcRack;

    @FXML
    private TableView<ProductPlacement> table;

    @FXML
    private Button btExit;


    @FXML
    void initialize() {
        assert vbUsers != null : "fx:id=\"vbUsers\" was not injected: check your FXML file 'filledProducts.fxml'.";
        assert tfStorehouse != null : "fx:id=\"tfStorehouse\" was not injected: check your FXML file 'filledProducts.fxml'.";
        assert tcPlace != null : "fx:id=\"tcPlace\" was not injected: check your FXML file 'filledProducts.fxml'.";
        assert tfProductId != null : "fx:id=\"tfProductId\" was not injected: check your FXML file 'filledProducts.fxml'.";
        assert tfProductName != null : "fx:id=\"tfProductName\" was not injected: check your FXML file 'filledProducts.fxml'.";
        assert tcRack != null : "fx:id=\"tcRack\" was not injected: check your FXML file 'filledProducts.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'filledProducts.fxml'.";
        assert btExit != null : "fx:id=\"btExit\" was not injected: check your FXML file 'filledProducts.fxml'.";

    }
    public void initData( ArrayList<ProductPlacement>arr){
        this.arr = arr;
        initFields();
    }
    ArrayList<ProductPlacement>arr = null;
    private void initFields() {
        tfProductId.setText(String.valueOf(arr.get(0).getProduct().getUniqueCode()));
        tfProductName.setText(arr.get(0).getProduct().getName());
        tfStorehouse.setText(arr.get(0).getStorehouse().getName());
        tcPlace.setCellValueFactory(new PropertyValueFactory<>("place"));
        tcRack.setCellValueFactory(new PropertyValueFactory<>("rack"));
        ObservableList<ProductPlacement> spesialArr = FXCollections.observableArrayList(arr);
        table.setItems(spesialArr);
    }

    public void onExit(ActionEvent actionEvent) {
        Stage stage1 = (Stage) btExit.getScene().getWindow();
        stage1.close();
    }

    public void onPrint(ActionEvent actionEvent) {
        try {
            Connector connector = new Connector();
            connector.writeFileWithProducts(arr);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}

