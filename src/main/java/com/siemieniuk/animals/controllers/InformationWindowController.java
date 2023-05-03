package com.siemieniuk.animals.controllers;

import com.siemieniuk.animals.core.DetailsPrintable;
import com.siemieniuk.animals.images.ImageLoader;
import com.siemieniuk.animals.core.typing.WorldObjectType;
import com.siemieniuk.animals.core.animals.Animal;
import com.siemieniuk.animals.core.locations.Hideout;
import com.siemieniuk.animals.core.locations.Intersection;
import com.siemieniuk.animals.core.locations.Location;
import com.siemieniuk.animals.core.locations.Source;
import com.siemieniuk.animals.gui.AnimalView;
import com.siemieniuk.animals.gui.AnimalViewFactory;
import com.siemieniuk.animals.math.Coordinates;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Iterator;
import java.util.List;

/**
 * This class is a controller for information window. Displays all animals and locations at a specified point.
 * @author Szymon Siemieniuk
 */
public class InformationWindowController {
    @FXML private Pane animalsVB;
    @FXML private ImageView icon;
    @FXML private Text title;
    @FXML private Text position;
    @FXML private Text capacity;

    public void changeLocation(Location loc) {
        Image img = ImageLoader.getImage(loc.getMetadataCode());
        icon.setImage(img);
        Coordinates pos = loc.getPos();
        String displayPosition = "X=" + pos.getX() + ", Y=" + pos.getY();
        position.setText(displayPosition);
        if (loc instanceof Source) {
            title.setText(((Source) loc).getName());
            capacity.setText(((Source) loc).getUsageString());
        } else {
            title.setText(loc.toString());
        }

        if (loc instanceof Intersection intersection) {
            if (intersection.isOccupied()) {
                capacity.setText("Occupied");
            } else {
                capacity.setText("Busy");
            }
        }

        if (loc instanceof Hideout hideout) {
            capacity.setText(hideout.getUsageString());
        }
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
