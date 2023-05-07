package com.siemieniuk.animals.gui;

import com.siemieniuk.animals.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ParametrizedAnimalCreationView extends GridPane {
    public ParametrizedAnimalCreationView() {
        super();
        try {
            URL path = Objects.requireNonNull(MainApplication.class.getResource("scenes/ParametrizedAnimalCreation.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(path);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
