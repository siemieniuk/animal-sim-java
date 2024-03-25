package siemieniuk.animals.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import siemieniuk.animals.core.animals.AnimalRepository;
import siemieniuk.animals.core.animals.Predator;
import siemieniuk.animals.core.animals.Prey;
import siemieniuk.animals.core.locations.LocationRepository;

public class ParametrizedAnimalCreationController {
    @FXML private ToggleGroup animalGroup;
    @FXML private TextField nameInput;
    @FXML private TextField speciesInput;
    @FXML private TextField healthInput;
    @FXML private TextField speedInput;
    @FXML private TextField strengthInput;

    private AnimalRepository animalRepository;
    private LocationRepository locationRepository;
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
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                field.setText(newValue.replaceAll("\\D", ""));
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
        Prey p = new Prey(animalRepository, locationRepository, name, health, speed, strength, species, 3, 3);
        p.setPos(locationRepository.getRandomPathLocation());
        animalRepository.push(p);
    }

    private void createPredator() {
        Predator p = new Predator(animalRepository, locationRepository, name, health, speed, strength, species);
        p.setPos(locationRepository.getRandomValidPosition());
        animalRepository.push(p);
    }

    private void fetchAttributes() throws NumberFormatException {
        name = nameInput.getCharacters().toString();
        species = speciesInput.getCharacters().toString();
        health = Integer.parseInt(healthInput.getCharacters().toString());
        speed = Integer.parseInt(speedInput.getCharacters().toString());
        strength = Integer.parseInt(strengthInput.getCharacters().toString());
    }

    public void setRepositories(AnimalRepository animalRepository, LocationRepository locationRepository) {
        this.animalRepository = animalRepository;
        this.locationRepository = locationRepository;
    }
}
