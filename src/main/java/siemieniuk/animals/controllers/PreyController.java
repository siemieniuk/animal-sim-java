package siemieniuk.animals.controllers;

import siemieniuk.animals.core.animals.AnimalRepository;
import siemieniuk.animals.core.animals.Prey;
import siemieniuk.animals.core.locations.LocationRepository;
import siemieniuk.animals.core.typing.WorldObjectType;
import siemieniuk.animals.gui.PreyView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.util.Duration;

public final class PreyController extends AnimalController {
    @FXML private ProgressBar foodBar;
    @FXML private Text foodLabel;
    @FXML private ProgressBar waterBar;
    @FXML private Text waterLabel;

    public PreyController(AnimalRepository animalRepository, Prey animal, PreyView view) {
        super(animalRepository, animal, view);
    }

    @FXML
    public void initialize() {
        super.initialize();
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1.0), e -> update())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public void update() {
        super.update();
        foodBar.setProgress(((Prey)getAnimal()).getFoodRatio());
        foodLabel.setText(((Prey)getAnimal()).getFoodDetails());
        waterBar.setProgress(((Prey)getAnimal()).getWaterRatio());
        waterLabel.setText(((Prey)getAnimal()).getWaterDetails());
    }

    @FXML
    public void sendToWaterSource() {
        try {
            ((Prey)getAnimal()).setNewTarget(WorldObjectType.WATER_SRC);
        } catch (InterruptedException e) {
            System.out.println("ERROR!");
            getAnimal().kill();
        }
    }

    @FXML
    public void sendToPlantSource() {
        try {
            ((Prey)getAnimal()).setNewTarget(WorldObjectType.PLANT_SRC);
        } catch (InterruptedException e) {
            getAnimal().kill();
            System.out.println("ERROR!");
        }
    }
}
