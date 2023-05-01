package com.siemieniuk.animals.core.locations;

import com.siemieniuk.animals.core.typing.WorldObjectType;
import com.siemieniuk.animals.core.animals.Prey;
import com.siemieniuk.animals.math.Coordinates;

import java.util.concurrent.Semaphore;

/**
 * A location with exactly only 0 or 1 prey per object
 * @author Szymon Siemieniuk
 */
public class Intersection extends Location {
	private final Semaphore sem;
	private Prey usedBy = null;
	
	/**
	 * Constructor
	 * @param pos Object of class Coordinates
	 */
	public Intersection(Coordinates pos) {
		super(pos);
		sem = new Semaphore(1);
	}
	
	/**
	 * Sets a prey which now uses the intersection
	 * @param p Prey
	 */
	public void setUsedBy(Prey p) {
		try {
			sem.acquire();
			usedBy = p;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Marks intersection as free
	 */
	public void unsetUsedBy() {
		usedBy = null;
		sem.release();
	}
	
	/**
	 * Checks if an intersection is occupied
	 * @return True if occupied, false otherwise
	 */
	public boolean isOccupied() {
		return usedBy != null;
	}

	@Override
	public String toString() {
		return "Intersection";
	}

	@Override
	public WorldObjectType getMetadataCode() {
		return WorldObjectType.INTERSECTION;
	}
}
