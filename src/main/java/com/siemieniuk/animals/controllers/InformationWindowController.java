package com.siemieniuk.animals.controllers;

import com.siemieniuk.animals.core.DetailsPrintable;
import com.siemieniuk.animals.gui.AnimalView;
import com.siemieniuk.animals.math.Coordinates;
import com.siemieniuk.animals.core.locations.*;
import com.siemieniuk.animals.core.typing.LocationVisitor;
import com.siemieniuk.animals.images.ImageLoader;
import com.siemieniuk.animals.core.typing.WorldObjectType;
import com.siemieniuk.animals.core.animals.Animal;
import com.siemieniuk.animals.gui.AnimalViewFactory;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.Iterator;
import java.util.List;

/**
 * This class is a controller for information window. Displays all animals and locations at a specified point.
 * @author Szymon Siemieniuk
 */
public final class InformationWindowController implements LocationVisitor {
    @FXML private Pane animalsVB;
    @FXML private ImageView icon;
    @FXML private Text title;
    @FXML private Text position;
    @FXML private Text capacity;

    public void changeLocation(Location location) {
        location.accept(this);
    }

    @Override
    public void visitWaterSource(WaterSource waterSource) {
        visitSource(waterSource);

        Image img = ImageLoader.getImage(WorldObjectType.WATER_SRC);
        icon.setImage(img);
    }

    @Override
    public void visitPlantSource(PlantSource plantSource) {
        visitSource(plantSource);

        Image img = ImageLoader.getImage(WorldObjectType.PLANT_SRC);
        icon.setImage(img);
    }

    private void visitSource(Source source) {
        getPositionFromLocation(source);
        title.setText(source.getName());
        String usage = "Usage: " + source.getHowManyPreysNow() + "/" + source.getCapacity();
        capacity.setText(usage);
    }

    @Override
    public void visitHideout(Hideout hideout) {
        getPositionFromLocation(hideout);
        title.setText(hideout.toString());

        String usage = "Usage: " + hideout.getHowManyPreysNow() + "/" + hideout.getCapacity();
        capacity.setText(usage);

        Image img = ImageLoader.getImage(WorldObjectType.HIDEOUT);
        icon.setImage(img);
    }

    @Override
    public void visitPath(Path path) {
        getPositionFromLocation(path);
        title.setText(path.toString());

        Image img = ImageLoader.getImage(WorldObjectType.PATH);
        icon.setImage(img);
    }

    @Override
    public void visitIntersection(Intersection intersection) {
        getPositionFromLocation(intersection);
        title.setText(intersection.toString());

        if (intersection.isOccupied()) {
            capacity.setText("Occupied");
        } else {
            capacity.setText("Busy");
        }

        Image img = ImageLoader.getImage(WorldObjectType.INTERSECTION);
        icon.setImage(img);
    }

    private void getPositionFromLocation(Location location) {
        Coordinates pos = location.getPos();
        String displayPosition = "X=" + pos.getX() + ", Y=" + pos.getY();
        position.setText(displayPosition);
    }

    public void configureNoLocation(Coordinates pos) {
        Image img = ImageLoader.getImage(WorldObjectType.GRASS);
        icon.setImage(img);
        title.setText("Grass");
        String displayPosition = "X=" + pos.getX() + ", Y=" + pos.getY();
        position.setText(displayPosition);
    }

    /**
     * This method is used for changing content of window.
     * @param   dpList the list of printable objects.
     */
    public void changeDisplay(List<DetailsPrintable> dpList) {
        assert dpList.size() != 0;
        Iterator<DetailsPrintable> it = dpList.iterator();
        DetailsPrintable currentObj = it.next();
        if (currentObj != null) {
            if (currentObj instanceof Animal animal) {
                AnimalView view = AnimalViewFactory.getAnimalView(animal);
                animalsVB.getChildren().add(view);
            }
        }
        while (it.hasNext()) {
            currentObj = it.next();
            AnimalView view = AnimalViewFactory.getAnimalView((Animal) currentObj);
            animalsVB.getChildren().add(view);
        }
    }
}
