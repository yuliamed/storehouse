package uni.example.storehouse.controllers;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uni.example.storehouse.entities.User;
import uni.example.storehouse.rmi.Connector;

public class ChangePassController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField tfNewPass;

    @FXML
    private Button btnSave;

    @FXML
    private PasswordField tfRepeatNewPass;

    @FXML
    private Label nameItem;

    @FXML
    private PasswordField tfOldPass;

    @FXML
    private Button btnExit;

    @FXML
    void onExit(ActionEvent event) {
        Stage stage1 = (Stage) btnExit.getScene().getWindow();
        stage1.close();
    }

    @FXML
    void onSave(ActionEvent event) {
        if(!tfOldPass.getText().equals(profile.getPass())){
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Ошибка");
            alert.setHeaderText("Неправильный старый пароль");
            alert.setContentText("Введите правильно старый пароль");

            alert.showAndWait();

        }else if (!tfNewPass.getText().equals(tfRepeatNewPass.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Ошибка");
            alert.setHeaderText("Не совпадают введённые пароли");
            alert.setContentText("Введите другой пароль или правильно повторите новый");

            alert.showAndWait();
        } else {
            profile.setPass(tfNewPass.getText());
            System.out.println(profile.getPass());
            try {
                Connector connector = new Connector();
                connector.changeUser(profile);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Изменение");
                alert.setHeaderText("Ваш пароль был изменён");
                alert.showAndWait();
                System.out.println(profile.getPass());
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
            Stage stage1 = (Stage) btnSave.getScene().getWindow();
            stage1.close();
        }
    }

    @FXML
    void initialize() {
        assert tfNewPass != null : "fx:id=\"tfNewPass\" was not injected: check your FXML file 'changePass.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'changePass.fxml'.";
        assert tfRepeatNewPass != null : "fx:id=\"tfRepeatNewPass\" was not injected: check your FXML file 'changePass.fxml'.";
        assert nameItem != null : "fx:id=\"nameItem\" was not injected: check your FXML file 'changePass.fxml'.";
        assert tfOldPass != null : "fx:id=\"tfOldPass\" was not injected: check your FXML file 'changePass.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'changePass.fxml'.";

    }

    User profile;

    public void initData(User chProfile) {
        profile = chProfile;
    }
}

