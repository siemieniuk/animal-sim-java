package com.siemieniuk.animals;

/**
 * Class representing a prey - subclass of animal
 * @author Szymon Siemieniuk
 * @version 0.1
 *
 */
public class Prey extends Animal {
	private int foodLevel;
	private int foodDecreaser;
	private int waterLevel;
	private int waterDecreaser;
	private Location targetLocation;
	private Location currentLocation;
	
	/* TODO: Implement */
	/**
	 * Constructor
     * @param name Name of animal
     * @param health Initial health
     * @param speed Speed of animal
     * @param strength Strength of animal
     * @param species Animal's species
	 * @param waterDecreaser How much water level will animal lose after each step 
	 * @param foodDecreaser How much food level will animal lose after each step 
	 */
	public Prey(String name, int health, int speed, int strength, String species, int waterDecreaser, int foodDecreaser) {
		super(name, health, speed, strength, species);
		this.foodDecreaser = foodDecreaser;
		this.waterDecreaser = waterDecreaser;
		this.foodLevel = 100;
		this.waterLevel = 100;
	}
	
	/* TODO: Implement */
	/**
	 * Checks if this prey can be attacked
	 * @return True if it can be attacked, false otherwise
	 */
	public boolean canBeAttacked() {
		return false;
	}
	
	/* TODO: Implement */
	/**
	 * Refills prey's water level
	 * @param waterSrc Water source which prey wants to use
	 */
	public void drink(WaterSource waterSrc) {

	}
	
	/**
	 * Decrease statistics
	 */
	public void decreaseStatistics() {
		foodLevel -= foodDecreaser;
		waterLevel -= waterDecreaser;
	}
	
	/* TODO: Implement */
	/**
	 * Moves prey
	 */
	public void move() {
		
	}
	
	/**
	 * Checks if a prey is hungry
	 * @return True if food level is less or equal zero, false otherwise
	 */
	public boolean isHungry() {
		return (foodLevel > 0) ? false : true;
	}
	
	/**
	 * Checks if a prey is thirsty
	 * @return True if water level is less or equal zero, false otherwise
	 */
	public boolean isThirsty() {
		return (waterLevel > 0) ? false : true;
	}
	
	/* TODO: Implement */
	/**
	 * Reproduces prey
	 * @return A new instance of prey
	 */
	public Prey reproduce() {
		return null;
	}
}
