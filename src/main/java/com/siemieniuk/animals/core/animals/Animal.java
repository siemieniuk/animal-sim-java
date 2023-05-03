package com.siemieniuk.animals.core.animals;

import com.siemieniuk.animals.core.DetailsPrintable;
import com.siemieniuk.animals.core.WorldObjectMetadata;
import com.siemieniuk.animals.math.Coordinates;

/**
 * This class is abstract. Represents animal. Each subclass must be runnable.
 * @author Szymon Siemieniuk
 */
public abstract class Animal implements DetailsPrintable, WorldObjectMetadata, Runnable {
    private static Integer createdAnimals = 0;
    private final Integer id;
    private Coordinates pos = null;
    private String name;
    private int health;
    private final int MAX_HEALTH;
    private int speed;
    private int strength;
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
    public void heal(int additionalHealth) {
        health = Math.min(health+additionalHealth, MAX_HEALTH);
    }

    /**
     * Finds a new thing to do
     * @throws InterruptedException Interrupted exception
     */
    protected abstract void findNewTarget() throws InterruptedException;

    public Integer getId() {
        return id;
    }

    public Coordinates getPos() {
        return pos;
    }

    public void setPos(Coordinates pos) {
        this.pos = pos;
    }

    public int getHealth() {
        return health;
    }

    public double getHealthRatio() {
        return health / (double) MAX_HEALTH;
    }

    public int getMAX_HEALTH() {
        return MAX_HEALTH;
    }

    public String getHealthDetails() {
        if (health < 0) {
            return "DIED";
        } else {
            return health + " / " + MAX_HEALTH;
        }
    }

    protected int getSpeed() {
        return speed;
    }

    public int getStrength() {
        return strength;
    }

    /**
     * Decreases health by a specific value
     * @param value A value by which health should be decreased.
     */
    protected void decreaseHealthBy(int value) {
        this.health = health - value;
    }

    public void kill() {
        this.health = -1000;
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
