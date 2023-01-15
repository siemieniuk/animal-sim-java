package com.siemieniuk.animals;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML private Button dummyBtn;
    @FXML private Button predatorCreateBtn;
    @FXML private Button preyCreateBtn;
    @FXML private Canvas canvas;
    private World world = null;

    private static WorldCanvasPainter wcp;

    public MainController() {
        try {
            String path = new File("").getAbsolutePath();
            path = path.concat("/conf/world/simple.hobhw");
            world = WorldBuilder.create(path);
            Thread threadWorld = new Thread(world);
            threadWorld.start();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Platform.exit();
            System.exit(0);
        }
//        world = WorldBuilder.create(10, 5, 20, 20);
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
    protected void createPrey(ActionEvent event) {
        Prey p = new Prey("aa", 23, 1, 2, "vx", null, 2, 3);
        world.createAnimal(p);
        System.out.println("Prey created!");
    }

    /* TODO: Random Predator */
    @FXML
    protected void createPredator(ActionEvent event) {
        Predator p = new Predator("a", 12, 2, 0, "ab", null);
        world.createAnimal(p);
        System.out.println("Predator created!");
    }

    public static void updateCanvas() {
        wcp.updateCanvas();
    }

    @FXML
    protected void drawMap(ActionEvent event) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

    }

    @FXML
    protected void deleteAnimal(ActionEvent event) {
        List<Animal> anims = world.getAnimals();
        if (anims.size() != 0) {
            world.removeAnimal(anims.get(0).getId());
        }
    }

    @FXML
    protected void showInformationWindow(MouseEvent event) {
        try {
            //NEW
            Coordinates pos = new Coordinates(WorldCanvasPainter.convertToWorldPos(event.getX(), event.getY()));
            List<DetailsPrintable> objectsToPrint = world.getObjectsToDraw(pos);
            if (objectsToPrint.size() > 0) {
                System.out.println("Size: " + objectsToPrint.size());
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("information-window.fxml"));
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