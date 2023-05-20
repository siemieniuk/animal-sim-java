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
public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setOnCloseRequest((evt) -> {
            Platform.exit();
            System.exit(0);
        });

        final FXMLLoader parentLoader = new FXMLLoader();

        Parent root;
//        try (InputStream sceneStream = MainApplication.class.getResourceAsStream("scenes/MainView.fxml")) {
        try (InputStream sceneStream = MainApplication.class.getResourceAsStream("scenes/MainViewNew.fxml")) {
            root = parentLoader.load(sceneStream);
        }

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