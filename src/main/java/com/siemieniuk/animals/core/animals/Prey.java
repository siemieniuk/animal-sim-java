package com.siemieniuk.animals.core;

import com.siemieniuk.animals.Hideout;
import com.siemieniuk.animals.PreyRouter;
import com.siemieniuk.animals.WorldObjectType;
import com.siemieniuk.animals.math.Coordinates;

import java.util.Iterator;
import java.util.List;

/**
 * Class representing a prey - subclass of animal
 * @author Szymon Siemieniuk
 */
public final class Prey extends Animal {
	private static final int MAX_FOOD_LEVEL = 100;
	private static final int MAX_WATER_LEVEL = 100;
	private final int foodDecrease;
	private int foodLevel;
	private final int waterDecrease;
	private int waterLevel;
	private boolean isUsingMutex;
	private boolean isWaitingForHideout;
	private Location targetLocation;
	private List<Coordinates> plan;
	private Coordinates nextStep;

	/**
	 * Constructor
     * @param name Name of animal
     * @param health Initial health
     * @param speed Speed of animal
     * @param strength Strength of animal
     * @param species Animal's species
	 * @param waterDecrease How much water level will animal lose after each step 
	 * @param foodDecrease How much food level will animal lose after each step 
	 */
	public Prey(String name, int health, int speed, int strength, String species, int waterDecrease, int foodDecrease) {
		super(name, health, speed, strength, species);
		this.foodDecrease = foodDecrease;
		this.waterDecrease = waterDecrease;
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
					if (getPos().equals(targetLocation.getPos())) {
						consume();
						releaseResources();
					} else {
						if (planIterator.hasNext()) {
							nextStep = planIterator.next();
						}
						releaseResources();
						move();
					}
					Thread.sleep(1000/getSpeed());
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
				obj = Class.forName("com.siemieniuk.animals.core.PlantSource");
			} else if (isThirsty()) {
				obj = Class.forName("com.siemieniuk.animals.core.WaterSource");
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

	public void findNewTarget(WorldObjectType locType) {
		PreyRouter router = new PreyRouter(getPos());
		Class obj;
		try {
			switch(locType) {
				case PLANT_SRC	-> obj = Class.forName("com.siemieniuk.animals.core.PlantSource");
				case WATER_SRC	-> obj = Class.forName("com.siemieniuk.animals.core.WaterSource");
				case HIDEOUT	-> obj = Class.forName("com.siemieniuk.animals.Hideout");
				default			-> throw new IllegalArgumentException("Should be PLANT_SRC, WATER_SRC, HIDEOUT");
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

	/**
	 * Takes damage from the predator.
	 * @param predatorStrength Strength of the predator
	 */
	public synchronized void beAttacked(int predatorStrength) {
		int lostHealth = Math.max(0, predatorStrength - getStrength());
		decreaseHealthBy(lostHealth);
	}

	private void consume() throws InterruptedException {
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

	private void useHideout() throws InterruptedException {
		if (targetLocation instanceof Hideout) {
			final float P_REPRODUCE = 0.01f;
			int maxHealth = getMAX_HEALTH();
			int i = 0;
			final int MIN_ITERATIONS = 5;
			while (isAlive() && (getHealth() < 0.9*maxHealth || (i < MIN_ITERATIONS))) {
				i++;
				heal(5);
				Thread.sleep(200);
				if (Math.random() < P_REPRODUCE) {
					reproduce();
				}
			}
		}
	}

	private void drink() throws InterruptedException {
		if (targetLocation instanceof WaterSource) {
			while (isAlive() && waterLevel < 0.95*MAX_WATER_LEVEL) {
				int increase = (int)((WaterSource) targetLocation).getHowMuchToConsume();
				waterLevel = Math.min(MAX_WATER_LEVEL, waterLevel + increase);
				Thread.sleep(200);
			}
		}
	}

	private void eat() throws InterruptedException {
		if (targetLocation instanceof PlantSource) {
			while (isAlive() && foodLevel < 0.95*MAX_FOOD_LEVEL) {
				int increase = (int)((PlantSource) targetLocation).getHowMuchToConsume();
				foodLevel = Math.min(MAX_FOOD_LEVEL, foodLevel + increase);
				Thread.sleep(200);
			}
		}
	}

	/**
	 * Decrease statistics
	 */
	public synchronized void decreaseStatistics() {
		if (!isWaitingForHideout) {
			foodLevel = Math.max(0, foodLevel-foodDecrease);
			waterLevel = Math.max(0, waterLevel-waterDecrease);
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
	public WorldObjectType getMetadataCode() {
		return WorldObjectType.PREY;
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

	/**
	 * Sets object-specific string to describe the object's state
	 * @return Text to display
	 */
	@Override
	public String getDetails() {
		return "Prey:" +
				"\n" + super.getDetails() +
				"\nFood level: " + foodLevel +
				"\nWater level: " + waterLevel + "\n";
	}
}
