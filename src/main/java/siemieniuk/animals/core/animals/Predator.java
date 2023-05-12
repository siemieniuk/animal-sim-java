package siemieniuk.animals.core.animals;

import siemieniuk.animals.core.DetailsPrintable;
import siemieniuk.animals.core.World;
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
public final class Predator extends Animal implements DetailsPrintable {
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
	public Predator(String name, int health, int speed, int strength, String species) {
		super(name, health, speed, strength, species);
		this.currentMode = PredatorMode.RELAXATION;
	}

	@Override
	public void run() {
		try {
			while (isAlive()) {
				if (currentMode.equals(PredatorMode.HUNTING)) {
					if (preyToEat == null || !preyToEat.isAlive()) {
						setNewTarget();
					} else {
						if (!getPos().equals(preyToEat.getPos())) {
							move();
						} else {
							attackMyPrey();
						}
					}
				} else {
					relax();
				}
				TimeUnit.MILLISECONDS.sleep(1000/getSpeed());
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
					World.getInstance().removeAnimal(preyToEat);
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
		for (Prey p : World.getInstance().getPreys()) {
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

	/**
	 * Sets object-specific string to describe the object's state
	 * @return Text to display
	 */
	@Override
	public String getDetails() {
		return "Predator:" +
				"\n" + super.getDetails() +
				"\nCurrent mode: " + currentMode + "\n";
	}

	public PredatorMode getCurrentMode() {
		return currentMode;
	}

	public Prey getPreyToEat() {
		return preyToEat;
	}
}
