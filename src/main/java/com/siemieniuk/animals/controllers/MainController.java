package com.siemieniuk.animals.controllers;

import com.siemieniuk.animals.*;
import com.siemieniuk.animals.math.Coordinates;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class represents the main window of the application.
 * @author  Szymon Siemieniuk
 */
public class MainController implements Initializable {
    @FXML private Canvas canvas;
    private World world = null;
    private static WorldCanvasPainter wcp;

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

    /**
     * Launches during start
     * @param src A source
     * @param rsb A resource bundle
     */
    @Override
    public void initialize(URL src, ResourceBundle rsb) {
        wcp = new WorldCanvasPainter(canvas);
        AnimationTimer at = new AnimationTimer() {
            @Override
            public void handle(long now) {
                wcp.updateCanvas();
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
            Coordinates pos = new Coordinates(WorldCanvasPainter.convertToWorldPos(event.getX(), event.getY()));
            List<DetailsPrintable> objectsToPrint = world.getObjectsToDraw(pos);
            if (objectsToPrint.size() > 0) {
                URL url = MainApplication.class.getResource("information-window.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader(url);
                Parent root = fxmlLoader.load();
                InformationController ic = fxmlLoader.getController();
                ic.changeDisplay(objectsToPrint);
                Stage stage = new Stage();
                stage.setTitle("Information window");
                stage.setScene(new Scene(root));
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}