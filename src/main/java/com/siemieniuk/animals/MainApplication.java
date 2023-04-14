package com.siemieniuk.animals;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
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

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

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