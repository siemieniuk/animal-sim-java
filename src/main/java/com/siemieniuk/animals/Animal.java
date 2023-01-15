package com.siemieniuk.animals;

import javafx.scene.canvas.GraphicsContext;

import java.util.Objects;

/**
 * Abstract class for animal
 * @author Szymon Siemieniuk
 * @version 0.1
 */
public abstract class Animal implements DetailsPrintable, Drawable, Runnable {
    private static Integer createdAnimals = 0;
    private final Integer id;
    private Coordinates pos;
    private String name;
    private int health;
    private int speed;
    private int strength;
    private String species;

    public Animal() {
        id = ++createdAnimals;
    }

    /**
     * Constructor
     * @param name Name of animal
     * @param health Initial health
     * @param speed Speed of animal
     * @param strength Strength of animal
     * @param species Animal's species
     */
    public Animal(String name, int health, int speed, int strength, String species, Coordinates pos) {
    	this.name = name;
    	this.health = health;
    	this.speed = speed;
    	this.strength = strength;
    	this.species = species;
        this.id = ++createdAnimals;
        System.out.println(this.id);
        this.pos = Objects.requireNonNullElseGet(pos, () -> new Coordinates(0, 0));
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
//    public abstract void move(Coordinates newPos);
    public abstract void move();

    /* TODO: Implement */
    /**
     * Draws animal on the screen
     */

    @Override
    public abstract void run();

    @Override
    public abstract void prepareToDrawOn(GraphicsContext gc);

    protected abstract void findNewTarget();

    public Integer getId() {
        return id;
    }

    public Coordinates getPos() {
        return pos;
    }

    public void setPos(Coordinates pos) {
        this.pos = pos;
    }

    protected int getSpeed() {
        return speed;
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
