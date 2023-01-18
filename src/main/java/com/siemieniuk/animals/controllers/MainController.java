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
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML private Canvas canvas;
    private World world = null;
    private static WorldCanvasPainter wcp;

    public MainController() {
        try {
            URL path = MainApplication.class.getResource("conf/release.hobhw");
            world = WorldBuilder.create(path);
            Thread threadWorld = new Thread(world);
            threadWorld.start();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Platform.exit();
            System.exit(0);
        }
    }

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

    /* TODO: Random prey */
    @FXML
    protected void createPrey() {
        Prey p = new Prey("Melman", 100, 1, 4, "Giraffe", 3, 5);
        world.createAnimal(p);
        System.out.println("Prey created!");
    }

    /* TODO: Random Predator */
    @FXML
    protected void createPredator() {
        Predator p = new Predator("Makumba", 100, 2, 13, "Lion");
        world.createAnimal(p);
        System.out.println("Predator created!");
    }

    @FXML
    protected void deleteAnimal() {
        List<Animal> animals = world.getAnimals();
        if (animals.size() != 0) {
            world.removeAnimal(animals.get(0).getId());
        }
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