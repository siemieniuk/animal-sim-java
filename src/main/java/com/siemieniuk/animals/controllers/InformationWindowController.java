package com.siemieniuk.animals.controllers;

import com.siemieniuk.animals.*;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Iterator;
import java.util.List;

/**
 * This class is a controller for information window. Displays all animals and locations at a specified point.
 * @author Szymon Siemieniuk
 */
public class InformationController {
    @FXML private VBox animalsVB;
    @FXML private ImageView icon;
    @FXML private Text title;
    @FXML private Text position;
    @FXML private Text capacity;

//    public void changeLocation(Source src) {
//        capacity.setText(src.getUsageString());
//        fillRemainingLocationDetails(src);
//    }
//
//    public void changeLocation(Intersection intersection) {
//        if (intersection.isOccupied()) {
//            capacity.setText("Occupied");
//        } else {
//            capacity.setText("Free");
//        }
//        fillRemainingLocationDetails(intersection);
//    }
//
//    public void changeLocation(Hideout hideout) {
//        capacity.setText(hideout.getUsageString());
//        fillRemainingLocationDetails(hideout);
//    }

    public void changeLocation(Location loc) {
        Image img = ImageLoader.getImage(loc.getMetadataCode());
        icon.setImage(img);
        position.setText(loc.getPos().toString());
        title.setText("Hello world!");
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
            if (currentObj instanceof Location) {
                animalsVB.getChildren().add(new Text(currentObj.getDetails()));
            } else {
                animalsVB.getChildren().add(new AnimalComponent((Animal) currentObj));
            }
        }
        while (it.hasNext()) {
            currentObj = it.next();
            animalsVB.getChildren().add(new AnimalComponent((Animal) currentObj));
        }
    }
}
