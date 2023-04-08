package com.siemieniuk.animals;

/**
 * A class representing each location having its own coordinates.
 * @author Szymon Siemieniuk
 * @version 0.1
 *
 */
public abstract class Location {
	private int xPos;
	private int yPos;
	
	public Location() {
		this.xPos = 0;
		this.yPos = 0;
	}
	
	
	/**
	 * Creates location
	 * 
	 * @param x First coordinate
	 * @param y Second coordinate
	 */
	public Location(int x, int y) {
		this.xPos = x;
		this.yPos = y;
	}
	
	/* TODO: Implement */
	/**
	 * Calculates distance from this object to another location
	 * @param other A destination location
	 * @return Value representing a distance
	 */
	public int[] getDistanceTo(Location other) {
		return null;
	}
	
	/**
	 * Gets coordinates of position
	 * @return A pair of coordinates (x, y)
	 */
	public int[] getPos() {
		int[] pos = {xPos, yPos};
		return pos;
	}

	public String toString() {
		return getClass() + " (" + xPos + ", " + yPos + ")\n";
	}
}
