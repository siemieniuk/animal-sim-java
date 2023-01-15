package com.siemieniuk.animals;

import javafx.scene.canvas.GraphicsContext;

/**
 * A class representing each location having its own coordinates.
 * @author Szymon Siemieniuk
 * @version 0.1
 *
 */
public abstract class Location implements DetailsPrintable, Drawable {
	private final Coordinates pos;
	
	/**
	 * Creates location
	 * @param pos Object of class Coordinates
	 */
	public Location(Coordinates pos) {
		this.pos = pos;
	}
	
	/* TODO: Implement */
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

	@Override
	public String getDetails() {
		return "LOCATION: " +
				"\nPosition: " + pos  +
				"\nType: ";
	}

	@Override
	public abstract void prepareToDrawOn(GraphicsContext gc);

	public String toString() {
		return getClass() + " " + pos;
	}
}
