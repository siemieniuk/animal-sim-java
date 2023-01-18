package com.siemieniuk.animals;

import com.siemieniuk.animals.math.Coordinates;
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
	private static final int MAX_FOOD_LEVEL = 100;
	private static final int MAX_WATER_LEVEL = 100;
	private final int foodDecreaser;
	private int foodLevel;
	private final int waterDecreaser;
	private int waterLevel;
	private boolean isUsingMutex;
	private boolean isWaitingForHideout;
	private Location targetLocation;
	private List<Coordinates> plan;
	private Coordinates nextStep;

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
		this.plan = null;
	}

	@Override
	public void run() {
		Iterator<Coordinates> planIterator = null;
		try {
			while (isAlive()) {
				if (targetLocation == null) {
					findNewTarget();
					if (plan != null) {
						planIterator = plan.iterator();
					}
				} else {
					assert planIterator != null;
					while (!getPos().equals(targetLocation.getPos())) {
						if (planIterator.hasNext()) {
							nextStep = planIterator.next();
						}
						releaseResources();
						move();
						Thread.sleep(1000/getSpeed());
					}
					consume();
					releaseResources();
				}
			}
		} catch (InterruptedException e) {
			releaseResources();
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

	protected void findNewTarget(LocationType locType) {
		PreyRouter router = new PreyRouter(getPos());
		Class obj;
		try {
			if (locType.equals(LocationType.PLANT_SRC)) {
				obj = Class.forName("com.siemieniuk.animals.PlantSource");
			} else if (locType.equals(LocationType.WATER_SRC)) {
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

	/**
	 * Checks if this prey can be attacked
	 * @return True if it can be attacked, false otherwise
	 */
	public boolean canBeAttacked() {
		return !(World.getInstance().getLocation(getPos()) instanceof Hideout);
	}

	public synchronized void beAttacked(int predStrength) {
		int decreaser = Math.max(0, predStrength - getStrength());
		decreaseHealth(decreaser);
	}

	public void consume() throws InterruptedException {
		isUsingMutex = true;
		if (targetLocation instanceof WaterSource) {
			drink();
		} else if (targetLocation instanceof PlantSource) {
			eat();
		} else {
			useHideout();
		}
		targetLocation = null;
	}

	public void useHideout() throws InterruptedException {
		if (targetLocation instanceof Hideout) {
			int maxHealth = getMAX_HEALTH();
			while (getHealth() < 0.9*maxHealth) {
				heal(5);
				Thread.sleep(200);
				if (Math.random() < 0.02) {
					reproduce();
				}
			}
		}
	}

	/**
	 * Refills prey's water level
	 */
	public void drink() throws InterruptedException {
		if (targetLocation instanceof WaterSource) {
			while (waterLevel < 0.9*MAX_WATER_LEVEL) {
				int increaser = (int)((WaterSource) targetLocation).getHowMuchToConsume();
				waterLevel = Math.min(MAX_FOOD_LEVEL, foodLevel + increaser);
				Thread.sleep(200);
			}
		}
	}

	/**
	 * Refills prey's energy
	 */
	public void eat() throws InterruptedException {
		if (targetLocation instanceof PlantSource) {
			while (foodLevel < 0.9*MAX_FOOD_LEVEL) {
				int increaser = (int)((PlantSource) targetLocation).getHowMuchToConsume();
				foodLevel = Math.min(MAX_FOOD_LEVEL, foodLevel + increaser);
				Thread.sleep(200);
			}
		}
	}

	/**
	 * Decrease statistics
	 */
	public synchronized void decreaseStatistics() {
		if (!isWaitingForHideout) {
			foodLevel = Math.max(0, foodLevel-foodDecreaser);
			waterLevel = Math.max(0, waterLevel-waterDecreaser);
		}
	}

	/**
	 * Releases used semaphores
	 */
	public void releaseResources() {
		if (isUsingMutex) {
			Location loc = (World.getInstance().getLocation(getPos()));
			if (loc instanceof Intersection) {
				((Intersection) loc).unsetUsedBy();
			} else if (loc instanceof Source) {
				((Source) loc).removeAnimal(this);
			} else if (loc instanceof Hideout) {
				((Hideout) loc).removeAnimal(this);
			}
			isUsingMutex = false;
		}
	}

	/**
	 * Moves prey by one position specified in the nextStep property.
	 */
	@Override
	public void move() {
		Location newLoc = World.getInstance().getLocation(nextStep);
		if (newLoc instanceof Intersection) {
			((Intersection) newLoc).setUsedBy(this);
			isUsingMutex = true;
		} else if (newLoc instanceof Source) {
			((Source) newLoc).addNewAnimal(this);
		} else if (newLoc instanceof Hideout) {
			isWaitingForHideout = true;
			((Hideout) newLoc).addNewAnimal(this);
			isWaitingForHideout = false;
		}
		setPos(nextStep);
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

	/**
	 * Reproduces prey
	 */
	public void reproduce() {
		Prey p = new Prey("Melman", 100, 1, 4, "Giraffe", 3, 5);
		World.getInstance().createAnimal(p);
	}

	@Override
	public String getDetails() {
		return "Prey:" +
				"\n" + super.getDetails() +
				"\nFood level: " + foodLevel +
				"\nWater level: " + waterLevel + "\n";
	}
}
