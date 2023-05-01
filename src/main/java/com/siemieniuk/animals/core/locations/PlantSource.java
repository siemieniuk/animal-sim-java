package com.siemieniuk.animals.core.locations;

import com.siemieniuk.animals.core.DetailsPrintable;
import com.siemieniuk.animals.core.WorldObjectType;
import com.siemieniuk.animals.math.Coordinates;

/**
 * This class represents a plant source - a place where preys can eat well (or being eaten by predators)
 * @author Szymon Siemieniuk
 */
public final class PlantSource extends Source implements DetailsPrintable {
	
	/**
	 * A simple constructor
	 * @param pos Object of class Coordinates
	 * @param name Name
	 * @param preyReplenishingSpeed Speed of replenishing prey's food
	 * @param capacity Maximal amount of animals which can use this source simultaneously
	 */
	public PlantSource(Coordinates pos, String name, float preyReplenishingSpeed, int capacity) {
		super(pos, name, preyReplenishingSpeed, capacity);
	}

	@Override
	public WorldObjectType getMetadataCode() {
		return WorldObjectType.PLANT_SRC;
	}

	/**
	 * Sets object-specific string to describe the object's state
	 * @return Text to display
	 */
	@Override
	public String getDetails() {
		return super.getDetails() + "Plant Source\n"
				+ "called " + getName() + "\n"
				+ "used by " + getUsageString() + " animals\n";
	}
}
