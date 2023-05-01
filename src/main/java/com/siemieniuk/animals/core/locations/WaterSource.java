package com.siemieniuk.animals.core.locations;

import com.siemieniuk.animals.core.DetailsPrintable;
import com.siemieniuk.animals.core.typing.WorldObjectType;
import com.siemieniuk.animals.math.Coordinates;

/**
 * This class represents Water Source.
 * @author Szymon Siemieniuk
 * @version 0.1
 */
public final class WaterSource extends Source implements DetailsPrintable {
	
	/**
	 * A simple constructor
	 * @param pos Object of class Coordinates
	 * @param name Name
	 * @param preyReplenishingSpeed Speed of replenishing water by prey
	 * @param capacity Maximal amount of preys which can use this water source simultaneously
	 */
	public WaterSource(Coordinates pos, String name, float preyReplenishingSpeed, int capacity) {
		super(pos, name, preyReplenishingSpeed, capacity);
	}

	public WaterSource() {
        super();
    }

	/**
	 * Sets object-specific string to describe the object's state
	 * @return Text to display
	 */
	@Override
	public String getDetails() {
		return super.getDetails() + "Water Source\n"
				                  + "called " + getName() + "\n"
							      + "used by " + getUsageString() + " animals\n";
	}

	@Override
	public WorldObjectType getMetadataCode() {
		return WorldObjectType.WATER_SRC;
	}
}
