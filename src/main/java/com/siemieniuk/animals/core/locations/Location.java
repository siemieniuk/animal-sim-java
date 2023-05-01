package com.siemieniuk.animals.core.locations;

import com.siemieniuk.animals.core.WorldObjectMetadata;
import com.siemieniuk.animals.math.Coordinates;

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

	/**
	 * Calculates distance from this object to another location
	 * @param other A destination location
	 * @return Value representing a distance
	 */
	public int getDistanceTo(Location other) {
		return pos.getManhattanDistanceTo(other.getPos());
	}
	
	/**
	 * Gets coordinates of position
	 * @return A pair of coordinates (x, y)
	 */
	public Coordinates getPos() {
		return pos;
	}

	/**
	 * Sets object-specific string to describe the object's state
	 * @return Text to display
	 */
	public String getDetails() {
		return "LOCATION: " +
				"\nPosition: " + pos  +
				"\nType: ";
	}

	public String toString() {
		return getClass() + " " + pos;
	}
}
