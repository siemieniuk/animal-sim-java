package siemieniuk.animals.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import siemieniuk.animals.core.animals.Animal;
import siemieniuk.animals.core.animals.AnimalRepository;
import siemieniuk.animals.core.typing.AnimalObservable;
import siemieniuk.animals.gui.AnimalView;
import siemieniuk.animals.images.ImageLoader;

public abstract class AnimalController implements AnimalObservable {
    private final Animal observedAnimal;
    private final AnimalView observedView;
    private final AnimalRepository animalRepository;
    @FXML private ProgressBar healthBar;
    @FXML private Text healthLabel;
    @FXML private Text name;
    @FXML private Text species;
    @FXML private Text stats;
    @FXML private ImageView icon;
    @FXML private Button deleteAnimalBtn;

    public AnimalController(AnimalRepository animalRepository, Animal animal, AnimalView view) {
        this.animalRepository = animalRepository;
        this.observedAnimal = animal;
        this.observedView = view;
    }

    @FXML
    public void initialize() {
        name.setText(observedAnimal.getName());
        species.setText(observedAnimal.getSpecies());
        icon.setImage(ImageLoader.getImage(observedAnimal.getMetadataCode()));
        update();
        stats.setText(String.format("⚔ %.0f | \uD83D\uDDF2 %.0f",
                                    observedAnimal.getStrength(), observedAnimal.getSpeed()));
    }

    @Override
    public void update() {
        if (observedAnimal != null) {
            healthBar.setProgress(observedAnimal.getHealthRatio());
            healthLabel.setText(observedAnimal.getHealthDetails());

        } else {
            ((Pane)observedView.getParent()).getChildren().remove(observedView);
        }
    }

    protected Animal getAnimal() {
        return observedAnimal;
    }

    @FXML
    public void deleteAnimal() {
        System.out.println("Empty animalRepository: " + animalRepository == null);
        if (observedAnimal != null) {
            animalRepository.remove(observedAnimal);
        }
        ((Pane)observedView.getParent()).getChildren().remove(observedView);
    }
}
