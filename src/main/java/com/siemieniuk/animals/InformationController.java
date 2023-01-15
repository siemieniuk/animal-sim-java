package com.siemieniuk.animals;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class InformationController implements Initializable {
    @FXML private Text propertiesText;

    /* TODO: Add buttons for predator/prey (route and delete) */
    public void changeDisplay(List<DetailsPrintable> obj) {
        StringBuilder textToDisplay = new StringBuilder();
        Iterator<DetailsPrintable> it = obj.iterator();

        DetailsPrintable currentObj = it.next();
        textToDisplay.append(currentObj.getDetails());
        while (it.hasNext()) {
            currentObj = it.next();
            textToDisplay.append("---------------\n");
            textToDisplay.append(currentObj.getDetails());
        }

        propertiesText.setText(textToDisplay.toString());
    }

    public void initialize(URL src, ResourceBundle rb) {
    }
}
