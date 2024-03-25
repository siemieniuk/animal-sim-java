package siemieniuk.animals.gui;

import javafx.scene.layout.GridPane;

/**
 * This class is animal custom JavaFX component. It wraps animal into the GridPane
 * @author  Szymon Siemieniuk
 */
public abstract class AnimalView extends GridPane {
    AnimalView() {
        getStyleClass().clear();
        getStyleClass().add("animal-view");
    }
}
