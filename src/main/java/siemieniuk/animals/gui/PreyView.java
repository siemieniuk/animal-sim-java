package siemieniuk.animals.gui;

import javafx.fxml.FXMLLoader;
import siemieniuk.animals.MainGUI;
import siemieniuk.animals.controllers.PreyController;
import siemieniuk.animals.core.animals.AnimalRepository;
import siemieniuk.animals.core.animals.Prey;

import java.io.IOException;
import java.util.Objects;

public class PreyView extends AnimalView {
    PreyView(AnimalRepository animalRepository, Prey prey) {
        super();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(MainGUI.class.getResource("scenes/Prey.fxml")));
            fxmlLoader.setRoot(this);
            PreyController animalController = new PreyController(animalRepository, prey, this);
            fxmlLoader.setController(animalController);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}