package siemieniuk.animals;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * This class contains the main body of JavaFX application
 * @author Szymon Siemieniuk
 */
public class MainGUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setOnCloseRequest((evt) -> {
            Platform.exit();
            System.exit(0);
        });

        final FXMLLoader parentLoader = new FXMLLoader();
        parentLoader.setLocation(getClass().getResource("scenes/MainViewNew.fxml"));

        Parent root = parentLoader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(0, Objects.requireNonNull(getClass().getResource("style/MainView.css")).toExternalForm());

        stage.setTitle("Hunt Or Be Hunted?");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());
    }
    public static void main(String[] args) {
        launch();
    }
}