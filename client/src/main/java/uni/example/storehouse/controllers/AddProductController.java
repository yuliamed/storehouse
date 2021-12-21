package uni.example.storehouse.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import uni.example.storehouse.entities.*;
import uni.example.storehouse.rmi.Connector;

public class AddProductController {


    public void initData(Product p) {

        tfCount.setVisible(false);
        labCount.setVisible(false);
        labProvider.setVisible(false);
        cbProducts.setVisible(false);

        tfVAT.setEditable(false);
        tfUnit.setEditable(false);
        tfMass.setEditable(false);
        tfPrice.setEditable(false);
        tfName.setEditable(false);
        tfId.setEditable(false);
        initFieldsWithWorkingProduct(p);
        btSave.setVisible(false);

    }

    private void initFieldsWithWorkingProduct(Product workingProduct) {
        tfUnit.setText(workingProduct.getMeasureUnit());
        tfMass.setText(String.valueOf(workingProduct.getMass()));
        tfVAT.setText(String.valueOf(workingProduct.getVAT()));
        tfName.setText(workingProduct.getName());
        tfPrice.setText(String.valueOf(workingProduct.getPrice()));
        tfId.setText(String.valueOf(workingProduct.getUniqueCode()));

        tfUnit.setEditable(false);
        tfMass.setEditable(false);
        tfVAT.setEditable(false);
        tfName.setEditable(false);
        tfPrice.setEditable(false);
        tfId.setEditable(false);


    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfUnit;

    @FXML
    private TextField tfMass;

    @FXML
    private VBox vbPersonalInfo;

    @FXML
    private TextField tfVAT;

    @FXML
    private TextField tfCount;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPrice;

    @FXML
    private TextField tfId;

    @FXML
    private Button btSave;

    @FXML
    private Button btExit;
    @FXML
    private Label labCount;
    @FXML
    private Label labProvider;
    @FXML
    private ComboBox<String> cbProducts;

    @FXML
    void initialize() {
        assert tfUnit != null : "fx:id=\"tfUnit\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert tfMass != null : "fx:id=\"tfMass\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert vbPersonalInfo != null : "fx:id=\"vbPersonalInfo\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert tfVAT != null : "fx:id=\"tfVAT\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert tfCount != null : "fx:id=\"tfCount\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert tfName != null : "fx:id=\"tfName\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert tfPrice != null : "fx:id=\"tfPrice\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert tfId != null : "fx:id=\"tfId\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert btSave != null : "fx:id=\"btSave\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert btExit != null : "fx:id=\"btExit\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert labCount != null : "fx:id=\"labCount\" was not injected: check your FXML file 'addProduct.fxml'.";

        assert labProvider != null : "fx:id=\"labProvider\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert cbProducts != null : "fx:id=\"cbProducts\" was not injected: check your FXML file 'addProduct.fxml'.";
        btExit.setOnAction(actionEvent -> {
            Stage stage1 = (Stage) btExit.getScene().getWindow();
            stage1.close();
        });
        try {
            connector = new Connector();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    Connector connector = null;
    ArrayList<Product> products = null;
    Procurement workingProcurement = null;


    private void initCombobox() {
        System.out.println("init combobox");
        try {
            products = connector.getProducts();
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("Can't read products");
        }
        ObservableList<String> spesialArr = null;
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("-");
        for (Product p : products) {
            strings.add(p.getUniqueCode() + " " + p.getName());
        }
        spesialArr = FXCollections.observableArrayList(strings);
        cbProducts.setItems(spesialArr);
        cbProducts.getSelectionModel().select(spesialArr.get(0));
    }

    public void initProcurement(Procurement procurement) {
        workingProcurement = procurement;
        initCombobox();
        selectedProduct = null;
        tfUnit.setText(null);
        tfName.setText(null);
        tfId.setText(null);
        tfPrice.setText(null);
        tfMass.setText(null);
        tfVAT.setText(null);

        tfUnit.setEditable(true);
        tfName.setEditable(true);
        tfId.setEditable(true);
        tfPrice.setEditable(true);
        tfMass.setEditable(true);
        tfVAT.setEditable(true);

    }

    public void onProducts(ActionEvent actionEvent) {
        String value = cbProducts.getValue();
        selectedProduct = selectProduct(value);
        if (selectedProduct != null) {
            tfUnit.setText(selectedProduct.getMeasureUnit());
            tfName.setText(selectedProduct.getName());
            tfId.setText(String.valueOf(selectedProduct.getUniqueCode()));
            tfPrice.setText(String.valueOf(selectedProduct.getPrice()));
            tfMass.setText(String.valueOf(selectedProduct.getMass()));
            tfVAT.setText(String.valueOf(selectedProduct.getVAT()));

            tfUnit.setEditable(false);
            tfName.setEditable(false);
            tfId.setEditable(false);
            tfPrice.setEditable(false);
            tfMass.setEditable(false);
            tfVAT.setEditable(false);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("не нашёлся товар(");
            alert.showAndWait();
        }
    }

    Product selectedProduct = null;

    private Product selectProduct(String value) {
        Integer index = value.indexOf(" ");
        String strId = null;
        if (index != -1)
            strId = value.substring(0, index);
        Integer id = null;
        try {
            id = Integer.parseInt(strId);
            System.out.println("id selected product  " + id);
        } catch (NumberFormatException e) {
            System.out.println("Не удалось получить айди продукта для его добавления");
        }

        for (Product pr : products) {
            if (pr.getUniqueCode().compareTo(id) == 0)
                return pr;
        }
        return null;
    }


    public void onExit(ActionEvent actionEvent) {
        Stage stage1 = (Stage) btExit.getScene().getWindow();
        stage1.close();
    }

    public void onSave(ActionEvent actionEvent) {
        if (selectedProduct == null) {
            if (checkFields()) {
                selectedProduct = new Product();
                selectedProduct.setName(tfName.getText());
                selectedProduct.setUniqueCode(Integer.parseInt(tfId.getText()));
                selectedProduct.setMass(Double.parseDouble(tfMass.getText()));
                selectedProduct.setPrice(Double.parseDouble(tfPrice.getText()));
                selectedProduct.setVAT(Double.parseDouble(tfVAT.getText()));
                selectedProduct.setMeasureUnit(tfUnit.getText());
                try {
                    connector.addProduct(selectedProduct);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else return;
        }

        try {
            Integer.parseInt(tfCount.getText());
        } catch (NumberFormatException e) {
            showAlert("Введите корректное значние количества");
            return;
        }

        ProcurementProduct pp = new ProcurementProduct();
        pp.setProduct(selectedProduct);
        pp.setCount(Integer.valueOf(tfCount.getText()));
        pp.setProcurement(workingProcurement);

        try {
            connector.addProcurementProduct(pp);
        } catch (RemoteException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка работы с connector");
            alert.setContentText("Добавление новой поставки с продуктами не удалось");
            alert.showAndWait();
        }

        Stage stage1 = (Stage) btSave.getScene().getWindow();
        stage1.close();

    }

    private boolean checkFields() {
        try {
            Integer.parseInt(tfId.getText());
        } catch (NumberFormatException e) {
            showAlert("Введите корректный номер продукта");
            return false;
        }
        try {
            Double.parseDouble(tfVAT.getText());
        } catch (NumberFormatException e) {
            showAlert("Введите корректное значение НДС");
            return false;
        }
        try {
            Double.parseDouble(tfMass.getText());
        } catch (NumberFormatException e) {
            showAlert("Введите корректное значение массы");
            return false;
        }
        try {
            Double.parseDouble(tfPrice.getText());
        } catch (NumberFormatException e) {
            showAlert("Введите корректное значение цены");
            return false;
        }
        if (tfName.getText().isEmpty() || tfUnit.getText().isEmpty()) {
            showAlert("Заполните поле названия и единицы измерения");
            return false;
        }
        return true;
    }

    void showAlert(String sms) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка заполнения полей");
        alert.setContentText(sms);
        alert.showAndWait();
    }
}
