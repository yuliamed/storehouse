package uni.example.storehouse.controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Optional;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import uni.example.storehouse.entities.*;
import uni.example.storehouse.rmi.Connector;

public class AddNewProcurementController {
    @FXML
    private ComboBox<Integer> cbProviderId;

    @FXML
    private VBox vbUsers;

    @FXML
    private TableColumn<Product, String> tcNameProduct;

    @FXML
    private TableColumn<Product, Integer> tcIdProduct;

    @FXML
    private TextField tfProcurNumber;

    @FXML
    private TableColumn<Product, Double> tcPrice;

    @FXML
    private TableColumn<?, ?> tcCount;

    @FXML
    private TextField tfName;

    @FXML
    private TableColumn<Product, Integer> tcVAT;

    @FXML
    private TableColumn<Product, Double> tcMass;

    @FXML
    private Button btSave;

    @FXML
    private TextField tfAdress;

    @FXML
    private TableView<Product> tableProducts;

    @FXML
    private Button btExit;

    @FXML
    private ComboBox<String> cbMenegers;

    @FXML
    private TextField tfNumber;

    @FXML
    private TableColumn<Product, String> tcUnit;

    Connector connector = null;

    @FXML
    void initialize() {
        assert cbProviderId != null : "fx:id=\"cbProviderId\" was not injected: check your FXML file 'addNewProcurement.fxml'.";
        assert vbUsers != null : "fx:id=\"vbUsers\" was not injected: check your FXML file 'addNewProcurement.fxml'.";
        assert tcNameProduct != null : "fx:id=\"tcNameProduct\" was not injected: check your FXML file 'addNewProcurement.fxml'.";
        assert tcIdProduct != null : "fx:id=\"tcIdProduct\" was not injected: check your FXML file 'addNewProcurement.fxml'.";
        assert tfProcurNumber != null : "fx:id=\"tfProcurNumber\" was not injected: check your FXML file 'addNewProcurement.fxml'.";
        assert tcPrice != null : "fx:id=\"tcPrice\" was not injected: check your FXML file 'addNewProcurement.fxml'.";
        assert tcCount != null : "fx:id=\"tcCount\" was not injected: check your FXML file 'addNewProcurement.fxml'.";
        assert tfName != null : "fx:id=\"tfName\" was not injected: check your FXML file 'addNewProcurement.fxml'.";
        assert tcVAT != null : "fx:id=\"tcVAT\" was not injected: check your FXML file 'addNewProcurement.fxml'.";
        assert tcMass != null : "fx:id=\"tcMass\" was not injected: check your FXML file 'addNewProcurement.fxml'.";
        assert btSave != null : "fx:id=\"btSave\" was not injected: check your FXML file 'addNewProcurement.fxml'.";
        assert tfAdress != null : "fx:id=\"tfAdress\" was not injected: check your FXML file 'addNewProcurement.fxml'.";
        assert tableProducts != null : "fx:id=\"tableProducts\" was not injected: check your FXML file 'addNewProcurement.fxml'.";
        assert btExit != null : "fx:id=\"btExit\" was not injected: check your FXML file 'addNewProcurement.fxml'.";
        assert cbMenegers != null : "fx:id=\"cbMenegers\" was not injected: check your FXML file 'addNewProcurement.fxml'.";
        assert tfNumber != null : "fx:id=\"tfNumber\" was not injected: check your FXML file 'addNewProcurement.fxml'.";
        assert tcUnit != null : "fx:id=\"tcUnit\" was not injected: check your FXML file 'addNewProcurement.fxml'.";

        try {
            connector = new Connector();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        initComboboxex();
        initData();
    }

    private ArrayList<User> users = null;
    private ArrayList<Provider> provides = null;

    public void initData() {
        selectedProvider = null;
        tfNumber.setText(null);
        tfName.setText(null);
        tfAdress.setText(null);
        tfNumber.setEditable(true);
        tfName.setEditable(true);
        tfAdress.setEditable(true);

    }

    private void initComboboxex() {
        ObservableList<String> spesialArr = null;
        try {
            users = connector.readUsers();
            ArrayList<String> strings = new ArrayList<String>();
            strings.add("-");
            for (User s : users) {
                if (s.getRole() != 1) strings.add(s.getLogin());
            }
            spesialArr = FXCollections.observableArrayList(strings);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        cbMenegers.setItems(spesialArr);
        ObservableList<Integer> spesial = null;
        try {
            provides = connector.getProviders();
            ArrayList<Integer> integers = new ArrayList<Integer>();
            integers.add(0);
            for (Provider s : provides) {
                integers.add(s.getIdProvider());
            }
            spesial = FXCollections.observableArrayList(integers);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        cbProviderId.setItems(spesial);
        cbProviderId.getSelectionModel().select(spesial.get(0));
    }

    boolean flag = false;

    void loadAddProduct() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/uni/example/storehouse/addProduct.fxml"));
        Stage stage = new Stage();
        Parent root;
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Добавление новой поставки");
        try {
            stage.setScene((new Scene((Pane) loader.load())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddProductController editController = loader.getController();
        editController.initProcurement(procurement);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private boolean checkProcurementInfo() {
        try {
            Integer.parseInt(tfProcurNumber.getText());
        } catch (NumberFormatException e) {
            showAlert("Введите номер поставки");
            return false;
        }

        if (tfAdress.getText().isEmpty() || tfName.getText().isEmpty()) {
            showAlert("Заполните данные поставщика");
            return false;
        }

        if (cbMenegers.getValue()==null||cbMenegers.getValue().equals("-")) {
            showAlert("Выберите менеджера");
            return false;
        }

        try {
            Integer numb = Integer.parseInt(tfNumber.getText());
            checkPhNumber(tfNumber.getText());
        } catch (NumberFormatException e) {
            showAlert("Введите правильно номер поставщика");
            return false;
        }
        return true;
    }

    private void checkPhNumber(String numb) throws NumberFormatException {
        if (numb.length() != 9) throw new NumberFormatException();
    }

    void showAlert(String sms) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка заполнения полей");
        alert.setContentText(sms);
        alert.showAndWait();
    }

    Provider selectedProvider = null;

    public void onSelectProvider(ActionEvent actionEvent) {
        Integer id = cbProviderId.getValue();
        if (id != 0) {
            for (Provider p : provides)
                if (p.getIdProvider().compareTo(id) == 0) {
                    selectedProvider = p;
                    break;
                }
            tfNumber.setText(String.valueOf(selectedProvider.getProviderPhNumber()));
            tfName.setText(selectedProvider.getProviderName());
            tfAdress.setText(selectedProvider.getProviderAdress());
            tfNumber.setEditable(false);
            tfName.setEditable(false);
            tfAdress.setEditable(false);
        } else {
            selectedProvider = null;
            tfNumber.setText(null);
            tfName.setText(null);
            tfAdress.setText(null);
            tfNumber.setEditable(true);
            tfName.setEditable(true);
            tfAdress.setEditable(true);
        }
    }

    public void addProduct(ActionEvent actionEvent) {
        if (!flag && checkProcurementInfo()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);//создаем окно подтверждения
            //далее заголовки и текст по желанию
            alert.setTitle("Подтверждение");
            alert.setHeaderText("Подтверждение данных по поставке");
            alert.setContentText("Вы действительно хотите созранить данные по поставке и перейти к добавлению продуктов?");
            //создаем кнопки "да" и "нет"
            ButtonType buttonTypeOne = new ButtonType("Yes");
            ButtonType buttonTypeCancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);//додаем кнопки к самому окну подтверждения
            Optional<ButtonType> result = alert.showAndWait();//вызываем окно подтверждения
            if (result.get() == buttonTypeCancel) {
                actionEvent.consume(); //отменяем событие если пользователь нажал отмену
                return;
            }

            procurement = new Procurement();
            if (selectedProvider != null)
                procurement.setProvider(selectedProvider);
            else {
                Provider provider = new Provider();
                provider.setProviderAdress(tfAdress.getText());
                provider.setProviderName(tfName.getText());
                provider.setProviderPhNumber(Integer.valueOf(tfNumber.getText()));
                int id = 0;
                try {
                    id = connector.addProvider(provider);
                    provider.setIdProvider(id);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    showAlert("Ошибка работы с connector", "Добавление нового поставщика не удалось");
                    return;
                }
                procurement.setProvider(provider);
            }
            procurement.setIdProcurement(Integer.valueOf(tfProcurNumber.getText()));
            String login = cbMenegers.getValue();
            User manager = getManager(login);
            procurement.setManagerLogin(manager);
            try {
                //TODO вот здесь собака ломается
                connector.addProcurement(procurement);
            } catch (RemoteException e) {
                e.printStackTrace();
                showAlert("Ошибка работы с connector", "Добавление новой поставки не удалось");
                return;
            }
            flag = true;
            tfNumber.setEditable(false);
            tfName.setEditable(false);
            tfAdress.setEditable(false);
            tfProcurNumber.setEditable(false);
            cbProviderId.setEditable(false);
            cbProviderId.setDisable(true);
            cbMenegers.setDisable(true);
            loadAddProduct();
            return;

        }
        if (flag) loadAddProduct();

    }

    private User getManager(String login) {
        for (User u : users) {
            if (u.getLogin().equals(login))
                return u;
        }
        return null;
    }

    void showAlert(String sms1, String sms2) {
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Ошибка");
        alert1.setHeaderText(sms1);
        alert1.setContentText(sms2);
        alert1.showAndWait();
    }

    Procurement procurement = null;

    public void deleteProduct(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);//создаем окно подтверждения
        //далее заголовки и текст по желанию
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Подтверждение удаления");
        alert.setContentText("Вы действительно хотите выбранный продукт (" + selectedProduct.getName() + ") из списка поставки?");
        //создаем кнопки "да" и "нет"
        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);//додаем кнопки к самому окну подтверждения
        Optional<ButtonType> result = alert.showAndWait();//вызываем окно подтверждения
        if (result.get() == buttonTypeCancel) {
            actionEvent.consume(); //отменяем событие если пользователь нажал отмену
            return;
        }
        try {
                        connector.deleteProcurementProduct(procurement, selectedProduct);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    Product selectedProduct = null;

    public void onRefrsh(ActionEvent actionEvent) {
        ArrayList<ProcurementProduct> pp = null;
        try {
            pp = connector.getProcurementProductByProcur(procurement);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        ArrayList<Product> products = new ArrayList<Product>();
        ObservableList<Integer> spesial_counts = null;
        for (ProcurementProduct p_p : pp) {
            products.add(p_p.getProduct());
            //counts.add(p_p.getCount());
        }
        ObservableList<Product> spesial_products = FXCollections.observableArrayList(products);
        ;
        tcIdProduct.setCellValueFactory(new PropertyValueFactory<>("uniqueCode"));
        tcMass.setCellValueFactory(new PropertyValueFactory<>("mass"));
        tcNameProduct.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tcUnit.setCellValueFactory(new PropertyValueFactory<>("measureUnit"));
        tcVAT.setCellValueFactory(new PropertyValueFactory<>("VAT"));
        tableProducts.setItems(spesial_products);
        TableView.TableViewSelectionModel<Product> selectionModel = tableProducts.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<Product>() {
            @Override
            public void changed(ObservableValue<? extends Product> observableValue, Product animal, Product t1) {
                if (t1 != null) {
                    System.out.println(t1.getName());
                    selectedProduct = t1;
                    //tfSelectedUser.setText(t1.getLogin());
                } else System.out.println("НЕ ВЫБРАН ЭЛЕМЕНТ!");
            }
        });
    }

    public void onExit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);//создаем окно подтверждения
        //далее заголовки и текст по желанию
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Подтверждение удаления поставки");
        alert.setContentText("Вы действительно хотите удалить поставку?");
        //создаем кнопки "да" и "нет"
        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);//додаем кнопки к самому окну подтверждения
        Optional<ButtonType> result = alert.showAndWait();//вызываем окно подтверждения
        if (result.get() == buttonTypeCancel) {
            actionEvent.consume(); //отменяем событие если пользователь нажал отмену
            return;
        }
        if (flag) {
            try {
                connector.deleteProcurementWithProducts(procurement);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        Stage stage1 = (Stage) btExit.getScene().getWindow();
        stage1.close();
    }

    public void onSave(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);//создаем окно подтверждения
        //далее заголовки и текст по желанию
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Подтверждение поставки");
        alert.setContentText("Вы действительно хотите закончить заполнение поставки?");
        //создаем кнопки "да" и "нет"
        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);//додаем кнопки к самому окну подтверждения
        Optional<ButtonType> result = alert.showAndWait();//вызываем окно подтверждения
        if (result.get() == buttonTypeCancel) {
            actionEvent.consume(); //отменяем событие если пользователь нажал отмену
            return;
        }
        Stage stage1 = (Stage) btExit.getScene().getWindow();
        stage1.close();
    }
}
