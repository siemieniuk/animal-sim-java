package com.siemieniuk.animals;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Iterator;
import java.util.List;

/**
 * Class representing a prey - subclass of animal
 * @author Szymon Siemieniuk
 * @version 0.1
 *
 */
public class Prey extends Animal {
	private final int foodDecreaser;
	private final int waterDecreaser;
	private volatile int foodLevel;
	private volatile int waterLevel;
	private Location targetLocation;
	private List<Coordinates> plan;
	
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
	public Prey(String name, int health, int speed, int strength, String species, Coordinates pos, int waterDecreaser, int foodDecreaser) {
		super(name, health, speed, strength, species, pos);
		this.foodDecreaser = foodDecreaser;
		this.waterDecreaser = waterDecreaser;
		this.foodLevel = 100;
		this.waterLevel = 100;
		this.plan = null;
	}

	@Override
	public void run() {
		Iterator<Coordinates> planIterator = null;
		Coordinates next = null;
		while (isAlive()) {
			if (targetLocation == null) {
				findNewTarget();
				planIterator = plan.iterator();
//				// TO REMOVE
//				for (Coordinates pos : plan) {
//					System.out.print(pos + " ");
//				}
//				System.out.println();
//				/////////////
			} else {
				assert planIterator != null;
				while (!getPos().equals(targetLocation.getPos())) {
					if (planIterator.hasNext()) {
						next = planIterator.next();
					}
					move();
					try {
						Thread.sleep(1000/getSpeed());
					} catch (InterruptedException e) {
						break;
					}
				}
				// TODO: Consume source
				// ...
			}
		}
	}

	@Override
	protected void findNewTarget() {
		PreyRouter router = new PreyRouter(getPos());
		Class obj;
		try {
			if (isHungry()) {
				obj = Class.forName("com.siemieniuk.animals.PlantSource");
			} else if (isThirsty()) {
				obj = Class.forName("com.siemieniuk.animals.WaterSource");
			} else {
				obj = Class.forName("com.siemieniuk.animals.Hideout");
			}
			router.setTargetToNearest(obj);
			router.setPlan();
			plan = router.getPlan();
			targetLocation = router.getTarget();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
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
	public synchronized void decreaseStatistics() {
		foodLevel = Math.max(0, foodLevel-foodDecreaser);
		waterLevel = Math.max(0, waterLevel-waterDecreaser);
	}
	
	/* TODO: Implement */
	/**
	 * Moves prey
	 */
	@Override
//	public void move(Coordinates newPos) {
	public void move() {

	}

	@Override
	public void prepareToDrawOn(GraphicsContext gc) {
		gc.setFill(Color.LIMEGREEN);
	}
	
	/**
	 * Checks if a prey is hungry
	 * @return True if food level is less or equal zero, false otherwise
	 */
	public boolean isHungry() {
		return foodLevel <= 0;
	}
	
	/**
	 * Checks if a prey is thirsty
	 * @return True if water level is less or equal zero, false otherwise
	 */
	public boolean isThirsty() {
		return waterLevel <= 0;
	}
	
	/* TODO: Implement */
	/**
	 * Reproduces prey
	 * @return A new instance of prey
	 */
	public Prey reproduce() {
		return null;
	}

	@Override
	public String getDetails() {
		return "Prey:" +
				"\n" + super.getDetails() +
				"\nFood level: " + foodLevel +
				"\nWater level: " + waterLevel + "\n";
	}
}
