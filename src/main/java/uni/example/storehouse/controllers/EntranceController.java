package uni.example.storehouse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import uni.example.storehouse.entities.Storehouse;
import uni.example.storehouse.rmi.Connector;
import uni.example.storehouse.rmi.IUser;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EntranceController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button entrance;

    @FXML
    private TextField loginIn;

    @FXML
    private TextField passIn;

    @FXML
    void onEntrance(ActionEvent event) throws NamingException {
    }
    @FXML
    void initialize() {
        assert loginIn != null : "fx:id=\"loginIn\" was not injected: check your FXML file 'entrance.fxml'.";
        assert entrance != null : "fx:id=\"entrance\" was not injected: check your FXML file 'entrance.fxml'.";
        assert passIn != null : "fx:id=\"passIn\" was not injected: check your FXML file 'entrance.fxml'.";

        entrance.setOnAction(event -> {
            try {
                Connector connector = new Connector();

                String login = loginIn.getText().trim();
                String password = passIn.getText().trim();
                // TODO проверочки

                if (connector.checkUser(login, password) == true) {
                    System.out.println("Вселенский успех");
                    if(connector.isUserAdmin(login)==true) {

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/uni/example/storehouse/mainMenuAdmin.fxml"));
                        Stage stage = new Stage();
                        Parent root;
                        try {
                            stage.setScene(new Scene((Pane) loader.load()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Stage stage1 = (Stage) entrance.getScene().getWindow();
                        stage1.close();
                        MenuAdminController editController = loader.getController();
                        editController.initData(login);
                        stage.show();
                    } else {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/uni/example/storehouse/mainMenuManager.fxml"));
                        Stage stage = new Stage();
                        Parent root;
                        try {
                            stage.setScene(new Scene((Pane) loader.load()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Stage stage1 = (Stage) entrance.getScene().getWindow();
                        stage1.close();
                        MenuManagerController editController = loader.getController();
                        editController.initData(login);
                        stage.show();
                    }

                } else {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Авторизация");
                    alert.setHeaderText("Ошибка овторизации, проверьте логин и пароль");
                    alert.showAndWait();
                    System.out.println("Полное разочарование");
                }

            } catch (RemoteException | NotBoundException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Авторизация");
                alert.setHeaderText("Нет подключения к серверу");
                alert.showAndWait();
            }
        });
    }
}