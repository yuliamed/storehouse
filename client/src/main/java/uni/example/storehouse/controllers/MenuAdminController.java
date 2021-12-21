package uni.example.storehouse.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import uni.example.storehouse.entities.Storehouse;
import uni.example.storehouse.entities.User;
import uni.example.storehouse.rmi.Connector;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MenuAdminController implements Initializable {
    private Connector connector;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfSurname;
    @FXML
    private TextField tfPatro;
    @FXML
    private TextField tfRole;
    @FXML
    private TextField tfLogin;

    @FXML
    private Button btnProfile;
    @FXML
    private Button btnChangePass;


    @FXML
    private TextField tfSelectedUser;
    @FXML
    private VBox vbUsers;
    @FXML
    private VBox vbPersonalInfo;
    @FXML
    private VBox vbProcurement;
    @FXML
    private VBox vbStorehouses;
    @FXML
    private TableView<User> tableUsers;
    @FXML
    private TableColumn<User, Byte> tcUserRole;
    @FXML
    private TableColumn<User, String> tcUserLogin;
    @FXML
    private TableColumn<User, Integer> tcUserNumber;
    @FXML
    private TableColumn<User, String> tcUserSurname;
    @FXML
    private TableColumn<User, String> tcUserName;
    @FXML
    private TableColumn<User, Integer> tcUserStorehouse;
    @FXML
    private TableColumn<User, String> tcUserPatro;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnMyProfile;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnExit;

    @FXML
    private Label nameItem;

    @FXML
    private Button btnProcurement;
    User selectedUser;
    User profile = null;

    public void initData(String login) {
        try {
            connector = new Connector();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        try {
            profile = connector.getUserByLogin(login);
            System.out.println("получен профиль " + profile.getLogin());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        vbPersonalInfo.toFront();
        initProfile();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    @FXML
    private void handleClicks(ActionEvent event) {

        if (event.getSource() == btnMyProfile) {
            nameItem.setText("МОИ ДАННЫЕ");
            nameItem.setTextAlignment(TextAlignment.CENTER);
            vbPersonalInfo.toFront();
            //initProfile();
        } else if (event.getSource() == btnUsers) {
            nameItem.setText("ПОЛЬЗОВАТЕЛИ");
            nameItem.setTextAlignment(TextAlignment.CENTER);
            vbUsers.toFront();
            initTableUsers();

        } else if (event.getSource() == btnProcurement) {
            nameItem.setText("ПОСТАВКИ");
            nameItem.setTextAlignment(TextAlignment.CENTER);
            vbProcurement.toFront();
            initTableProcurement();
        } else if (event.getSource() == btnOrders) {
            nameItem.setText("СКЛАДЫ");
            nameItem.setTextAlignment(TextAlignment.CENTER);
            vbStorehouses.toFront();
            initTableStorehouses();
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


    @FXML
    private TableColumn<Procurement, String> tcProviderName;
    @FXML
    private TableColumn<Procurement, String> tcProcurManager;
    @FXML
    private TableColumn<Procurement, Integer> tcProcurNumber;

    @FXML
    private TableColumn<Procurement, Boolean> tcProcurStatus;
    @FXML
    private TableView<Procurement> tableProcurements;
    @FXML
    private TextField tfSelectedProcurement;

    Procurement selectedProcurement;

    private void initTableProcurement() {
        assert tableProcurements != null : "fx:id=\"tableProcurements\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        assert tfSelectedProcurement != null : "fx:id=\"tfSelectedProcurement\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        assert tcProviderName != null : "fx:id=\"tcProviderName\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        assert tcProcurManager != null : "fx:id=\"tcProcurManager\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        assert tcProcurNumber != null : "fx:id=\"tcProcurNumber\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        assert tcProcurStatus != null : "fx:id=\"tcProcurStatus\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        ObservableList<Procurement> spesialArr = null;
        try {
            ArrayList<Procurement> arr = connector.getProcurements();
            for (Procurement u : arr) {
                System.out.println("Procurement arr: " + u.getIdProcurement() == null);
            }
            spesialArr = FXCollections.observableArrayList(arr);

        } catch (RemoteException e) {
            e.printStackTrace();
        }

        tcProviderName.setCellValueFactory(new PropertyValueFactory<>("providerStr"));
        tcProcurManager.setCellValueFactory(new PropertyValueFactory<>("managerStr"));
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

    private void initProfile() {
        tfLogin.setText(profile.getLogin());
        tfLogin.setEditable(false);
        tfName.setText(profile.getName());
        tfSurname.setText(profile.getSurname());
        tfPatro.setText(profile.getPatronymic());
        tfRole.setText(String.valueOf(profile.getRole()));
        tfRole.setEditable(false);
    }

    private void initTableUsers() {
        assert tcUserLogin != null : "fx:id=\"tcUserLogin\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        assert tcUserName != null : "fx:id=\"tcUserName\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        assert tcUserPatro != null : "fx:id=\"tcUserPatro\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        assert tcUserRole != null : "fx:id=\"tcUserRole\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        assert tfLogin != null : "fx:id=\"tfLogin\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        assert tcUserNumber != null : "fx:id=\"tcUserNumber\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        assert tcUserStorehouse != null : "fx:id=\"tcUserStorehouse\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        ObservableList<User> spesialArr = null;
        try {
            ArrayList<User> arr = connector.readUsers();
            for (User u : arr) {
                System.out.println(u.getIdStorehouse() == null);
            }
            spesialArr = FXCollections.observableArrayList(arr);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        tcUserName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcUserSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        tcUserPatro.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        tcUserStorehouse.setCellValueFactory(new PropertyValueFactory<>("intIdShouse"));

        tcUserRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        tcUserLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        tableUsers.setItems(spesialArr);
        TableView.TableViewSelectionModel<User> selectionModel = tableUsers.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observableValue, User animal, User t1) {
                if (t1 != null) {
                    System.out.println(t1.getLogin());
                    selectedUser = t1;
                    tfSelectedUser.setText(t1.getLogin());
                } else System.out.println("НЕ ВЫБРАН ЭЛЕМЕНТ!");
            }
        });
    }

    public void addProcurement(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/uni/example/storehouse/addNewProcurement.fxml"));
        Stage stage = new Stage();
        Parent root;
        stage.setTitle("Добавление новой поставки");
        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            stage.setScene((new Scene((Pane) loader.load())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddNewProcurementController editController = loader.getController();
        stage.showAndWait();
        initTableProcurement();
    }

    public void deleteProcurement(ActionEvent actionEvent) {
        if (selectedProcurement != null) {
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
            deleteProcurementFromDB();
            initTableProcurement();
        } else {
            showErrorAlert("Ошибка удаления", "Не выбрана поставка для удаления))");
        }

    }

    private void deleteProcurementFromDB() {
        try {
            connector.deleteProcurementWithProducts(selectedProcurement);
        } catch (RemoteException e) {
            e.printStackTrace();
            showErrorAlert("Ощибка удаление поставки", "поставку " + selectedProcurement.getIdProcurement() + " не удалось удалить(");
        }
    }

    private void showErrorAlert(String sms1, String sms2) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(sms1);
        alert.setContentText(sms2);
        alert.showAndWait();
    }

    public void moreInfoProcurement(ActionEvent actionEvent) {
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

    public void editUser(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/uni/example/storehouse/userInfo.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Изменение пользователя");
        Parent root;
        try {
            stage.setScene(new Scene((Pane) loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        UserInfoController editController = loader.getController();
        editController.initData(selectedUser.getLogin());
        stage.showAndWait();
        initTableUsers();
    }

    public void deleteUser(ActionEvent actionEvent) {
        try {
            selectedUser.getLogin();
        } catch (Exception e) {
            showErrorAlert("Ошибка удаления пользователя", "Выберите пользователя");
            return;
        }
        try {
            connector.deleteUser(selectedUser);
        } catch (RemoteException e) {
            showErrorAlert("Ошибка удаления пользователя", "Не удалось удалить пользователя");
            return;
        } catch (SecurityException e) {
            showErrorAlert("Ошибка удаления пользователя", "Не удалось удалить админа");
            return;
        } finally {
            initTableUsers();
        }
    }

    public void addUser(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/uni/example/storehouse/userInfo.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Добавление пользователя");
        Parent root;
        try {
            stage.setScene(new Scene((Pane) loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        UserInfoController editController = loader.getController();
        editController.initData("newUser");
        stage.showAndWait();
        initTableUsers();
    }

    public void changePass(ActionEvent actionEvent) {
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
        editController.initData(profile);
        stage.showAndWait();
    }

    public void saveProfile(ActionEvent actionEvent) {
        profile.setName(tfName.getText());
        profile.setSurname(tfSurname.getText());
        profile.setPatronymic(tfPatro.getText());
        try {
            connector.changeUser(profile);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Изменение");
            alert.setHeaderText("Ваш профиль был изменён");
            alert.showAndWait();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private TableColumn<Storehouse, Integer> tcStorehouseID;
    @FXML
    private TableColumn<Storehouse, String> tcStorehouseAdress;
    @FXML
    private TableColumn<Storehouse, Integer> tcStorehouseMaxRack;

    @FXML
    private TableColumn<Storehouse, Integer> tcStorehouseMaxPlaces;
    @FXML
    private TableView<Storehouse> tableStorehouse;
    @FXML
    private TextField tfSelectedStore;

    private void initTableStorehouses() {
        assert tableStorehouse != null : "fx:id=\"tableStorehouse\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        assert tfSelectedStore != null : "fx:id=\"tfSelectedStore\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        assert tcStorehouseMaxPlaces != null : "fx:id=\"tcStorehouseMaxPlaces\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        assert tcStorehouseMaxRack != null : "fx:id=\"tcStorehouseMaxRack\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        assert tcStorehouseAdress != null : "fx:id=\"tcStorehouseAdress\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        assert tcStorehouseID != null : "fx:id=\"tcStorehouseID\" was not injected: check your FXML file 'mainMenuAdmin.fxml'.";
        ObservableList<Storehouse> spesialArr = null;
        try {
            ArrayList<Storehouse> arr = connector.readStorehouses();
            spesialArr = FXCollections.observableArrayList(arr);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        tcStorehouseMaxPlaces.setCellValueFactory(new PropertyValueFactory<>("maxCells"));
        tcStorehouseMaxRack.setCellValueFactory(new PropertyValueFactory<>("maxRows"));
        tcStorehouseAdress.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcStorehouseID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableStorehouse.setItems(spesialArr);

        TableView.TableViewSelectionModel<Storehouse> selectionModel = tableStorehouse.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<Storehouse>() {
            @Override
            public void changed(ObservableValue<? extends Storehouse> observableValue, Storehouse animal, Storehouse t1) {
                if (t1 != null) {
                    System.out.println("Выбранный поставка: " + t1.getId());
                    selectedStorehouse = t1;
                    tfSelectedStore.setText(String.valueOf(t1.getId()));
                } else System.out.println("НЕ ВЫБРАН поставка!");
            }
        });

    }

    Storehouse selectedStorehouse = null;

    public void onAnalizeUser(ActionEvent actionEvent) {
    }

    public void onAnalizeAllStores(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/uni/example/storehouse/fullAnalizeStorehouse.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Анализ склада");
        Parent root;
        try {
            stage.setScene(new Scene((Pane) loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        FullAnalizeStorehouseController editController = loader.getController();
        stage.showAndWait();
    }

    public void onAnalizeStorehouse(ActionEvent actionEvent) {
        if(selectedStorehouse!=null){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/uni/example/storehouse/analizeStorehouse.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Анализ склада");
        Parent root;
        try {
            stage.setScene(new Scene((Pane) loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.initModality(Modality.APPLICATION_MODAL);
       AnalizeStorehouse editController = loader.getController();
        editController.initData(selectedStorehouse);
        stage.showAndWait();}
        else {
            showErrorAlert("Ошибка Анализа", "Не выбран склад для Анализа))");
        }
    }

    public void onDeleteStorehouse(ActionEvent actionEvent) {
        if (selectedStorehouse != null) {
            System.out.println("Удание склада");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);//создаем окно подтверждения
            //далее заголовки и текст по желанию
            alert.setTitle("Подтверждение");
            alert.setHeaderText("Подтверждение удаления склада");
            alert.setContentText("Вы действительно хотите удалить склад со всеми его поставками?");
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
                connector.deleteStorehouse(selectedStorehouse);
            } catch (RemoteException e) {
                e.printStackTrace();
                showErrorAlert("Ощибка удаление склада", "склад " + selectedStorehouse.getId() + " не удалось удалить(");
            }
            initTableStorehouses();
        } else {
            showErrorAlert("Ошибка удаления", "Не выбран склад для удаления))");
        }
    }

    public void onAddStorehouse(ActionEvent actionEvent) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/uni/example/storehouse/addStorehouse.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Добавление склада");
        Parent root;
        try {
            stage.setScene(new Scene((Pane) loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        AddStorehouseController editController = loader.getController();
        editController.initData(null);
        stage.showAndWait();
        initTableStorehouses();
    }

    public void onUpdateStoreshouse(ActionEvent actionEvent) {
        if (selectedStorehouse != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uni/example/storehouse/addStorehouse.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Редактирование склада");
            Parent root;
            try {
                stage.setScene(new Scene((Pane) loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.initModality(Modality.APPLICATION_MODAL);
            AddStorehouseController editController = loader.getController();
            editController.initData(selectedStorehouse);
            stage.showAndWait();
            initTableStorehouses();
        } else {
            showErrorAlert("Ошибка редактирования", "Не выбран склад для редактирования))");
        }
    }
}
