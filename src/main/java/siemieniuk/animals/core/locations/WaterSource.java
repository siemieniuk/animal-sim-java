package siemieniuk.animals.core.locations;

import siemieniuk.animals.core.typing.LocationVisitor;
import siemieniuk.animals.core.typing.WorldObjectType;
import siemieniuk.animals.math.Coordinates;

/**
 * This class represents Water Source.
 * @author Szymon Siemieniuk
 * @version 0.1
 */
public final class WaterSource extends Source {
	
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

	@Override
	public void accept(LocationVisitor visitor) {
		visitor.visitWaterSource(this);
	}

	@Override
	public WorldObjectType getMetadataCode() {
		return WorldObjectType.WATER_SRC;
	}
}
