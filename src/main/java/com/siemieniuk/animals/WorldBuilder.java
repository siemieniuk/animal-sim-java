package com.siemieniuk.animals;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Iterator;

public class WorldBuilder {
    private static final int MIN_DIST = 4;
    private WorldBuilder() {}
    public static World create(int howManySources, int howManyHideouts, int xSize, int ySize){
        List<Coordinates> coordinates = generateCoords(xSize, ySize, howManySources+howManyHideouts);
        System.out.println(coordinates);
        List<Location> locations = new ArrayList<> ();
        generateWaterSources(locations, coordinates.subList(0, howManySources / 2));
        generatePlantSources(locations, coordinates.subList(howManySources/2, howManySources));
        generateHideouts(locations, coordinates.subList(howManySources, coordinates.size()));
        // TODO: create animals
        return new World(locations, xSize, ySize);
    }

    private static void generatePlantSources(List<Location> locations, List<Coordinates> coordinates) {
        Iterator<Coordinates> it = coordinates.iterator();
        while (it.hasNext()) {
            /* TODO: Random replenishing speed */
            /* TODO: Random capacity */
            Coordinates c = it.next();
            PlantSource ps = new PlantSource(c.getX(), c.getY(), "a", 2, 3);
            locations.add(ps);
        }
    }

    private static void generateWaterSources(List<Location> locations, List<Coordinates> coordinates) {
        Iterator<Coordinates> it = coordinates.iterator();
        while (it.hasNext()) {
            /* TODO: Random replenishing speed */
            /* TODO: Random capacity */
            Coordinates c = it.next();
            WaterSource ws = new WaterSource(c.getX(), c.getY(), "a", 2, 3);
            locations.add(ws);
        }
    }

    private static void generateHideouts(List<Location> locations, List<Coordinates> coordinates) {
        Iterator<Coordinates> it = coordinates.iterator();
        while (it.hasNext()) {
            /* TODO: Random capacity */
            Coordinates c = it.next();
            Hideout h = new Hideout(c.getX(), c.getY(), 3);
            locations.add(h);
        }
    }

    private static List<Coordinates> generateCoords(int xSize, int ySize, int targetSize) {
        List<Coordinates> coordinates = new ArrayList<>();
        for (int x = 0; x < xSize; x++) {
            for (int y=0; y<ySize; y++) {
                coordinates.add(new Coordinates(x, y));
            }
        }
        Collections.shuffle(coordinates);

        int currentSize = 2;
        while (currentSize < targetSize) {
            boolean flag_to_rm = false;
            for (int i=0; i<currentSize; i++) {
                if (coordinates.get(i).getManhattanDistanceTo(coordinates.get(currentSize)) < MIN_DIST) {
                    flag_to_rm = true;
                    break;
                }
            }
            if (flag_to_rm) {
                coordinates.remove(currentSize);
            } else {
                currentSize++;
            }
        }
        while (coordinates.size() > targetSize) {
            coordinates.remove(targetSize);
        }
        return coordinates;
    }
}
