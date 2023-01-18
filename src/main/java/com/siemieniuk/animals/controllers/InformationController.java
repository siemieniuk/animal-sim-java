package com.siemieniuk.animals.controllers;

import com.siemieniuk.animals.Animal;
import com.siemieniuk.animals.AnimalComponent;
import com.siemieniuk.animals.DetailsPrintable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class InformationController implements Initializable {
    @FXML private VBox animalsVB;

    public void initialize(URL src, ResourceBundle rb) {
    }

    public void changeDisplay(List<DetailsPrintable> obj) {
        assert obj.size() != 0;
        Iterator<DetailsPrintable> it = obj.iterator();
        DetailsPrintable currentObj = it.next();
        if (currentObj != null) {
            animalsVB.getChildren().add(new Text(currentObj.getDetails()));
        }
        while (it.hasNext()) {
            currentObj = it.next();
            System.out.println(currentObj);
            animalsVB.getChildren().add(new AnimalComponent((Animal) currentObj));
        }
    }

}
