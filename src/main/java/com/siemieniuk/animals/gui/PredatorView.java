package com.siemieniuk.animals.gui;

import com.siemieniuk.animals.MainApplication;
import com.siemieniuk.animals.controllers.PredatorController;
import com.siemieniuk.animals.core.animals.Predator;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Objects;

public class PredatorView extends AnimalView {
    PredatorView(Predator predator) {
        super();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(MainApplication.class.getResource("scenes/Predator.fxml")));
            fxmlLoader.setRoot(this);
            PredatorController animalController = new PredatorController(predator, this);
            fxmlLoader.setController(animalController);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}