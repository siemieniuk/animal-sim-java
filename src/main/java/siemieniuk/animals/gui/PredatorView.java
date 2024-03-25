package siemieniuk.animals.gui;

import javafx.fxml.FXMLLoader;
import siemieniuk.animals.MainGUI;
import siemieniuk.animals.controllers.PredatorController;
import siemieniuk.animals.core.animals.AnimalRepository;
import siemieniuk.animals.core.animals.Predator;

import java.io.IOException;
import java.util.Objects;

public class PredatorView extends AnimalView {
    PredatorView(AnimalRepository animalRepository, Predator predator) {
        super();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(MainGUI.class.getResource("scenes/Predator.fxml")));
            fxmlLoader.setRoot(this);
            PredatorController animalController = new PredatorController(animalRepository, predator, this);
            fxmlLoader.setController(animalController);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}