package siemieniuk.animals.controllers;

import siemieniuk.animals.core.World;
import siemieniuk.animals.core.randanimal.RandomAnimalAppender;
import siemieniuk.animals.core.world_creation.WorldBuilder;
import siemieniuk.animals.gui.ParametrizedAnimalCreationView;
import siemieniuk.animals.math.Coordinates;
import siemieniuk.animals.core.locations.Location;
import siemieniuk.animals.images.ImageLoader;
import siemieniuk.animals.gui.WorldView;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * This class represents the controller of the main window.
 * @author  Szymon Siemieniuk
 */
public final class MainController {
    @FXML private WorldView canvas;
    @FXML private ImageView bigLogo;
    @FXML private VBox sidebar;
    private World world = null;

    /**
     * Default constructor; loads default <em>release.hobhw</em> file as a base for the world
     */
    public MainController() {
        try {
            URL path = MainGUI.class.getResource("conf/release.hobhw");
            world = WorldBuilder.create(path);
            ImageLoader.init();
            Thread threadWorld = new Thread(world);
            threadWorld.setName("World");
            threadWorld.start();
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
    }

    @FXML
    private void createRandomPrey() {
        RandomAnimalAppender.newPrey();
    }

    @FXML
    private void createRandomPredator() {
        RandomAnimalAppender.newPredator();
    }

    @FXML
    private void showInformationWindow(MouseEvent event) {
        try {
            Coordinates pos = new Coordinates(WorldView.convertToWorldPos(event.getX(), event.getY()));
            List<Object> objectsToPrint = world.getObjectsAt(pos);
            Location loc = world.getLocation(pos);

            URL url = MainGUI.class.getResource("scenes/InformationWindow.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            String css = Objects.requireNonNull(MainGUI.class.getResource("style/InformationWindow.css"))
                    .toExternalForm();
            scene.getStylesheets().add(css);

            InformationWindowController ic = fxmlLoader.getController();
            if (loc != null) {
                ic.changeLocation(loc);
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