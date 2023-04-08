package com.siemieniuk.animals;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Szymon Siemieniuk
 * @version 0.1
 *
 */
public abstract class Source extends Location {
	private final String name;
	private final float preyReplenishingSpeed;
	private final int capacity;
	private List<Prey> usedBy;

	private Source() {
		this.name = "";
		this.capacity = 0;
		this.preyReplenishingSpeed = 0.0f;
	}

	/**
	 * @param x First coordinate
	 * @param y Second coordinate
	 * @param name Name
	 * @param preyReplenishingSpeed Speed of replenishing water by prey
	 * @param capacity Maximal amount of preys which can use this water source simultaneously
	 */
	public Source(int x, int y, String name, float preyReplenishingSpeed, int capacity) {
		super(x, y);
		this.name = name;
		this.capacity = capacity;
		this.preyReplenishingSpeed = preyReplenishingSpeed;
		this.usedBy = new ArrayList<Prey>();
	}

	/**
	 * Checks if there is some place for next creature
	 * @return True if occupied, false otherwise
	 */
	public boolean isOccupied() {
		return (usedBy.size() == capacity) ? true : false;
	}

	/**
	 * Adds prey which consumes this source
	 * @param newPrey Prey
	 */
	public void addNewAnimal(Prey newPrey) {
		if (this.isOccupied() == false) {
			usedBy.add(newPrey);
		}
	}

	/**
	 * Removes prey from source; frees one slot
	 * @param prey Prey to remove
	 */
	public void removeAnimal(Prey prey) {
		if (usedBy.contains(prey)) {
			usedBy.remove(prey);			
		}
	}

	/**
	 * Gets current list of animals
	 * @return List of animals currently using this source
	 */
	public List<Prey> getCurrentAnimals() {
		return usedBy;
	}

	/**
	 * Gets name
	 * @return Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get prey replenishing speed
	 * @return Prey replenishing speed
	 */
	public float getPreyReplenishingSpeed() {
		return preyReplenishingSpeed;
	}
}
