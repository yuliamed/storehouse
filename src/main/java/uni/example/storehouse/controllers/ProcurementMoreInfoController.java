package uni.example.storehouse.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uni.example.storehouse.entities.*;
import uni.example.storehouse.rmi.Connector;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ProcurementMoreInfoController {

    @FXML
    private VBox vbUsers;

    @FXML
    private TableColumn<ProcurementProduct, String> tcIdProduct;

    @FXML
    private TableColumn<ProcurementProduct, Boolean> tcStatus;


    @FXML
    private TableColumn<ProcurementProduct, Integer> tcCount;


    @FXML
    private Button btFillIn;

    @FXML
    private TextField tfProcurement;

    @FXML
    private TextField tfProvider;


    @FXML
    private TextField tfSelectedProduct;

    @FXML
    private Button btExit;


    @FXML
    private TableView<ProcurementProduct> table;

    @FXML
    void initialize() {
        assert vbUsers != null : "fx:id=\"vbUsers\" was not injected: check your FXML file 'procurementMoreInfo.fxml'.";
        assert tcIdProduct != null : "fx:id=\"tcIdProduct\" was not injected: check your FXML file 'procurementMoreInfo.fxml'.";
        assert tcStatus != null : "fx:id=\"tcStatus\" was not injected: check your FXML file 'procurementMoreInfo.fxml'.";
        assert tcCount != null : "fx:id=\"tcCount\" was not injected: check your FXML file 'procurementMoreInfo.fxml'.";
        assert btFillIn != null : "fx:id=\"btFillIn\" was not injected: check your FXML file 'procurementMoreInfo.fxml'.";
        assert tfProcurement != null : "fx:id=\"tfProcurement\" was not injected: check your FXML file 'procurementMoreInfo.fxml'.";
        assert tfProvider != null : "fx:id=\"tfProvider\" was not injected: check your FXML file 'procurementMoreInfo.fxml'.";
        assert tfSelectedProduct != null : "fx:id=\"tfSelectedProduct\" was not injected: check your FXML file 'procurementMoreInfo.fxml'.";
        assert btExit != null : "fx:id=\"btExit\" was not injected: check your FXML file 'procurementMoreInfo.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'procurementMoreInfo.fxml'.";
        btExit.setOnAction(actionEvent -> {
            Stage stage1 = (Stage) btExit.getScene().getWindow();
            stage1.close();
        });
    }

    Connector connector;
    Procurement procurement = null;
    ArrayList<ProcurementProduct> products = null;
    ProcurementProduct selected;

    public void initData(Procurement procurement) {
        this.procurement = procurement;
        try {
            connector = new Connector();
            products = connector.getProcurementProductByProcur(procurement);
            fillInValues();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        tfProcurement.setText(String.valueOf(procurement.getIdProcurement()));
        tfProvider.setText(procurement.getProviderStr());
        if (procurement.getStatus())
            btFillIn.setDisable(true);
    }


    private void fillInValues() {
        ObservableList<ProcurementProduct> spesialArr = null;

        spesialArr = FXCollections.observableArrayList(products);


        tcIdProduct.setCellValueFactory(new PropertyValueFactory<>("strProduct"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tcCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        table.setItems(spesialArr);

        TableView.TableViewSelectionModel<ProcurementProduct> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<ProcurementProduct>() {
            @Override
            public void changed(ObservableValue<? extends ProcurementProduct> observableValue, ProcurementProduct animal, ProcurementProduct t1) {
                if (t1 != null) {
                    System.out.println("Выбранный продукт: " + t1.getProduct());
                    selected = t1;
                    tfSelectedProduct.setText(String.valueOf(t1.getStrProduct()));
                } else System.out.println("НЕ ВЫБРАН продукт!");
            }
        });
    }

    public void onFill(ActionEvent actionEvent) {
        if(!checkProcurementProduct()){
            return;
        }
        ArrayList<ProductPlacement> productPlacements = null;
        try {
            productPlacements = connector.filInProcurementProduct(selected);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/uni/example/storehouse/filledProducts.fxml"));
        Stage stage = new Stage();
        Parent root;
        stage.setTitle("Просмотр информации о размещеии товара");
        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            stage.setScene((new Scene((Pane) loader.load())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        FilledProductsController editController = loader.getController();
        editController.initData(productPlacements);
        stage.showAndWait();
        initData(procurement);
    }

    private void showErrorAlert(String sms1, String sms2) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(sms1);
        alert.setContentText(sms2);
        alert.showAndWait();
    }

    private boolean checkProcurementProduct() {
        try {
            selected.getStatus();
        } catch (Exception e){
            showErrorAlert("Ошибка размещения", "Нельзя разместить пустоту");
            return false;
        }
        if(selected.getStatus())
        {
            showErrorAlert("Ошибка размещения", "Даный продукт уже размещён на складе");
            return false;
        }
        return true;
    }

    public void onWatchInfo(ActionEvent actionEvent) {
        try{
            selected.getProcurement();
        } catch (Exception e ){
            showErrorAlert("Ошибка просмотра информации","Невозможно просмотерть информацию без выбранного элемента для просмотра");
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/uni/example/storehouse/addProduct.fxml"));
        Stage stage = new Stage();
        Parent root;
        stage.setTitle("Просмотр информации о товаре");
        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            stage.setScene((new Scene((Pane) loader.load())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddProductController editController = loader.getController();
        Product p = selected.getProduct();
        editController.initData(p);
        stage.showAndWait();
    }

    public void onExit(ActionEvent actionEvent) {
        Stage stage1 = (Stage) btExit.getScene().getWindow();
        stage1.close();
    }
}
