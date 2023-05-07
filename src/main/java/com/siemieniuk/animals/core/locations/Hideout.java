package com.siemieniuk.animals.core.locations;

import com.siemieniuk.animals.core.DetailsPrintable;
import com.siemieniuk.animals.core.animals.Prey;
import com.siemieniuk.animals.core.typing.LocationVisitor;
import com.siemieniuk.animals.math.Coordinates;
import com.siemieniuk.animals.core.typing.WorldObjectType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * This class represents a hideout - a place where preys can reproduce and
 * not worry about being eaten by predator
 * @author Szymon Siemieniuk
 */
public final class Hideout extends Location implements DetailsPrintable {
	private final int capacity;

	private final Semaphore sem;
	List<Prey> usedBy;

	/**
	 * Constructor
	 * @param pos Object of class Coordinate
	 * @param capacity Maximal amount of animals
	 */
	public Hideout(Coordinates pos, int capacity) {
		super(pos);
		this.capacity = capacity;
		this.usedBy = new ArrayList<> ();
		this.sem = new Semaphore(capacity);
	}

	/**
	 * Checks if all the slots are occupied
	 * @return True if there is no free space, false otherwise
	 */
	public boolean isOccupied() {
		return usedBy.size() == capacity;
	}

	/**
	 * Adds the prey to the hideout
	 * @param newPrey Prey to be added
	 */
	public void addNewAnimal(Prey newPrey) {
		try {
			sem.acquire();
			usedBy.add(newPrey);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			usedBy.remove(newPrey);
		}
	}

	/**
	 * Removes animal from hideout (does nothing if it is not)
	 * @param prey Prey to remove
	 */
	public void removeAnimal(Prey prey) {
		usedBy.remove(prey);
		sem.release();
	}

	@Override
	public void accept(LocationVisitor visitor) {
		visitor.visitHideout(this);
	}

	/**
	 * Returns list of current animals
	 * @return List of current animals
	 */
	public List<Prey> getCurrentAnimals() {
		return usedBy;
	}

	@Override
	public WorldObjectType getMetadataCode() {
		return WorldObjectType.HIDEOUT;
	}

	public int getHowManyPreysNow() {
		return usedBy.size();
	}

	public int getCapacity() {
		return capacity;
	}

	@Override
	public String toString() {
		return "Hideout";
	}

	/**
	 * Sets object-specific string to describe the object's state
	 * @return Text to display
	 */
	@Override
	public String getDetails() {
		return super.getDetails() + "Hideout\n"
				+ "used by " + usedBy.size() + "/" + capacity + " animals\n";
	}
}
