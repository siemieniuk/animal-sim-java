package com.siemieniuk.animals;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
	private final int capacity;
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
	}

	/**
	 * Checks if all the slots are occupied
	 * @return True if there is no free space, false otherwise
	 */
	public boolean isOccupied() {
		return usedBy.size() == capacity;
	}
	
	/**
	 * Adds animal to the hideout
	 * @param newPrey Prey to be added
	 */
	public void addNewAnimal(Prey newPrey) {
		if (!this.isOccupied()) {
			usedBy.add(newPrey);
		}
	}
	
	/**
	 * Removes animal from hideout (does nothing if it is not)
	 * @param prey Prey to remove
	 */
	public void removeAnimal(Prey prey) {
		usedBy.remove(prey);
	}
	
	/**
	 * Returns list of current animals
	 * @return List of current animals
	 */
	public List<Prey> getCurrentAnimals() {
		return usedBy;
	}

	@Override
	public void prepareToDrawOn(GraphicsContext gc) {
		gc.setFill(Color.ORANGE);
	}

	/* TODO: Implement */
	@Override
	public String getDetails() {
		return super.getDetails() + "Hideout\n";
	}
}
