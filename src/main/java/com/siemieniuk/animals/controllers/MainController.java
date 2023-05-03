package com.siemieniuk.animals.controllers;

import com.siemieniuk.animals.*;
import com.siemieniuk.animals.core.DetailsPrintable;
import com.siemieniuk.animals.core.locations.Location;
import com.siemieniuk.animals.core.animals.Predator;
import com.siemieniuk.animals.core.animals.Prey;
import com.siemieniuk.animals.core.World;
import com.siemieniuk.animals.images.ImageLoader;
import com.siemieniuk.animals.gui.WorldView;
import com.siemieniuk.animals.math.Coordinates;
import com.siemieniuk.animals.core.world_creation.WorldBuilder;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * This class represents the controller of the main window.
 * @author  Szymon Siemieniuk
 */
public class MainController {
    @FXML private WorldView canvas;
    @FXML private ImageView bigLogo;
    private World world = null;

    /**
     * Default constructor; loads default <em>release.hobhw</em> file as a base for the world
     */
    public MainController() {
        try {
            URL path = MainApplication.class.getResource("conf/release.hobhw");
            world = WorldBuilder.create(path);
            ImageLoader.init();
            Thread threadWorld = new Thread(world);
            threadWorld.start();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Platform.exit();
            System.exit(0);
        }
    }

    @FXML
    private void initialize() {
        String path = Objects.requireNonNull(MainApplication.class.getResource("images/logo_big.png")).toExternalForm();
        Image img = new Image(path, 100.0, 100.0, true, false);
        bigLogo.setImage(img);
        System.out.println(bigLogo.getParent().getScaleX());

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
        Prey p = new Prey("Affe", 100, 1, 4, "Giraffe", 2, 3);
        world.createAnimal(p);
    }

    @FXML
    protected void createPredator() {
        Predator p = new Predator("Simba", 100, 2, 13, "Lion");
        world.createAnimal(p);
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
                stage.sizeToScene();
                stage.show();
                stage.setMinHeight(stage.getHeight());
                stage.setMaxHeight(stage.getHeight());
                stage.setMinWidth(240.0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}