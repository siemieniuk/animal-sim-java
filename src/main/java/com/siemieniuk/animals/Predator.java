package com.siemieniuk.animals;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

/**
 * @author Szymon Siemieniuk
 * @version 0.1
 *
 */
public class Predator extends Animal implements DetailsPrintable {
	private PredatorMode currentMode;
	private Prey preyToEat = null;
	
	/* TODO: Implement */
	/**
	 * 
	 */
	public Predator() { }
	
	/**
     * Constructor
     * @param name Name of animal
     * @param health Initial health
     * @param speed Speed of animal
     * @param strength Strength of animal
     * @param species Animal's species}
     */
	public Predator(String name, int health, int speed, int strength, String species, Coordinates pos) {
		super(name, health, speed, strength, species, pos);
		this.currentMode = PredatorMode.RELAXATION;
	}

	/* TODO: Implement */
	@Override
	public void run() {
		Random rd = new Random();
		int MAX_X = World.getInstance().getxSize();
		int MAX_Y = World.getInstance().getySize();
		main: while (isAlive()) {
			if (preyToEat == null) {
				// TODO: Switch mode
				findNewTarget();
			} else {
				while (!getPos().equals(preyToEat.getPos())) {
					move();
					try {
						Thread.sleep(1000 / getSpeed());
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						break main;
					}
				}
				attackMyPrey();
//				System.out.println("I am predator!\tID=" + getId());
			}
		}
	}

	/* TODO: Implement */
	/**
	 * Attacks prey in preyToEat
	 */
	public void attackMyPrey() {
		
	}

	/**
	 * Eats prey in preyToEat
	 */
	public void eatMyPrey() {
		if (!preyToEat.isAlive()) {
			switchMode();
			World.getInstance().removeAnimal(preyToEat.getId());
			preyToEat = null;
		}
	}

	@Override
	protected void findNewTarget() {
		int minDist = Integer.MAX_VALUE;
		int currentDist;
		for (Prey p : World.getInstance().getPreys()) {
			currentDist = getPos().getManhattanDistanceTo(p.getPos());
			if (currentDist < minDist) {
				minDist = currentDist;
				preyToEat = p;
			}
		}
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
	public void prepareToDrawOn(GraphicsContext gc) {
		gc.setFill(Color.RED);
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

	@Override
	public String getDetails() {
//		return "Predator:\n%s\nCurrent mode:%s".formatted(super.getDetails(), currentMode);
		return "Predator:" +
				"\n" + super.getDetails() +
				"\nCurrent mode: " + currentMode + "\n";
	}
}
