package com.siemieniuk.animals.core.locations;

import com.siemieniuk.animals.core.DetailsPrintable;
import com.siemieniuk.animals.core.typing.LocationVisitor;
import com.siemieniuk.animals.math.Coordinates;
import com.siemieniuk.animals.core.typing.WorldObjectType;

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

	public PlantSource() {
		super();
	}

	@Override
	public void accept(LocationVisitor visitor) {
		visitor.visitPlantSource(this);
	}

	@Override
	public WorldObjectType getMetadataCode() {
		return WorldObjectType.PLANT_SRC;
	}
}
