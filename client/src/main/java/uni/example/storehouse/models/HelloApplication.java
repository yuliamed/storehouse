package uni.example.storehouse.models;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    public static final String ICON_IMAGE_LOC = "src/main/resources/icons/cat.png";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/uni/example/storehouse/entrance.fxml"));
        File fileIcon = new File(ICON_IMAGE_LOC);
        Image applicationIcon = new Image(fileIcon.toURI().toString());
        stage.getIcons().add(applicationIcon);

        Scene scene = new Scene(fxmlLoader.load(), 670, 490);
        stage.setTitle("<- Cat");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}