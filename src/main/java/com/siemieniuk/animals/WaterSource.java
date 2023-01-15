package com.siemieniuk.animals;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Szymon Siemieniuk
 * @version 0.1
 *
 */
public class WaterSource extends Source {
	
	/**
	 * @param pos Object of class Coordinates
	 * @param name Name
	 * @param preyReplenishingSpeed Speed of replenishing water by prey
	 * @param capacity Maximal amount of preys which can use this water source simultaneously
	 */
	public WaterSource(Coordinates pos, String name, float preyReplenishingSpeed, int capacity) {
		super(pos, name, preyReplenishingSpeed, capacity);
	}

	@Override
	public void prepareToDrawOn(GraphicsContext gc) {
		gc.setFill(Color.BLUE);
	}

	/* TODO: Implement */
	@Override
	public String getDetails() {
		return super.getDetails() + "WaterSource\n";
	}
}
