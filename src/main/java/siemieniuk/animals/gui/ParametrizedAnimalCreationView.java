package siemieniuk.animals.gui;

import siemieniuk.animals.MainGUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ParametrizedAnimalCreationView extends GridPane {
    public ParametrizedAnimalCreationView() {
        try {
            URL path = Objects.requireNonNull(MainGUI.class.getResource("scenes/ParametrizedAnimalCreation.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(path);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
