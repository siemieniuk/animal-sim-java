package com.siemieniuk.animals;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
	 * @param pos Object of class Coordinates
	 */
	public Intersection(Coordinates pos) {
		super(pos);
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
	 * @return A prey
	 */
	public Prey getUsedBy() {
		return (usedBy == null) ? null : usedBy;
	}
	
	/**
	 * Checks if an intersection is occupied
	 * @return True if occupied, false otherwise
	 */
	public boolean isOccupied() {
		return usedBy != null;
	}

	public void prepareToDrawOn(GraphicsContext gc) {
		gc.setFill(Color.YELLOW);
	}

	/* TODO: Implement */
	@Override
	public String getDetails() {
		return super.getDetails() + "Intersection\n";
	}
}
