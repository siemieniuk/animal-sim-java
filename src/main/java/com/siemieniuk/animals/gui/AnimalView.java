package com.siemieniuk.animals.gui;

import javafx.scene.layout.GridPane;

/**
 * This class is animal custom JavaFX component. It wraps animal into the GridPane
 * @author  Szymon Siemieniuk
 */
public abstract class AnimalView extends GridPane {
    private boolean wasDeleteClicked = false;

    AnimalView() {
        getStyleClass().clear();
        getStyleClass().add("animal-view");
    }
}
