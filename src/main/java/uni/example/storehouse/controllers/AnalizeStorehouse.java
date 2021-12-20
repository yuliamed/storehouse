package uni.example.storehouse.controllers;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uni.example.storehouse.entities.Storehouse;
import uni.example.storehouse.rmi.Connector;

public class AnalizeStorehouse {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox vbPersonalInfo;

    @FXML
    private PieChart pieStore;

    @FXML
    private Button btExit;

    @FXML
    void onExit(ActionEvent event) {
        Stage stage1 = (Stage) btExit.getScene().getWindow();
        stage1.close();
    }

    @FXML
    void initialize() {
        assert vbPersonalInfo != null : "fx:id=\"vbPersonalInfo\" was not injected: check your FXML file 'analizeStorehouse.fxml'.";
        assert pieStore != null : "fx:id=\"pieStore\" was not injected: check your FXML file 'analizeStorehouse.fxml'.";
        assert btExit != null : "fx:id=\"btExit\" was not injected: check your FXML file 'analizeStorehouse.fxml'.";

    }

    Storehouse selectedStorehouse = null;

    public void initData(Storehouse selectedStorehouse) {
        this.selectedStorehouse = selectedStorehouse;
        System.out.println("выбранный склад для анализу " + selectedStorehouse.getId());
        PieChart.Data data[] = new PieChart.Data[2];

        // строковые и целочисленные данные

        String status[] = null;//{"Занято", "Свободно" };

        int values[] = null;
        try {
            Connector connector = new Connector();
            int filledCount = connector.getCountFiledCellsInStorehouse(selectedStorehouse);
            values = new int[]{filledCount, selectedStorehouse.getMaxRows() * selectedStorehouse.getMaxCells() - filledCount};
            status = new String[]{"Занято - " + filledCount, "Свободно - " + (selectedStorehouse.getMaxRows() * selectedStorehouse.getMaxCells() - filledCount)};
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 2; i++) {

            data[i] = new PieChart.Data(status[i], values[i]);

        }
        ObservableList<PieChart.Data> observableList = FXCollections.observableArrayList(data);
        // создаем круговую диаграмму
        pieStore.setData(observableList);
    }
    Connector connector = null;
    public void onWrite(ActionEvent actionEvent) {
        try {
            connector = new Connector();
            connector.writeFileWithAnalize(selectedStorehouse);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Запись");
            alert.setHeaderText("Запись отчёта успешно прошла");
            alert.setContentText("Радуйся)");
            alert.showAndWait();
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
