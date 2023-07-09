package siemieniuk.animals.controllers;

import siemieniuk.animals.core.World;
import siemieniuk.animals.gui.AnimalView;
import siemieniuk.animals.core.animals.Animal;
import siemieniuk.animals.core.typing.AnimalObservable;
import siemieniuk.animals.images.ImageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public abstract class AnimalController implements AnimalObservable {
    private final Animal observedAnimal;
    private final AnimalView observedView;
    @FXML private ProgressBar healthBar;
    @FXML private Text healthLabel;
    @FXML private Text name;
    @FXML private Text species;
    @FXML private Text stats;
    @FXML private ImageView icon;
    @FXML private Button deleteAnimalBtn;

    public AnimalController(Animal animal, AnimalView view) {
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
        if (observedAnimal != null) {
            World.getInstance().removeAnimal(observedAnimal);
        }
        ((Pane)observedView.getParent()).getChildren().remove(observedView);
    }
}
