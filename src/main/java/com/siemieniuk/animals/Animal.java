package com.siemieniuk.animals;

/**
 * Abstract class for animal
 * @author Szymon Siemieniuk
 * @version 0.1
 */
public abstract class Animal implements Drawable {
    private String name;
    private int health;
    private int speed;
    private int strength;
    private String species;
    
    /* TODO: implement */
    public Animal() {
    	
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
    	this.speed = speed;
    	this.strength = strength;
    	this.species = species;
    }
    
    /**
     * Checks if an animal is still alive
     * @return True if alive, false if passed away
     */
    public boolean isAlive() {
    	return (health > 0) ? true : false;
    }
    
    /**
     * Moves animal
     */
    public abstract void move();
    
    /* TODO: Implement */
    /**
     * Draws animal on the screen
     */
    public void draw() {
    	
    }
}
