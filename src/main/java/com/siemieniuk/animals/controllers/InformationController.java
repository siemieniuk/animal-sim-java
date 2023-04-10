package com.siemieniuk.animals.controllers;

import com.siemieniuk.animals.Animal;
import com.siemieniuk.animals.AnimalComponent;
import com.siemieniuk.animals.DetailsPrintable;
import com.siemieniuk.animals.Location;
import javafx.fxml.FXML;
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
