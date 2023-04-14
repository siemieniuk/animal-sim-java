package com.siemieniuk.animals;

import com.siemieniuk.animals.math.Coordinates;

import java.util.Random;

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
		Random random = new Random();
		final int MIN_RELAX_MS = 1000;
		final int MAX_RELAX_MS = 5000;
		try {
			while (isAlive()) {
				if (currentMode.equals(PredatorMode.HUNTING)) {
					try {
						findNewTarget();
						while (!getPos().equals(preyToEat.getPos())) {
							move();
							Thread.sleep(1000 / getSpeed());
						}
						attackMyPrey();
						Thread.sleep(1000 / getSpeed());
					} catch (NullPointerException e) {
						e.printStackTrace();
						switchMode();
					}
				} else {
					int timeToRelax = random.nextInt(MIN_RELAX_MS, MAX_RELAX_MS);
					Thread.sleep(timeToRelax);
					switchMode();
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			preyToEat = null;
		}
	}

	private synchronized void attackMyPrey() {
		if (preyToEat == null) {
			switchMode();
		} else {
			if (isSameLocationAsPrey() && preyToEat.canBeAttacked()) {
				preyToEat.beAttacked(getStrength());
				if (!preyToEat.isAlive()) {
					if (preyToEat != null) {
						World.getInstance().removeAnimal(preyToEat.getId());
					}
					preyToEat = null;
					switchMode();
				}
			}
		}
	}

	@Override
	protected void findNewTarget() throws InterruptedException {
		int minDist = Integer.MAX_VALUE;
		int currentDist;
		while (preyToEat == null) {
			for (Prey p : World.getInstance().getPreys()) {
				if (p.canBeAttacked()) {
					currentDist = getPos().getManhattanDistanceTo(p.getPos());
					if (currentDist < minDist) {
						minDist = currentDist;
						preyToEat = p;
					}
				}
			}
			Thread.sleep(5);
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

	@Override
	public WorldObjectType getMetadataCode() {
		return WorldObjectType.PREDATOR;
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
}
