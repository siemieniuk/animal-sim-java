package siemieniuk.animals.controllers;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import siemieniuk.animals.MainGUI;
import siemieniuk.animals.core.WorldRunnable;
import siemieniuk.animals.core.animals.Animal;
import siemieniuk.animals.core.animals.AnimalRepository;
import siemieniuk.animals.core.locations.Location;
import siemieniuk.animals.core.locations.LocationRepository;
import siemieniuk.animals.core.randanimal.RandomAnimalAppender;
import siemieniuk.animals.core.world_creation.WorldBuilder;
import siemieniuk.animals.gui.WorldView;
import siemieniuk.animals.images.ImageLoader;
import siemieniuk.animals.math.Coordinates;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents the controller of the main window.
 * @author  Szymon Siemieniuk
 */
public final class MainController {
    @FXML private WorldView canvas;
    @FXML private ImageView bigLogo;
    @FXML private VBox sidebar;
    @FXML private ParametrizedAnimalCreationController pacController;
    private AnimalRepository animalRepository;
    private LocationRepository locationRepository;

    /**
     * Default constructor; loads default <em>release.hobhw</em> file as a base for the world
     */
    public MainController() {
        try {
            URL path = MainGUI.class.getResource("conf/release.hobhw");
            this.locationRepository = WorldBuilder.create(path);
            this.animalRepository = new AnimalRepository();

            ImageLoader.init();

            WorldRunnable worldRunnable = new WorldRunnable(animalRepository);
            Thread threadWorld = new Thread(worldRunnable);
            threadWorld.setName("World");
            threadWorld.start();

            URL pathToPreys = MainGUI.class.getResource("conf/preys.txt");
            URL pathToPredators = MainGUI.class.getResource("conf/predators.txt");
            URL pathToNames = MainGUI.class.getResource("conf/names.txt");
            RandomAnimalAppender.loadConfig(pathToPreys, pathToPredators, pathToNames);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Platform.exit();
            System.exit(0);
        }
    }

    @FXML
    private void initialize() {
        String path = Objects.requireNonNull(MainGUI.class.getResource("images/logo_big.png")).toExternalForm();
        Image img = new Image(path, 100.0, 100.0, true, false);
        bigLogo.setImage(img);

        AnimationTimer at = new AnimationTimer() {
            @Override
            public void handle(long now) {
                canvas.redraw();
            }
        };
        at.start();
        pacController.setRepositories(animalRepository, locationRepository);

        canvas.setRepositories(animalRepository, locationRepository);
    }

    @FXML
    private void createRandomPrey() {
        RandomAnimalAppender.newPrey(animalRepository, locationRepository);
    }

    @FXML
    private void createRandomPredator() {
        RandomAnimalAppender.newPredator(animalRepository, locationRepository);
    }

    @FXML
    private void showInformationWindow(MouseEvent event) {
        try {
            Coordinates pos = new Coordinates(WorldView.convertToWorldPos(event.getX(), event.getY()));

            List<Animal> animalsToShow = animalRepository.getAnimalsAt(pos);
            List<Object> objectsToPrint = new ArrayList<>();
            Location locationToPrint = locationRepository.getLocation(pos);
            if (locationToPrint != null) {
                objectsToPrint.add(locationRepository.getLocation(pos));
            }
            objectsToPrint.addAll(animalsToShow);

            URL url = MainGUI.class.getResource("scenes/InformationWindow.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            String css = Objects.requireNonNull(MainGUI.class.getResource("style/InformationWindow.css"))
                    .toExternalForm();
            scene.getStylesheets().add(css);

            InformationWindowController ic = fxmlLoader.getController();
            ic.setAnimalRepository(animalRepository);
            if (locationToPrint != null) {
                ic.changeLocation(locationToPrint);
            } else {
                ic.configureNoLocation(pos);
            }
            ic.changeDisplay(objectsToPrint);

            Stage stage = new Stage();
            stage.setTitle("Details");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
            stage.setMinHeight(stage.getHeight());
            stage.setMaxHeight(stage.getHeight());
            stage.setMinWidth(240.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}