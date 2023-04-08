package com.siemieniuk.animals;

/**
 * @author Szymon Siemieniuk
 * @version 0.1
 *
 */
public class WaterSource extends Source {
	
	/**
	 * @param x First coordinate
	 * @param y Second coordinate
	 * @param name Name
	 * @param preyReplenishingSpeed Speed of replenishing water by prey
	 * @param capacity Maximal amount of preys which can use this water source simultaneously
	 */
	public WaterSource(int x, int y, String name, float preyReplenishingSpeed, int capacity) {
		super(x, y, name, preyReplenishingSpeed, capacity);
	}
}
