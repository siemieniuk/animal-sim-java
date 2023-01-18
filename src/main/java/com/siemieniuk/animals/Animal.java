package com.siemieniuk.animals;

import com.siemieniuk.animals.math.Coordinates;
import javafx.scene.canvas.GraphicsContext;

/**
 * Abstract class for animal
 * @author Szymon Siemieniuk
 * @version 0.1
 */
public abstract class Animal implements DetailsPrintable, Drawable, Runnable {
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
     * @param name Name of animal
     * @param health Initial health
     * @param speed Speed of animal
     * @param strength Strength of animal
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

    @Override
    public abstract void run();

    @Override
    public abstract void prepareToDrawOn(GraphicsContext gc);

    public void heal(int addHp) {
        health = Math.min(health+addHp, MAX_HEALTH);
    }

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

    public int getMAX_HEALTH() {
        return MAX_HEALTH;
    }

    protected int getSpeed() {
        return speed;
    }

    public int getStrength() {
        return strength;
    }

    protected void decreaseHealth(int decreaser) {
        this.health = health - decreaser;
    }

    @Override
    public String getDetails() {
        return "Name: " + name +
                "\nSpecies: " + species +
                "\nHealth: " + health +
                "\nSpeed: " + speed +
                "\nStrength: " + strength;
    }
}
