package com.siemieniuk.animals;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Szymon Siemieniuk
 * @version 0.1
 *
 */
public class World {
	private int howManySources = -1;
	private int howManyHideouts = -1;
	private final int xSize;
	private final int ySize;
	private List<Animal> animals;
	private List<Location> locations;

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	private World() {
		this.xSize = 0;
		this.ySize = 0;
	}

	/**
	 * Generates a world
	 * @param locations List containing all locations in the world
	 * @param xSize Size in horizontal axis
	 * @param ySize Size in vertical axis
	 */
	public World(List<Location> locations, int xSize, int ySize) {
		this.locations = locations;
		this.xSize = xSize;
		this.ySize = ySize;
		this.animals = new ArrayList<Animal> ();
		getHowManyHideouts();
		getHowManySources();
	}
	
	/* TODO: Implement */
	/**
	 * Changes route of animal
	 * @param a Animal to modify a route
	 * @param s A new destination
	 */
	public void changeRouteOfAnimal(Animal a, Location l) {
		
	}
	
	/* TODO: Implement */
	/**
	 * Adds a new predator to the world
	 * @param p A predator (previously created)
	 */
	public void createPredator(Predator p) {
		
	}
	
	/* TODO: Implement */
	/**
	 * Adds a new prey to the world
	 * @param p Prey (previously created)
	 */
	public void createPrey(Prey p) {
		
	}

	@Override
	public String toString() {
		return "Hideouts: " + howManyHideouts + ", sources: " + howManySources + "\n" + locations.toString();
	}

	public int getHowManySources() {
		if (howManySources == -1) {
			howManySources = 0;
			for (Location l : locations) {
				if (l instanceof Source) {
					howManySources++;
				}
			}
		}
		return howManySources;
	}

	public void setHowManySources(int howManySources) {
		this.howManySources = howManySources;
	}

	public int getHowManyHideouts() {
		if (howManyHideouts == -1) {
			howManyHideouts = 0;
			for (Location l : locations) {
				if (l instanceof Hideout) {
					howManyHideouts++;
				}
			}
		}
		return howManyHideouts;
	}

	public void setHowManyHideouts(int howManyHideouts) {
		this.howManyHideouts = howManyHideouts;
	}
}
