package uni.example.storehouse.controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uni.example.storehouse.entities.Procurement;
import uni.example.storehouse.entities.ProductPlacement;
import uni.example.storehouse.entities.User;
import uni.example.storehouse.rmi.Connector;

public class MenuManagerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<ProductPlacement, Integer> tcPlace;

    @FXML
    private Button btMoreInfoProcurement;

    @FXML
    private Button btnProfile;

    @FXML
    private TextField tfSurname;

    @FXML
    private Button btnFindingProduct;

    @FXML
    private Label nameItem;

    @FXML
    private TextField tfIdProduct;

    @FXML
    private TableView<Procurement> tableProcurements;

    @FXML
    private TableColumn<Procurement, String> tcProviderName;

    @FXML
    private TextField tfLogin;

    @FXML
    private VBox vbFindProduct;

    @FXML
    private Button btnMyProfile;

    @FXML
    private Button btnExit;

    @FXML
    private TableColumn<Procurement, Boolean> tcProcurStatus;

    @FXML
    private VBox vbPersonalInfo;

    @FXML
    private TableColumn<Procurement, Integer> tcProcurNumber;

    @FXML
    private TextField tfName;

    @FXML
    private Button btFindById;

    @FXML
    private TableColumn<ProductPlacement, Integer> tcRack;

    @FXML
    private TableView<ProductPlacement> tableProducts;

    @FXML
    private TextField tfSelectedProcurement;

    @FXML
    private VBox vbProcurement;

    @FXML
    private Button btnChangePass;

    @FXML
    private TextField tfPatro;

    @FXML
    private TextField tfCountProduct;


    @FXML
    private TextField tfNameProduct;

    @FXML
    private TextField tfRole;

    @FXML
    private Button btnProcurement;

    @FXML
    void handleClicks(ActionEvent event) {
        if (event.getSource() == btnMyProfile) {
            nameItem.setText("МОИ ДАННЫЕ");
            nameItem.setTextAlignment(TextAlignment.CENTER);
            vbPersonalInfo.toFront();
            initProfile();
        } else if (event.getSource() == btnProcurement) {
            nameItem.setText("ПОСТАВКИ");
            nameItem.setTextAlignment(TextAlignment.CENTER);
            vbProcurement.toFront();
            initTableProcurement();
        } else if (event.getSource() == btnFindingProduct) {
            nameItem.setText("ПОИСК");
            nameItem.setTextAlignment(TextAlignment.CENTER);
            vbFindProduct.toFront();
        } else if (event.getSource() == btnExit) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uni/example/storehouse/entrance.fxml"));
            Stage stage = new Stage();
            Parent root;
            stage.setTitle("вход");
            try {
                stage.setScene((new Scene((Pane) loader.load())));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage1 = (Stage) btnExit.getScene().getWindow();
            stage1.close();
            EntranceController editController = loader.getController();
            stage.showAndWait();
        }
    }

    private void initTableProcurement() {
        assert tableProcurements != null : "fx:id=\"tableProcurements\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        assert tfSelectedProcurement != null : "fx:id=\"tfSelectedProcurement\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        assert tcProviderName != null : "fx:id=\"tcProviderName\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        assert tcProcurNumber != null : "fx:id=\"tcProcurNumber\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        assert tcProcurStatus != null : "fx:id=\"tcProcurStatus\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        ObservableList<Procurement> spesialArr = null;
        try {
            ArrayList<Procurement> arr = connector.getProcurementsByManager(manager);
            for (Procurement u : arr) {
                System.out.println("Procurement arr: " + u.getIdProcurement() == null);
            }
            spesialArr = FXCollections.observableArrayList(arr);

        } catch (RemoteException e) {
            e.printStackTrace();
        }

        tcProviderName.setCellValueFactory(new PropertyValueFactory<>("providerStr"));
        // tcProcurManager.setCellValueFactory(new PropertyValueFactory<>("managerStr"));
        tcProcurNumber.setCellValueFactory(new PropertyValueFactory<>("idProcurement"));
        tcProcurStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableProcurements.setItems(spesialArr);

        TableView.TableViewSelectionModel<Procurement> selectionModel = tableProcurements.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<Procurement>() {
            @Override
            public void changed(ObservableValue<? extends Procurement> observableValue, Procurement animal, Procurement t1) {
                if (t1 != null) {
                    System.out.println("Выбранный поставка: " + t1.getIdProcurement());
                    selectedProcurement = t1;
                    tfSelectedProcurement.setText(String.valueOf(t1.getIdProcurement()));
                } else System.out.println("НЕ ВЫБРАН поставка!");
            }
        });
    }

    Procurement selectedProcurement = null;


    @FXML
    void moreInfoProcurement(ActionEvent event) {
        try {
            selectedProcurement.getStatus();
        } catch (Exception e) {
            showErrorAlert("Ошибка просмотра информации о поставке", "Выберите поставку для смотра");
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/uni/example/storehouse/procurementMoreInfo.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Информация о поставке");
        Parent root;
        try {
            stage.setScene(new Scene((Pane) loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        ProcurementMoreInfoController editController = loader.getController();
        editController.initData(selectedProcurement);
        stage.showAndWait();
        initTableProcurement();
    }


    @FXML
    void changePass(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/uni/example/storehouse/changePass.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Изменение пароля");
        Parent root;
        try {
            stage.setScene(new Scene((Pane) loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        ChangePassController editController = loader.getController();
        editController.initData(manager);
        stage.showAndWait();
    }

    private void showErrorAlert(String sms1, String sms2) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(sms1);
        alert.setContentText(sms2);
        alert.showAndWait();
    }

    @FXML
    void saveProfile(ActionEvent event) {
        manager.setName(tfName.getText());
        manager.setSurname(tfSurname.getText());
        manager.setPatronymic(tfPatro.getText());
        try {
            connector.changeUser(manager);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Изменение");
            alert.setHeaderText("Ваш профиль был изменён");
            alert.showAndWait();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void onFindById(ActionEvent event) {
        Integer productId = Integer.parseInt(tfIdProduct.getText());
        ArrayList<ProductPlacement> products = null;
        try {
            products = connector.findProductInStorehouseById(manager.getIdStorehouse(), productId);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NullPointerException у) {
            showErrorAlert("Ошибка поиска", "Такой продукт не был найден");
        }
        initTableWithProductPlacement(products);

    }

    void initTableWithProductPlacement(ArrayList<ProductPlacement> products) {
        ObservableList<ProductPlacement> spesialArr = null;
        spesialArr = FXCollections.observableArrayList(products);
        tcRack.setCellValueFactory(new PropertyValueFactory<>("rack"));
        tcPlace.setCellValueFactory(new PropertyValueFactory<>("place"));
        tableProducts.setItems(spesialArr);
        tfCountProduct.setText(String.valueOf(products.size()));
    }


    @FXML
    void initialize() {
        assert tcPlace != null : "fx:id=\"tcPlace\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert btMoreInfoProcurement != null : "fx:id=\"btMoreInfoProcurement\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert btnProfile != null : "fx:id=\"btnProfile\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert tfSurname != null : "fx:id=\"tfSurname\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert nameItem != null : "fx:id=\"nameItem\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert tfIdProduct != null : "fx:id=\"tfIdProduct\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert tableProcurements != null : "fx:id=\"tableProcurements\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert tcProviderName != null : "fx:id=\"tcProviderName\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert tfLogin != null : "fx:id=\"tfLogin\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert vbFindProduct != null : "fx:id=\"vbFindProduct\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert btnMyProfile != null : "fx:id=\"btnMyProfile\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert tcProcurStatus != null : "fx:id=\"tcProcurStatus\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert vbPersonalInfo != null : "fx:id=\"vbPersonalInfo\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert tcProcurNumber != null : "fx:id=\"tcProcurNumber\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert tfName != null : "fx:id=\"tfName\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert btFindById != null : "fx:id=\"btFindById\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert tcRack != null : "fx:id=\"tcRack\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert tableProducts != null : "fx:id=\"tableProducts\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert tfSelectedProcurement != null : "fx:id=\"tfSelectedProcurement\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert vbProcurement != null : "fx:id=\"vbProcurement\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert btnChangePass != null : "fx:id=\"btnChangePass\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert tfPatro != null : "fx:id=\"tfPatro\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert tfCountProduct != null : "fx:id=\"tfCountProduct\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert tfNameProduct != null : "fx:id=\"tfNameProduct\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert tfRole != null : "fx:id=\"tfRole\" was not injected: check your FXML file 'mainMenuManager.fxml'.";
        assert btnProcurement != null : "fx:id=\"btnProcurement\" was not injected: check your FXML file 'mainMenuManager.fxml'.";

    }

    private void initProfile() {
        tfLogin.setText(manager.getLogin());
        tfLogin.setEditable(false);
        tfName.setText(manager.getName());
        tfSurname.setText(manager.getSurname());
        tfPatro.setText(manager.getPatronymic());
        tfRole.setText(String.valueOf(manager.getRole()));
        tfRole.setEditable(false);
    }

    Connector connector = null;
    User manager = null;

    public void initData(String login) {
        try {
            connector = new Connector();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        try {
            manager = connector.getUserByLogin(login);
            System.out.println("получен профиль " + manager.getLogin());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        vbPersonalInfo.toFront();
        initProfile();
    }

    public void onFindTime(ActionEvent actionEvent) {
        try {
            int mins = connector.findWorkTime(manager);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Изменение");
            alert.setHeaderText("Тебе надо поработать " + mins + " минут");
            alert.showAndWait();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
