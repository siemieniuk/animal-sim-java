package com.siemieniuk.animals;

/**
 * @author Szymon Siemieniuk
 * @version 0.1
 *
 */
public class Predator extends Animal {
	private PredatorMode currentMode;
	private Prey preyToEat = null;
	
	/* TODO: Implement */
	/**
	 * 
	 */
	public Predator() {
		super();
	}
	
	/**
     * Constructor
     * @param name Name of animal
     * @param health Initial health
     * @param speed Speed of animal
     * @param strength Strength of animal
     * @param species Animal's species
     */
	public Predator(String name, int health, int speed, int strength, String species) {
		super(name, health, speed, strength, species);
		this.currentMode = PredatorMode.RELAXATION;
	}
	
	/* TODO: Implement */
	/**
	 * Attacks prey in preyToEat
	 */
	public void attackMyPrey() {
		
	}
	
	/* TODO: Implement */
	/**
	 * Eats prey in preyToEat
	 */
	public void eatMyPrey() {
		if (preyToEat.isAlive() == false) {
			this.switchMode();
			preyToEat = null; // TODO: Physically remove this animal
		}
	}
	
	/* TODO: Implement */
	/**
	 * Moves predator
	 */
	public void move() {
		
	}
	
	/* TODO: Implement */
	/**
	 * Finds a new prey for predator
	 */
	public void findNewPrey() {
		
	}
	
	/**
	 * Switches mode of this predator. Hunting reaches relaxation and vice versa.
	 */
	public void switchMode() {
		if (this.currentMode.equals(PredatorMode.HUNTING)) {
			this.currentMode = PredatorMode.RELAXATION;
		} else {
			this.currentMode = PredatorMode.HUNTING;
		}
	}
}
