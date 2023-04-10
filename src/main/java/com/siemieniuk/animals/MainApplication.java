package com.siemieniuk.animals;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
        stage.setTitle("Hunt Or Be Hunted?");
        stage.setMinHeight(420);
        stage.setMinWidth(620);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}