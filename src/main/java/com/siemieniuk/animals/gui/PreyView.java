package com.siemieniuk.animals.gui;

import com.siemieniuk.animals.MainApplication;
import com.siemieniuk.animals.controllers.PreyController;
import com.siemieniuk.animals.core.animals.Prey;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Objects;

public class PreyView extends AnimalView {
    PreyView(Prey prey) {
        super();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(MainApplication.class.getResource("scenes/Prey.fxml")));
            fxmlLoader.setRoot(this);
            PreyController animalController = new PreyController(prey, this);
            fxmlLoader.setController(animalController);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}