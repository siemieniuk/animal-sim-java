package siemieniuk.animals.core.animals;

import siemieniuk.animals.core.World;
import siemieniuk.animals.core.animals.preyrouter.HideoutRouter;
import siemieniuk.animals.core.animals.preyrouter.PlantSourceRouter;
import siemieniuk.animals.core.animals.preyrouter.PreyRouter;
import siemieniuk.animals.core.animals.preyrouter.WaterSourceRouter;
import siemieniuk.animals.core.locations.*;
import siemieniuk.animals.core.typing.WorldObjectType;
import siemieniuk.animals.math.Coordinates;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
	private boolean isUsingResource;
	private Location targetLocation;
	private volatile List<Coordinates> plan;
	private volatile Iterator<Coordinates> planIterator = null;
	private volatile Coordinates nextStep;

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
		this.foodLevel = MAX_FOOD_LEVEL;
		this.waterDecrease = waterDecrease;
		this.waterLevel = MAX_WATER_LEVEL;
		this.plan = null;
		this.isUsingResource = false;
	}

	@Override
	public void run() {
		try {
			while (isAlive()) {
				if (targetLocation == null) {
					setNewTarget();
				} else {
					if (isInTarget()) {
						consume();
					} else {
						if (planIterator.hasNext()) {
							nextStep = planIterator.next();
						}
						releaseResources();
						move();
					}
					TimeUnit.MILLISECONDS.sleep(1000/getSpeed());
				}
			}
		} catch (InterruptedException e) {
			releaseResources();
		}
	}

	@Override
	protected void setNewTarget() throws InterruptedException {
		PreyRouter router;
		if (isHungry()) {
			router = new PlantSourceRouter(getPos());
		} else if (isThirsty()) {
			router = new WaterSourceRouter(getPos());
		} else {
			router = new HideoutRouter(getPos());
		}
		plan = router.getPlan();
		targetLocation = router.getTarget();
		if (plan != null) {
			planIterator = plan.iterator();
		}
	}

	public void setNewTarget(WorldObjectType locType) throws InterruptedException {
		switch(locType) {
			case PLANT_SRC -> foodLevel = 0;
			case WATER_SRC -> waterLevel = 0;
			case HIDEOUT   -> {}
			default        -> throw new IllegalArgumentException("Should be PLANT_SRC, WATER_SRC, HIDEOUT");
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
	public void beAttacked(int predatorStrength) {
		int lostHealth = Math.max(0, predatorStrength - getStrength());
		decreaseHealthBy(lostHealth);
	}

	private void consume() throws InterruptedException {
		isUsingMutex = true;
		if (targetLocation instanceof WaterSource) {
			isUsingResource = drink();
		} else if (targetLocation instanceof PlantSource) {
			isUsingResource = eat();
		} else {
			isUsingResource = useHideout();
		}
		if (!isUsingResource) {
			targetLocation = null;
			releaseResources();
		}
	}

	private boolean useHideout() throws InterruptedException {
		if (targetLocation instanceof Hideout) {
			final float P_REPRODUCE = 0.005f;
			int maxHealth = getMAX_HEALTH();
			int i = 0;
			final int MIN_ITERATIONS = 5;
			while (isAlive() && (getHealth() < 0.9*maxHealth || (i < MIN_ITERATIONS))) {
				i++;
				heal(5);
				TimeUnit.MILLISECONDS.sleep(200);
				if (Math.random() < P_REPRODUCE) {
					reproduce();
				}
			}
		}
		return false;
	}

	private boolean drink() {
		if (targetLocation instanceof WaterSource) {
			if (isAlive() && waterLevel < 0.95 * MAX_WATER_LEVEL) {
				int increase = (int) ((WaterSource) targetLocation).getHowMuchToConsume();
				waterLevel = Math.min(MAX_WATER_LEVEL, waterLevel + increase);
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	private boolean eat() {
		if (targetLocation instanceof PlantSource) {
			if (isAlive() && foodLevel < 0.95 * MAX_FOOD_LEVEL) {
				int increase = (int) ((PlantSource) targetLocation).getHowMuchToConsume();
				foodLevel = Math.min(MAX_FOOD_LEVEL, foodLevel + increase);
				return true;
			} else {
				return false;
			}
		}
		return false;
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
	@Override
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

	public boolean isInTarget() {
		if (plan == null) {
			return true;
		}
		if (getPos().equals(targetLocation.getPos())) {
			return true;
		}
		return isUsingResource;
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

	public double getWaterRatio() {
		return waterLevel / (double) MAX_WATER_LEVEL;
	}

	public String getWaterDetails() {
		return waterLevel + " / " + MAX_WATER_LEVEL;
	}

	public double getFoodRatio() {
		return foodLevel / (double) MAX_FOOD_LEVEL;
	}

	public String getFoodDetails() {
		return foodLevel + " / " + MAX_FOOD_LEVEL;
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
