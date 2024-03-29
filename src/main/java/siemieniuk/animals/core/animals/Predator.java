package siemieniuk.animals.core.animals;

import lombok.Getter;
import siemieniuk.animals.core.locations.LocationRepository;
import siemieniuk.animals.core.typing.WorldObjectType;
import siemieniuk.animals.math.Coordinates;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * This class represents a predator. Can work as a thread. His life is simple: sleep, hunt, repeat.
 * @author Szymon Siemieniuk
 * @version 0.1
 *
 */
@Getter
public final class Predator extends Animal {
	private PredatorMode currentMode;
	private Prey preyToEat = null;

	/**
     * Constructor
     * @param name Name of animal
     * @param health Initial health
     * @param speed Speed of animal
     * @param strength Strength of animal
     * @param species Animal's species
     */
	public Predator(AnimalRepository animalRepository, LocationRepository locationRepository,
					String name, int health, int speed, int strength, String species) {
		super(animalRepository, locationRepository, name, health, speed, strength, species);
		this.currentMode = PredatorMode.RELAXATION;
	}

	@Override
	public void run() {
		try {
			while (isAlive()) {
				switch (currentMode) {
					case HUNTING -> {
						if (preyToEat == null || !preyToEat.isAlive()) {
							setNewTarget();
						} else {
							if (!getPos().equals(preyToEat.getPos())) {
								move();
							} else {
								attackMyPrey();
							}
						}
						TimeUnit.MILLISECONDS.sleep((long)(1000.0/getSpeed()));
					}

					case RELAXATION -> relax();
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			kill();
			preyToEat = null;
		}
	}

	private synchronized void attackMyPrey() {
		if (!(preyToEat.isAlive())) {
			switchMode(PredatorMode.RELAXATION);
		} else {
			if (isSameLocationAsPrey() && preyToEat.canBeAttacked()) {
				preyToEat.beAttacked(getStrength());
				if (!preyToEat.isAlive()) {
					getAnimalRepository().remove(preyToEat);
					preyToEat = null;
					switchMode(PredatorMode.RELAXATION);
				}
			}
		}
	}

	@Override
	protected void setNewTarget() throws InterruptedException {
		if (preyToEat != null) {
			preyToEat = null;
		}
		int minDist = Integer.MAX_VALUE;
		int currentDist;
		for (Prey p : getKnownPreys()) {
			if (p.canBeAttacked()) {
				currentDist = getPos().getManhattanDistanceTo(p.getPos());
				if (currentDist < minDist) {
					minDist = currentDist;
					preyToEat = p;
				}
			}
		}
	}

	private boolean isSameLocationAsPrey() {
		if (preyToEat == null) {
			return false;
		}
		return preyToEat.getPos().equals(this.getPos());
	}

	/**
	 * Moves predator
	 */
	@Override
	public void move() {
		if (preyToEat == null) {
			switchMode(PredatorMode.RELAXATION);
			return;
		}
		Coordinates preyCoords = preyToEat.getPos();
		if (getPos().equals(preyCoords)) {
			// position is the same; do not move
			return;
		}
		int currentX = getPos().getX();
		int currentY = getPos().getY();
		if (currentX != preyCoords.getX()) {
			int newX = currentX + (currentX < preyCoords.getX() ? 1 : -1);
			setPos(new Coordinates(newX, currentY));
		} else {
			int newY = currentY + (currentY < preyCoords.getY() ? 1 : -1);
			setPos(new Coordinates(currentX, newY));
		}
	}

	private void relax() throws InterruptedException {
		if (currentMode.equals(PredatorMode.RELAXATION)) {
			Random random = new Random();
			final int MIN_RELAX_MS = 1000;
			final int MAX_RELAX_MS = 5000;

			int timeToRelax = random.nextInt(MIN_RELAX_MS, MAX_RELAX_MS);
			Thread.sleep(timeToRelax);
			switchMode(PredatorMode.HUNTING);
		}
	}

	@Override
	public WorldObjectType getMetadataCode() {
		return WorldObjectType.PREDATOR;
	}

	/**
	 * Switches mode of this predator. Hunting reaches relaxation and vice versa.
	 */
	public void switchMode(PredatorMode newMode) {
		this.currentMode = newMode;
	}

}
