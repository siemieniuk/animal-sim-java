package com.siemieniuk.animals;

import com.siemieniuk.animals.math.Coordinates;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Szymon Siemieniuk
 * @version 0.1
 *
 */
public class PlantSource extends Source {
	
	/**
	 * @param pos Object of class Coordinates
	 * @param name Name
	 * @param preyReplenishingSpeed Speed of replenishing prey's food
	 * @param capacity Maximal amount of animals which can use this source simultaneously
	 */
	public PlantSource(Coordinates pos, String name, float preyReplenishingSpeed, int capacity) {
		super(pos, name, preyReplenishingSpeed, capacity);
	}

	@Override
	public void prepareToDrawOn(GraphicsContext gc) {
		gc.setFill(Color.BROWN);
	}

	@Override
	public String getDetails() {
		return super.getDetails() + "Plant Source\n"
				+ "called " + getName() + "\n"
				+ "used by " + getUsageString() + " animals\n";
	}
}
