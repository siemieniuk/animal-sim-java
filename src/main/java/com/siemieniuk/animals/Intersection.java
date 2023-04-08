package com.siemieniuk.animals;

/**
 * A location with exactly only 0 or 1 prey per object
 * @author Szymon Siemieniuk
 * @version 0.1
 *
 */
public class Intersection extends Location {
	private Prey usedBy = null;
	
	/**
	 * Constructor
	 * @param x First coordinate
	 * @param y Second coordinate
	 */
	public Intersection(int x, int y) {
		super(x, y);
	}
	
	/**
	 * Sets a prey which now uses the intersection
	 * @param p Prey
	 */
	public void setUsedBy(Prey p) {
		usedBy = p;
	}
	
	/**
	 * Marks intersection as free
	 */
	public void unsetUsedBy() {
		usedBy = null;
	}
	
	/**
	 * Returns reference to a prey using this intersection
	 * @return
	 */
	public Prey getUsedBy() {
		return (usedBy == null) ? null : usedBy;
	}
	
	/**
	 * Checks if an intersection is occupied
	 * @return True if occupied, false otherwise
	 */
	public boolean isOccupied() {
		return (usedBy == null) ? false : true;
	}
}
