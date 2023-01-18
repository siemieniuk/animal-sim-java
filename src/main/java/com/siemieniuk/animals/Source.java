package com.siemieniuk.animals;

import com.siemieniuk.animals.math.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * @author Szymon Siemieniuk
 * @version 0.1
 *
 */
public abstract class Source extends Location {
	private final String name;
	private final float preyReplenishingSpeed;
	private final int capacity;
	private final List<Prey> usedBy;
	private final Semaphore sem;

	/**
	 * @param pos Object of type Coordinates
	 * @param name Name
	 * @param preyReplenishingSpeed Speed of replenishing water by prey
	 * @param capacity Maximal amount of preys which can use this water source simultaneously
	 */
	public Source(Coordinates pos, String name, float preyReplenishingSpeed, int capacity) throws IllegalArgumentException {
		super(pos);
		this.name = name;
		this.capacity = capacity;
		this.preyReplenishingSpeed = preyReplenishingSpeed;
		this.usedBy = new ArrayList<>();
		this.sem = new Semaphore(capacity);
	}

	/**
	 * Checks if there is some place for next creature
	 * @return True if occupied, false otherwise
	 */
	public boolean isOccupied() {
		return usedBy.size() == capacity;
	}

	/**
	 * Adds prey which consumes this source
	 * @param newPrey Prey
	 */
	public void addNewAnimal(Prey newPrey) {
		try {
			sem.acquire();
			usedBy.add(newPrey);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Removes prey from source; frees one slot
	 * @param prey Prey to remove
	 */
	public void removeAnimal(Prey prey) {
		usedBy.remove(prey);
		sem.release();
	}

	/**
	 * Gets name
	 * @return Name
	 */
	public String getName() {
		return name;
	}

	protected String getUsageString() {
		return usedBy.size() + "/" + capacity;
	}

	/**
	 * Get prey replenishing speed
	 * @return Prey replenishing speed
	 */
	public float getHowMuchToConsume() {
		return usedBy.size() == 0 ? 0 : preyReplenishingSpeed / usedBy.size();
	}
}
