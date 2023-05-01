package com.siemieniuk.animals.gui;

import com.siemieniuk.animals.core.typing.WorldObjectType;
import com.siemieniuk.animals.core.animals.Animal;
import com.siemieniuk.animals.core.animals.Prey;
import com.siemieniuk.animals.core.World;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * This class is animal custom JavaFX component. It wraps animal into the GridPane
 * @author  Szymon Siemieniuk
 */
public class AnimalView extends GridPane {
    private boolean wasDeleteClicked = false;

    /**
     * Constructor
     * @param animal The animal to wrap
     */
    public AnimalView(Animal animal) {
        Text text = new Text();
        text.setText(animal.getDetails());
        text.getStyleClass().clear();
        text.getStyleClass().add("ac-text");

        Button removeBtn = new Button("Delete animal");
        removeBtn.setOnAction((event) -> {
            World.getInstance().removeAnimal(animal.getId());
            wasDeleteClicked = true;
        });

        if (animal instanceof Prey) {
            Button hideoutBtn = new Button("Send to hideout");
            hideoutBtn.setOnAction((event) -> ((Prey) animal).findNewTarget(WorldObjectType.HIDEOUT));
            add(hideoutBtn, 0, 2);

            Button plantSrcBtn = new Button("Send to plant source");
            plantSrcBtn.setOnAction((event) -> ((Prey) animal).findNewTarget(WorldObjectType.PLANT_SRC));
            add(plantSrcBtn, 1, 2);

            Button waterSrcBtn = new Button("Send to water source");
            waterSrcBtn.setOnAction((event) -> ((Prey) animal).findNewTarget(WorldObjectType.WATER_SRC));
            add(waterSrcBtn, 2, 2);
        }
        add(removeBtn, 0, 1, 3, 1);
        add(text, 0, 0, 3, 1);
        setHgap(10.0);
        setPrefWidth(Double.MAX_VALUE);
        this.getStyleClass().clear();
        this.getStyleClass().add("animal-component");
    }
}
