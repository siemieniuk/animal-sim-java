package siemieniuk.animals.core.animals;

import siemieniuk.animals.core.DetailsPrintable;
import siemieniuk.animals.core.WorldObjectMetadata;
import siemieniuk.animals.math.Coordinates;

/**
 * This class is abstract. Represents animal. Each subclass must be runnable.
 * @author Szymon Siemieniuk
 */
public abstract class Animal implements DetailsPrintable, WorldObjectMetadata, Runnable {
    private static Integer createdAnimals = 0;
    private final Integer id;
    private volatile Coordinates pos = null;
    private String name;
    private volatile double health;
    private final double MAX_HEALTH;
    private double speed;
    private double strength;
    private String species;

    public Animal() {
        id = ++createdAnimals;
        MAX_HEALTH = 0;
    }

    /**
     * Constructor
     * @param name Name of an animal
     * @param health Initial health (and also the maximum level)
     * @param speed Speed of an animal
     * @param strength Strength of an animal
     * @param species Animal's species
     */
    public Animal(String name, int health, int speed, int strength, String species) {
    	this.name = name;
    	this.health = health;
        this.MAX_HEALTH = health;
    	this.speed = speed;
    	this.strength = strength;
    	this.species = species;
        this.id = ++createdAnimals;
    }
    
    /**
     * Checks if an animal is still alive
     * @return True if alive, false if passed away
     */
    public boolean isAlive() {
    	return health > 0;
    }

    /**
     * Moves animal
     */
    public abstract void move();

    /**
     * Heals animal by a specific value
     * @param additionalHealth Amount to increase health
     */
    public synchronized void heal(int additionalHealth) {
        health = Math.min(health+additionalHealth, MAX_HEALTH);
    }

    /**
     * Finds a new thing to do
     * @throws InterruptedException Interrupted exception
     */
    protected abstract void setNewTarget() throws InterruptedException;

    public void releaseResources() {}

    public Integer getId() {
        return id;
    }

    public Coordinates getPos() {
        return pos;
    }

    public void setPos(Coordinates pos) {
        this.pos = pos;
    }

    public double getHealth() {
        return health;
    }

    public double getHealthRatio() {
        return health / MAX_HEALTH;
    }

    public double getMAX_HEALTH() {
        return MAX_HEALTH;
    }

    public String getHealthDetails() {
        if (health < 0) {
            return "DIED";
        } else {
            return String.format("%.0f / %.0f", health, MAX_HEALTH);
        }
    }

    protected double getSpeed() {
        return speed;
    }

    public double getStrength() {
        return strength;
    }

    /**
     * Decreases health by a specific value
     * @param value A value by which health should be decreased.
     */
    protected synchronized void decreaseHealthBy(double value) {
        this.health = health - value;
    }

    public void kill() {
        this.health = -1000.0;
    }

    @Override
    public String getDetails() {
        return "Name: " + name +
                "\nSpecies: " + species +
                "\nHealth: " + health +
                "\nSpeed: " + speed +
                "\nStrength: " + strength;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }
}
