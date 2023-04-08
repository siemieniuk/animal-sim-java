package com.siemieniuk.animals;

/**
 * @author Szymon Siemieniuk
 * @version 0.1
 *
 */
public class PlantSource extends Source {
	
	/**
	 * @param x First coordinate
	 * @param y Second coordinate
	 * @param name Name
	 * @param preyReplenishingSpeed Speed of replenishing prey's food
	 * @param capacity Maximal amount of animals which can use this source simultaneously
	 */
	public PlantSource(int x, int y, String name, float preyReplenishingSpeed, int capacity) {
		super(x, y, name, preyReplenishingSpeed, capacity);
	}
}
