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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uni.example.storehouse.entities.Storehouse;
import uni.example.storehouse.entities.User;
import uni.example.storehouse.rmi.Connector;

public class UserInfoController {
    private String workingLogin;
    private Connector connector;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TextField tfLogin;

    @FXML
    private ComboBox<Integer> cbIdStorehouse;

    @FXML
    private TextField tfPatro;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfSurname;
    // TODO изменить на выпадающий список
    @FXML
    private TextField tfRole;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnExit;

    @FXML
    void initialize() {
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'userInfo.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'userInfo.fxml'.";
        assert cbIdStorehouse != null : "fx:id=\"cbIdStorehouse\" was not injected: check your FXML file 'userInfo.fxml'.";

        try {
            connector = new Connector();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    private User user = null;

    public void onSave(ActionEvent actionEvent) {

        Integer storehouse = null;
        Byte role = null;
        // TODO доделать проверки сограсно типу поля
        try {
            storehouse = cbIdStorehouse.getValue();
            role = (Byte) Byte.parseByte(tfRole.getText());
        } catch (NumberFormatException e) {
            System.out.println("ппц");
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Приведение типов");
            alert.setHeaderText("не удалось преобразовать значение к числу");
            alert.showAndWait();
            System.out.println("role: " + role);
            System.out.println("storehouse: " + storehouse);
            return;
        }
        if (workingLogin.equals("newUser")) {
            try {
                Storehouse storehouse1 = null;
                for (Storehouse s : arr)
                    if (s.getId() == storehouse) {
                        storehouse1 = s;
                        break;
                    }
                connector.addUser(tfLogin.getText(), tfName.getText(), tfSurname.getText(), tfPatro.getText(),
                        role, storehouse1);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            try {Storehouse storehouse1 = null;
                for (Storehouse s : arr)
                    if (s.getId() == storehouse) {
                        storehouse1 = s;
                        break;
                    }
                user.setName(tfName.getText());
                user.setSurname(tfSurname.getText());
                user.setPatronymic(tfPatro.getText());
                user.setStorehouse(storehouse1);
                user.setRole(role);
                connector.changeUser(user);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        Stage stage1 = (Stage) btnSave.getScene().getWindow();
        stage1.close();
    }

    public void onExit(ActionEvent actionEvent) {
        Stage stage1 = (Stage) btnExit.getScene().getWindow();
        stage1.close();
    }

    ArrayList<Storehouse> arr = null;

    void initData(String login) {
        workingLogin = login;
        ObservableList<Integer> spesialArr = null;
        try {
            arr = connector.readStorehouses();
            System.out.println(arr.size());
            ArrayList<Integer> integers = new ArrayList<Integer>();
            for (Storehouse s : arr)
                integers.add(s.getId());
            System.out.println("INTEGERS: " + integers.size());
            spesialArr = FXCollections.observableArrayList(integers);
            System.out.println("spesial: " + spesialArr.size());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        cbIdStorehouse.setItems(spesialArr);
        if (!workingLogin.equals("newUser")) {
            try {
                user = connector.getUserByLogin(workingLogin);
                tfLogin.setText(user.getLogin());
                tfLogin.setEditable(false);
                if (user.getIdStorehouse() != null)
                    cbIdStorehouse.getSelectionModel().select(user.getIdStorehouse().getId());
                //cbIdStorehouse.set(String.valueOf(user.getIdStorehouse()));
                tfName.setText(user.getName());
                tfPatro.setText(user.getPatronymic());
                tfSurname.setText(user.getSurname());
                tfRole.setText(String.valueOf(user.getRole()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}