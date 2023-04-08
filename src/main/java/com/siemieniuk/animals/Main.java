package com.siemieniuk.animals;

public class Main {
    public static void main(String[] args) {
    	World world = WorldBuilder.create(10, 5, 20, 20);
    	System.out.println(world);
    }
}