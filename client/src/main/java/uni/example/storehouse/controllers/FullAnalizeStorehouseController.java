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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uni.example.storehouse.entities.Storehouse;
import uni.example.storehouse.rmi.Connector;

public class FullAnalizeStorehouseController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfStorehousePrice;

    @FXML
    private PieChart pieStoreStorePrice;

    @FXML
    private TextField tfFilledCells;

    @FXML
    private TextField tfFreeCells;

    @FXML
    private Button btExit;

    @FXML
    private PieChart pieStoreFilledCells;

    @FXML
    void onExit(ActionEvent event) {
        Stage stage1 = (Stage) btExit.getScene().getWindow();
        stage1.close();
    }

    Connector connector = null;
    int count = 0;
    ArrayList<Storehouse> storehouses = null;
    @FXML
    void initialize() {
        assert tfStorehousePrice != null : "fx:id=\"tfStorehousePrice\" was not injected: check your FXML file 'fullAnalizeStorehouse.fxml'.";
        assert pieStoreStorePrice != null : "fx:id=\"pieStoreStorePrice\" was not injected: check your FXML file 'fullAnalizeStorehouse.fxml'.";
        assert tfFilledCells != null : "fx:id=\"tfFilledCells\" was not injected: check your FXML file 'fullAnalizeStorehouse.fxml'.";
        assert tfFreeCells != null : "fx:id=\"tfFreeCells\" was not injected: check your FXML file 'fullAnalizeStorehouse.fxml'.";
        assert btExit != null : "fx:id=\"btExit\" was not injected: check your FXML file 'fullAnalizeStorehouse.fxml'.";
        assert pieStoreFilledCells != null : "fx:id=\"pieStoreFilledCells\" was not injected: check your FXML file 'fullAnalizeStorehouse.fxml'.";
        try {
            connector = new Connector();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        try {
            storehouses = connector.readStorehouses();
            count = storehouses.size();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        initCellsPie();
        initPricePie();

    }

    private void initCellsPie() {

        PieChart.Data data[] = new PieChart.Data[count];

        // строковые и целочисленные данные

        String status[] = new String[count];//{"Занято", "Свободно" };

        int values[] = new int[count];
        int allCountFilled = 0;
        int allCount = 0;
        try {
            for (int i = 0; i < count; i++) {
                values[i] = connector.getCountFiledCellsInStorehouse(storehouses.get(i));
                allCountFilled+=values[i];
                status[i] = "Склад "+String.valueOf(storehouses.get(i).getId())+" - "+ values[i];
                allCount+=storehouses.get(i).getMaxCells()*storehouses.get(i).getMaxRows();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < count; i++) {
            data[i] = new PieChart.Data(status[i], values[i]);
        }
        ObservableList<PieChart.Data> observableList = FXCollections.observableArrayList(data);
        // создаем круговую диаграмму
        pieStoreFilledCells.setData(observableList);
        tfFilledCells.setText(String.valueOf(allCountFilled));
        tfFreeCells.setText(String.valueOf(allCount-allCountFilled));
    }


    private void initPricePie() {
        PieChart.Data data[] = new PieChart.Data[count];

        // строковые и целочисленные данные

        String status[] = new String[count];//{"Занято", "Свободно" };

        double values[] = new double[count];
        double fullPrice = 0;
        try {
            for (int i = 0; i < count; i++) {
                values[i] = connector.getPriceInStorehouse(storehouses.get(i));
                fullPrice+=values[i];
                status[i] = "Склад " + String.valueOf(storehouses.get(i).getId())+" - "+ values[i];
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < count; i++) {
            data[i] = new PieChart.Data(status[i], values[i]);
        }
        ObservableList<PieChart.Data> observableList = FXCollections.observableArrayList(data);
        // создаем круговую диаграмму
        pieStoreStorePrice.setData(observableList);
        tfStorehousePrice.setText(String.valueOf(fullPrice));
    }
}
