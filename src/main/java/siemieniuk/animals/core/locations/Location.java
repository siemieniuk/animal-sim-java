package siemieniuk.animals.core.locations;

import siemieniuk.animals.core.WorldObjectMetadata;
import siemieniuk.animals.core.typing.LocationVisitor;
import siemieniuk.animals.math.Coordinates;

/**
 * A class representing each location having its own coordinates.
 * @author Szymon Siemieniuk
 * @version 0.1
 *
 */
public abstract class Location implements WorldObjectMetadata {
	private final Coordinates pos;

	public Location() {
		this.pos = null;
	}

	/**
	 * Creates location
	 * @param pos Object of class Coordinates
	 */
	public Location(Coordinates pos) {
		this.pos = pos;
	}

	public abstract void accept(LocationVisitor visitor);
	
	/**
	 * Gets coordinates of position
	 * @return A pair of coordinates (x, y)
	 */
	public Coordinates getPos() {
		return pos;
	}

	public String toString() {
		return getClass() + " " + pos;
	}
}
