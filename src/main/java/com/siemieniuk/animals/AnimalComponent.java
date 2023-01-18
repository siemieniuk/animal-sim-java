package com.siemieniuk.animals;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;


public class AnimalComponent extends GridPane {

    public AnimalComponent(Animal a) {
        Text text = new Text();
        text.setText(a.getDetails());

        Button removeBtn = new Button("Delete animal");
        removeBtn.setOnAction((event) -> World.getInstance().removeAnimal(a.getId()));

        if (a instanceof Prey) {
            Button hideoutBtn = new Button("Send to hideout");
            hideoutBtn.setOnAction((event) -> ((Prey) a).findNewTarget(LocationType.HIDEOUT));
            add(hideoutBtn, 0, 2);

            Button plantSrcBtn = new Button("Send to plant source");
            plantSrcBtn.setOnAction((event) -> ((Prey) a).findNewTarget(LocationType.PLANT_SRC));
            add(plantSrcBtn, 1, 2);

            Button waterSrcBtn = new Button("Send to water source");
            waterSrcBtn.setOnAction((event) -> ((Prey) a).findNewTarget(LocationType.WATER_SRC));
            add(waterSrcBtn, 2, 2);
        }
        add(removeBtn, 0, 1, 3, 1);
        add(text, 0, 0, 3, 1);
        setHgap(10.0);
        setPrefWidth(Double.MAX_VALUE);
    }
}
