package com.siemieniuk.animals;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents hideout - a place where preys can reproduce and 
 * not worry about being eaten by predator
 * @author Szymon Siemieniuk
 * @version 0.1
 *
 */
public class Hideout extends Location {
	private int capacity;
	List<Prey> usedBy;
	
	/**
	 * Constructor
	 * @param x First coordinate
	 * @param y Second coordinate
	 * @param capacity Maximal amount of animals
	 */
	public Hideout(int x, int y, int capacity) {
		super(x, y);
		this.capacity = capacity;
		this.usedBy = new ArrayList<Prey> ();
	}
	
	/**
	 * Checks if all the slots are occupied
	 * @return True if there is no free space, false otherwise
	 */
	public boolean isOccupied() {
		return (usedBy.size() == capacity) ? true : false;
	}
	
	/**
	 * Adds animal to the hideout
	 * @param newPrey Prey to be added
	 */
	public void addNewAnimal(Prey newPrey) {
		if (this.isOccupied() == false) {
			usedBy.add(newPrey);
		}
	}
	
	/**
	 * Removes animal from hideout (does nothing if it is not)
	 * @param prey Prey to remove
	 */
	public void removeAnimal(Prey prey) {
		if (usedBy.contains(prey)) {
			usedBy.remove(prey);			
		}
	}
	
	/**
	 * Returns list of current animals
	 * @return List of current animals
	 */
	public List<Prey> getCurrentAnimals() {
		return usedBy;
	}
}
