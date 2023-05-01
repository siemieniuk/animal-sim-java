package com.siemieniuk.animals.controllers;

import com.siemieniuk.animals.*;
import com.siemieniuk.animals.core.DetailsPrintable;
import com.siemieniuk.animals.core.world_creation.ImageLoader;
import com.siemieniuk.animals.core.typing.WorldObjectType;
import com.siemieniuk.animals.core.locations.Location;
import com.siemieniuk.animals.core.animals.Predator;
import com.siemieniuk.animals.core.animals.Prey;
import com.siemieniuk.animals.core.World;
import com.siemieniuk.animals.gui.WorldView;
import com.siemieniuk.animals.math.Coordinates;
import com.siemieniuk.animals.core.world_creation.WorldBuilder;
import com.siemieniuk.animals.math.Pair;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents the main window of the application.
 * @author  Szymon Siemieniuk
 */
public class MainController {
    @FXML private WorldView canvas;
    private World world = null;

    /**
     * Default constructor; loads default <em>release.hobhw</em> file as a base for the world
     */
    public MainController() {
        try {
            URL path = MainApplication.class.getResource("conf/release.hobhw");
            world = WorldBuilder.create(path);
            Thread threadWorld = new Thread(world);
            threadWorld.start();
            loadImages();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Platform.exit();
            System.exit(0);
        }
    }

    @FXML
    private void initialize() {
        AnimationTimer at = new AnimationTimer() {
            @Override
            public void handle(long now) {
                canvas.redraw();
            }
        };
        at.start();
    }

    @FXML
    protected void createPrey() {
        Prey p = new Prey("Affe", 100, 1, 4, "Giraffe", 3, 5);
        world.createAnimal(p);
    }

    @FXML
    protected void createPredator() {
        Predator p = new Predator("Simba", 100, 2, 13, "Lion");
        world.createAnimal(p);
    }

    protected void loadImages() {
        String[] paths = new String[] {
                "images/grass.png",
                "images/hideout.png",
                "images/intersection.png",
                "images/path.png",
                "images/water.png",
                "images/plant.png"
        };

        WorldObjectType[] types = new WorldObjectType[] {
                WorldObjectType.GRASS,
                WorldObjectType.HIDEOUT,
                WorldObjectType.INTERSECTION,
                WorldObjectType.PATH,
                WorldObjectType.WATER_SRC,
                WorldObjectType.PLANT_SRC
        };

        List<Pair<WorldObjectType, String>> entriesToLoad = new ArrayList<>();

        for (int i=0; i<paths.length; i++) {
            String path = Objects.requireNonNull(MainApplication.class.getResource(paths[i])).toString();
            entriesToLoad.add(new Pair<>(types[i], path));
        }
        ImageLoader.init(entriesToLoad);
    }

    @FXML
    protected void showInformationWindow(MouseEvent event) {
        try {
            Coordinates pos = new Coordinates(WorldView.convertToWorldPos(event.getX(), event.getY()));
            List<DetailsPrintable> objectsToPrint = world.getObjectsToDraw(pos);
            Location loc = world.getLocation(pos);
            if (objectsToPrint.size() > 0) {
                URL url = MainApplication.class.getResource("scenes/InformationWindow.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader(url);
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                String css = Objects.requireNonNull(MainApplication.class.getResource("style/InformationWindow.css"))
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
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO: Remove
    @FXML protected void dummy() {
        System.out.println(Thread.getAllStackTraces().keySet());
    }
}