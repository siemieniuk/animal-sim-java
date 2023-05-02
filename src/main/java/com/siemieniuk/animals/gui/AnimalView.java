package com.siemieniuk.animals.gui;

import com.siemieniuk.animals.MainApplication;
import com.siemieniuk.animals.core.typing.WorldObjectType;
import com.siemieniuk.animals.core.animals.Animal;
import com.siemieniuk.animals.core.animals.Prey;
import com.siemieniuk.animals.core.World;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;

/**
 * This class is animal custom JavaFX component. It wraps animal into the GridPane
 * @author  Szymon Siemieniuk
 */
public class AnimalView extends GridPane {
    private boolean wasDeleteClicked = false;

    /**
     * Constructor
     * @param animal The animal to wrap
     */
    public AnimalView(Animal animal) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(MainApplication.class.getResource("scenes/Animal.fxml")));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
