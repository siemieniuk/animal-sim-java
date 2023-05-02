package com.siemieniuk.animals.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

public class AnimalController {
    @FXML private ProgressBar healthBar;
    @FXML private ProgressBar foodBar;
    @FXML private ProgressBar waterBar;
    @FXML private Text name;
    @FXML private Text species;

    public AnimalController() {
        
    }

    @FXML
    public void initialize() {

    }
}
