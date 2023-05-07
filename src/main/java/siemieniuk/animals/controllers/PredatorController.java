package siemieniuk.animals.controllers;

import siemieniuk.animals.core.animals.Predator;
import siemieniuk.animals.gui.PredatorView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.util.Duration;

public final class PredatorController extends AnimalController {
    @FXML private Text currentMode;

    public PredatorController(Predator predator, PredatorView view) {
        super(predator, view);
    }

    @FXML
    public void initialize() {
        super.initialize();
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1.0), e -> update())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        update();
    }

    @Override
    public void update() {
        super.update();
        currentMode.setText(((Predator) getAnimal()).getCurrentMode().toString());
    }
}
