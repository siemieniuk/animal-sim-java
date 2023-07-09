package siemieniuk.animals.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import siemieniuk.animals.core.World;
import siemieniuk.animals.core.animals.Predator;
import siemieniuk.animals.core.animals.Prey;

public class ParametrizedAnimalCreationController {
    @FXML private ToggleGroup animalGroup;
    @FXML private TextField nameInput;
    @FXML private TextField speciesInput;
    @FXML private TextField healthInput;
    @FXML private TextField speedInput;
    @FXML private TextField strengthInput;

    private String name;
    private String species;
    private int health;
    private int speed;
    private int strength;

    @FXML
    public void initialize() {
        addNumericValidation(healthInput);
        addNumericValidation(speedInput);
        addNumericValidation(strengthInput);
    }

    private void addNumericValidation(TextField field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    field.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @FXML
    public void createAnimal() {
        try {
            fetchAttributes();
        } catch (NumberFormatException ignored) {}

        String selected = ((RadioButton) animalGroup.getSelectedToggle()).getText();
        switch (selected) {
            case "Predator" -> createPredator();
            case "Prey" -> createPrey();
            default -> {}
        }
    }

    private void createPrey() {
        Prey p = new Prey(name, health, speed, strength, species, 3, 3);
        World.getInstance().createAnimal(p);
    }

    private void createPredator() {
        Predator p = new Predator(name, health, speed, strength, species);
        World.getInstance().createAnimal(p);
    }

    private void fetchAttributes() throws NumberFormatException {
        name = nameInput.getCharacters().toString();
        species = speciesInput.getCharacters().toString();
        health = Integer.parseInt(healthInput.getCharacters().toString());
        speed = Integer.parseInt(speedInput.getCharacters().toString());
        strength = Integer.parseInt(strengthInput.getCharacters().toString());
    }
}
